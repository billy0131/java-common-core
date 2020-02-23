package com.ecwalk.common.other.thread.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSelftLock {
	public void test() throws InterruptedException{
		final Lock lock=new SelfLock();
		class Worker extends Thread{
			@Override
			public void run(){
				while(true){
					lock.lock();
					try {
						TimeUnit.SECONDS.sleep(1);
						System.out.println(Thread.currentThread().getName());
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} finally {
						lock.unlock();
					}
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		//启动10个线程
		for(int i=0;i<10;i++){
			Worker worker=new Worker();
			worker.setDaemon(true);
			worker.start();
		}
		//主线程每隔1秒换行
		for(int i=0;i<10;i++){
			TimeUnit.SECONDS.sleep(1);
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestSelftLock testSelftLock=new TestSelftLock();
		testSelftLock.test();
	}

}
