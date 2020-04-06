package com.ecwalk.common.other.thread.mypool;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseThreadPool {
	
	static class Worker implements Runnable{
		
		private String taskName;
		private Random r=new Random();

		public String getTaskName() {
			return taskName;
		}

		public Worker(String taskName) {
			
			this.taskName = taskName;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()
					+" process the task :"+taskName);
			try {
				TimeUnit.MILLISECONDS.sleep(r.nextInt(100)+5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	static class CallWorker implements Callable<String>{
		
		private String taskName;
		private Random r=new Random();

		public CallWorker(String taskName) {
			
			this.taskName = taskName;
		}

		public String getTaskName() {
			return taskName;
		}

		@Override 
		public String call() throws Exception {
			System.out.println(Thread.currentThread().getName()
					+" process the task :"+taskName);
			return Thread.currentThread().getName()+":"+r.nextInt(100)*5;
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService pool=new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, 
				new ArrayBlockingQueue<>(10),
				new ThreadPoolExecutor.DiscardOldestPolicy());
//		ExecutorService pool=Executors.newCachedThreadPool();
		for(int i=0;i<6;i++){
			Worker worker=new Worker("worker_"+i);
			pool.execute(worker);
		}
		
		for(int i=0;i<6;i++){
			CallWorker callWorker=new CallWorker("callWorker_"+i);
			Future<String> result=pool.submit(callWorker);
			System.out.println(result.get());
		}
		pool.shutdown();
	}

}
