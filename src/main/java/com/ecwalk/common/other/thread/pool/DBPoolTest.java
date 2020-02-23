package com.ecwalk.common.other.thread.pool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {
	
	static DBPool pool=new DBPool(10);
	
	static CountDownLatch end;
	
	public static void main(String[] args) throws Exception{
		//线程数量
		int threadCount=50;
		end=new CountDownLatch(threadCount);
		int count=20;//每个线程操作的次数
		AtomicInteger got=new AtomicInteger();//计数器；统计可以拿到链接的线程
		AtomicInteger notGot=new AtomicInteger();//计数器；统计没有拿到链接的线程
		for(int i=0;i<threadCount;i++){
			Thread thread=new Thread(new Worker(count,got,notGot),"worker_"+i);
			thread.start();
		}
		end.await();//main线程在此处等待
		System.out.println("总尝试"+(threadCount*count));
		System.out.println("拿到连接次数"+got);
		System.out.println("没拿到次数"+notGot);
	}
	
	static class Worker implements Runnable{

		int count;
		AtomicInteger got;
		AtomicInteger notGot;
		
		public Worker(int count,
				AtomicInteger got,
				AtomicInteger notGot){
			this.count=count;
			this.got=got;
			this.notGot=notGot;
		}
		
		@Override
		public void run() {
			while(count>0){
				try {
					//从线程池中获取连接，如果1000ms内无法获取，将返回null
					//分别统计连接获取的数量got和未获取的数量notGot
					Connection connection=pool.fetchConn(1000);
					if(connection!=null){
						try {
							connection.createStatement();
							connection.commit();
						}finally{
							pool.releaseConn(connection);
							got.incrementAndGet();
						}
					}else{
						notGot.incrementAndGet();
						System.out.println(Thread.currentThread().getName()+"等待超时");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}finally {
					count--;
				}
			}
			end.countDown();
		}
		
	}
}
