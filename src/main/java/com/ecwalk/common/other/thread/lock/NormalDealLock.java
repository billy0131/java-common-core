package com.ecwalk.common.other.thread.lock;

import java.util.concurrent.TimeUnit;

public class NormalDealLock {
	
	private static Object valueFirst=new Object();
	private static Object valueSecond=new Object();
	
	private static void firstToSecond() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		synchronized (valueFirst) {
		System.out.println(threadName+" get first");
		TimeUnit.MILLISECONDS.sleep(100);
			synchronized (valueSecond) {
				System.out.println(threadName+" get second");
			}
		}
	}
	
	private static void secondToFirst() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		synchronized (valueSecond) {
		System.out.println(threadName+" get second");
		TimeUnit.MILLISECONDS.sleep(100);
			synchronized (valueFirst) {
				System.out.println(threadName+" get first");
			}
		}
	}
	
	//执行第一个锁，再拿第二个锁
	private static class TestThread extends Thread{
		private String name;

		public TestThread(String name) {
			super();
			this.name = name;
		}
		
		@Override
		public void run(){
			Thread.currentThread().setName(name);
			try {
				secondToFirst();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		Thread.currentThread().setName("TestDeadLock");
		TestThread testThread=new TestThread("SubTestThread");
		testThread.start();
		try {
			firstToSecond();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
