package com.ecwalk.common.other.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class FlowLimitFilter {
	
	/**
	 * 多线程有问题
	 * @param ip
	 * @param limit
	 * @param time
	 * @param jedis
	 * @return
	 */
	private boolean accessLimit(String ip,int limit,int time,Jedis jedis){
		boolean result=false;
		String key="rate.limit:"+ip;
		if(jedis.exists(key)){
			long afterValue=jedis.incr(key);
			if(afterValue>limit){
				result =false;
			}
		}else{
			Transaction transaction=jedis.multi();
			transaction.incr(key);
			transaction.expire(key, time);
			transaction.exec();
		}
		return result;
	}

}
