package com.ecwalk.common.other.thread.safe;

public class EndRunnable {
	
	private static class UseRunnable implements Runnable{
		
		@Override
		public void run(){
			String threadName=Thread.currentThread().getName();
			while(!Thread.currentThread().isInterrupted()){
				System.out.println(threadName+" is run");
			}
			System.out.println(threadName+" interrput "+Thread.currentThread().isInterrupted());
		}
	}

	public static void main (String[] args) throws InterruptedException{
		UseRunnable useRunnable=new UseRunnable();
		Thread endThread=new Thread(useRunnable,"endThread");
		endThread.interrupt();
		Thread.sleep(20);
		endThread.interrupt();
	}
}
