package com.ecwalk.common.other.thread.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 实现数据库链接池
 * @author billy
 *
 */
public class DBPool {
	//数据库容器
	private static LinkedList<Connection> pool=new LinkedList<>();
	
	public DBPool(int initalSize){
		if(initalSize>0){
			for(int i=0;i<initalSize;i++){
				pool.addLast(SqlConnectImpl.fetchConnection());
			}
		}
	}
	
	//在mills时间内拿不到数据库链接池，返回null
	public Connection fetchConn(long mills) throws InterruptedException{
		synchronized (pool) {
			if(mills<0){
				while (pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			}else{
				long overtime=System.currentTimeMillis()+mills;
				long remain=mills;
				//remain时间内
				while (pool.isEmpty()&&remain>0) {
					pool.wait(remain);
					remain=overtime-System.currentTimeMillis();
				}
				Connection result=null;
				if(!pool.isEmpty()){
					result=pool.removeFirst();
				}
				return result;
			}
		}
	}
	
	/**
	 * 释放链接
	 */
	public void releaseConn(Connection conn){
		if(conn!=null){
			synchronized (pool) {
				pool.addLast(conn);
				pool.notifyAll();
			}
		}
	}
}
