package com.ecwalk.common.other.thread.tools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier {

	private static ConcurrentHashMap<String, Long> resultMap=new ConcurrentHashMap<>();//存放子线程工作集的容器
	
	static Random random=new Random();
	
	private static CyclicBarrier barrier=new CyclicBarrier(5,new CollectThread());

	public static void main(String[] args){
		for(int i=0;i<=4;i++){
			Thread thread=new Thread(new SubThread());
			thread.start();
		}
	}
	
	/**
	 * 负责屏障开放后的工作
	 * @author billy
	 *
	 */
	private static class CollectThread implements Runnable{

		@Override
		public void run() {
			StringBuilder result=new StringBuilder();
			for(Map.Entry<String, Long> workResult:resultMap.entrySet()){
				result.append("["+workResult.getValue()+"]");
			}
			System.out.println("the result ="+result);
			System.out.println("do other business");
		}
		
	}

	private static class SubThread implements Runnable{

		@Override
		public void run() {
			long id =Thread.currentThread().getId();
			resultMap.put(id+"",id);
			try{
				if(random.nextBoolean()){
					Thread.sleep(2000+id);
					System.out.println("Thread_"+id+"...do something");
				}
				System.out.println("Thread_"+id+"...is await");
				barrier.await();
				Thread.sleep(1000+id);
				System.out.println("Thread_"+id+"...do its business");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}

}
