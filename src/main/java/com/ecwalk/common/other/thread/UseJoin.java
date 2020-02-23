package com.ecwalk.common.other.thread;

import java.util.concurrent.TimeUnit;

public class UseJoin {
	static class JumpQueue implements Runnable{
		private Thread thread;//用来插队线程
		
		public JumpQueue(Thread thread){
			this.thread=thread;
		}
		

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"terminated ");
		}
		
		public static void main(String[] args) throws Exception{
			Thread previous=Thread.currentThread();//现在是主线程
			for(int i=0;i<10;i++){
				//i=0;previous主线程 ;i=1,previous是i=0这个线程
				Thread thread=new Thread(new JumpQueue(previous),String.valueOf(i));
				System.out.println(previous.getName()+" jump a queue the thread:"+thread.getName());
				thread.start();
				previous=thread;
			}
			
			TimeUnit.SECONDS.sleep(2);
			System.out.println(Thread.currentThread().getName()+" terminate");
		}
		
	}

}
