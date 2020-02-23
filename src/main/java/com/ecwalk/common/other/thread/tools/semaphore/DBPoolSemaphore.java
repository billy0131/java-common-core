package com.ecwalk.common.other.thread.tools.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import com.ecwalk.common.other.thread.pool.SqlConnectImpl;

public class DBPoolSemaphore {
	private final static  int POOL_SIZE=10;
	private final Semaphore useful,useless;//useful可用的数据库连接，useless已用的数据库连接

	public DBPoolSemaphore(){
		this.useful=new Semaphore(POOL_SIZE);
		this.useless=new Semaphore(0);
	}
	//存数据库连接的池
	private static LinkedList<Connection> pool=new LinkedList<>();
	//初始化
	static{
		for(int i=0;i<POOL_SIZE;i++){
			pool.addLast(SqlConnectImpl.fetchConnection());
		}
	}
	/**
	 * 归还连接
	 * @param connection
	 * @throws InterruptedException
	 */
	public void returnConneect(Connection connection) throws InterruptedException{
		if(connection!=null){
			System.out.println("当前有"+useful.getQueueLength()+"个线程等待数据库连接"
					+"可用连接数:"+useful.availablePermits());
			useless.acquire();
			synchronized (pool) {
				pool.addLast(connection);
			}
			useful.release();
		}
	}

	/**
	 * 从池子拿连接
	 * @return
	 * @throws InterruptedException
	 */
	public Connection takeConnect() throws InterruptedException{
		useful.acquire();
		Connection connection;
		synchronized (pool) {
			connection=pool.removeFirst();	
		}
		useless.release();
		return connection;
	}
}
