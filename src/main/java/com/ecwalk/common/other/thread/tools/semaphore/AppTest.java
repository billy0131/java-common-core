package com.ecwalk.common.other.thread.tools.semaphore;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AppTest {
	
	private static DBPoolSemaphore dbPool=new DBPoolSemaphore();
	
	static Random random=new Random();
	
	private static class BusiThread extends Thread{
		
		@Override
		public void run(){
			long start=System.currentTimeMillis();
			try {
				Connection connection=dbPool.takeConnect();
				System.out.println("Thread_"+Thread.currentThread().getId()
						+"_获得数据库连接共耗时【"+(System.currentTimeMillis()-start)+"】ms");
				TimeUnit.MILLISECONDS.sleep(100+random.nextInt(100));
				System.out.println("查询数据完成，归还连接");
				dbPool.returnConneect(connection);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args)throws InterruptedException{
		for(int i=0;i<50;i++){
			Thread thread=new BusiThread();
			thread.start();
		}
	}

}
