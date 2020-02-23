package com.ecwalk.common.other.redis;

import redis.clients.jedis.Jedis;

public class TestRedis {
	
	public static void main(String[] args) {
		Jedis jedis=new Jedis("192.168.0.156",6379);
		jedis.set("billy", "1");
		jedis.close();
	}

}
