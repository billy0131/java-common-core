package com.ecwalk.common.other.thread.vola;

import java.util.concurrent.TimeUnit;

public class VolatileUnsafe {
	
	private static class VolatileVar implements Runnable{
		
		private volatile int a=0;

		@Override
		public void run() {
			String threadName=Thread.currentThread().getName();
			a=a+1;
			System.out.println(threadName+"===="+a);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			a=a+1;
			System.out.println(threadName+"===="+a);
		}
		
	}
	
	public static void main(String[] args){
		VolatileVar v=new VolatileVar();
		
		Thread t1=new Thread(v);
		Thread t2=new Thread(v);
		Thread t3=new Thread(v);
		Thread t4=new Thread(v);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

}
