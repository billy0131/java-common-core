package com.ecwalk.common.other.thread;

public class StartAndRun {

	public static class ThreadRun extends Thread{
		
		@Override
		public void run(){
			int i=90;
			while(i>0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("i am "+Thread.currentThread().getName()+" and now the i="+i--);
			}
		}
	}
	
	public static void main (String[] args) {
		ThreadRun beCalled=new ThreadRun();
		beCalled.setName("BeCalled");
		beCalled.setPriority(1);//线程优先级
		beCalled.run();//run只是执行这个线程 当前在main这个方法的栈帧 i am main
		beCalled.start();//start是开始这个线程，才会在ThreadRun i am BeCalled
	}
}
