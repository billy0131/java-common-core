package com.ecwalk.common.other.thread;

public class SleepLock {
	
	private Object lock=new Object();
	
	public static void main(String[] args){
		SleepLock sleepLock=new SleepLock();
		Thread threadA=sleepLock.new ThreadSleep();
		threadA.setName("ThreadSleep");
		Thread threadB=sleepLock.new ThreadNotSleep();
		threadB.setName("ThreadNotSleep");
		threadA.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadB.start();
	}
	
	private class ThreadSleep extends Thread{
		
		@Override
		public void run(){
			String threadName=Thread.currentThread().getName();
			System.out.println(threadName+" will take the lock");
			try {
				synchronized (lock) {
					System.out.println(threadName+" taking the lock");
					Thread.sleep(5000);
					System.out.println("Finish the work:"+threadName);
				}
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
	
	private class ThreadNotSleep extends Thread{
		@Override
		public void run(){
			String threadName=Thread.currentThread().getName();
			System.out.println(threadName+" will take the lock time="+System.currentTimeMillis());
			synchronized (lock) {
				System.out.println(threadName+" taking the lock time="+System.currentTimeMillis());
				System.out.println("Finish the work:"+threadName);
			}
		}
	}
}
