package com.ecwalk.common.other.thread.safe;

public class EndThread {
	
	private static class UseThread extends Thread{
		
		private boolean cancel=false;
		
		public void cancel(){
			cancel=true;
		}
		
		public UseThread(String name){
			super(name);
		}
		
		@Override
		public void run(){
			String threadName=Thread.currentThread().getName();
			while(!isInterrupted()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO InterruptedException抛出 要手动interrupt
					interrupt();
					e.printStackTrace();
				}
				System.out.println(threadName+" is run");
			}
			System.out.println(threadName+" interrput "+isInterrupted());
		}
	}

	public static void main (String[] args) throws InterruptedException{
		Thread endThread=new UseThread("endThread");
		endThread.setDaemon(true);
		endThread.start();
		Thread.sleep(500);
		endThread.interrupt();
	}
}
