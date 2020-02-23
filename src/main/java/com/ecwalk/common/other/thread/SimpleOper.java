package com.ecwalk.common.other.thread;

public class SimpleOper {

	private volatile long count=0;//计数器

	/**
	 * 普通加锁 先保证本地锁安全 再考虑分布式
	 */
	public synchronized void intCount(){
		count=count+1;
	}

	private static class Count extends Thread{
		private SimpleOper simpleOper;

		public Count(SimpleOper simpleOper){
			this.simpleOper=simpleOper;
		}

		@Override
		public void run(){
			for(int i=0;i<10000;i++){
				simpleOper.intCount();
			}
		}
	}


	public static void main(String[] args)throws InterruptedException{
		SimpleOper 	simpleOper=new SimpleOper();
		//启动2个线程
		Count count1=new Count(simpleOper);
		Count count2=new Count(simpleOper);
		count1.start();
		count2.start();
		Thread.sleep(50);
		System.out.println(simpleOper.count);
			
	}
}
