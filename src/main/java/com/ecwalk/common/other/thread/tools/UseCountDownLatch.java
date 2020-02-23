package com.ecwalk.common.other.thread.tools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 有五个初始化线程，六个扣除点
 * 扣除完毕后，主线程和业务线程才能继续自己工作
 * @author billy
 *
 */
public class UseCountDownLatch {
	
	static CountDownLatch latch=new CountDownLatch(6);
	//初始化线程
	private static class InitThread implements Runnable{

		@Override
		public void run() {
			System.out.println("Thread_"+Thread.currentThread().getId()+" ready init work ");
			latch.countDown();
			for(int i=0;i<2;i++){
				System.out.println("Thread_"+Thread.currentThread().getId()+" continue do its work");
			}
		}
		
	}
	//业务线程
	private static class BusiThread implements Runnable{

		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<3;i++){
				System.out.println("BusiThread_"+Thread.currentThread().getId()+"  do business");
			}
			
		}
		
	}
	
	public static void main(String[] args)throws InterruptedException{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Thread_"+Thread.currentThread().getId()+" ready init work step 1st..");
				latch.countDown();//每完成一次，扣减一次
				System.out.println("begin step 2nd..");
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Thread_"+Thread.currentThread().getId()+" ready init work step 2st..");
				latch.countDown();//每完成一次，扣减一次
			}
		}).start();
		new Thread(new BusiThread()).start();
		for(int i=0;i<=3;i++){
			Thread thread=new Thread(new InitThread());
			thread.start();
		}
		latch.await();
		System.out.println("Main do ites work");
	}

}
