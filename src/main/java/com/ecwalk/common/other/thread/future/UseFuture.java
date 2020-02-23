package com.ecwalk.common.other.thread.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import oracle.net.aso.r;

public class UseFuture {
	
	static Random random=new Random();
	
	/**
	 * 实现Callable接口，允许返回值
	 * @author billy
	 *
	 */
	private static class UseCallable implements Callable<Integer>{
		private int sum;
		@Override
		public Integer call() throws Exception {
			System.out.println("Callable子线程开始计算");
			Thread.sleep(2000);
			for(int i=0;i<5000;i++){
				sum=sum+i;
			}
			System.err.println("Callable子线程计算完成，结果="+sum);
			return sum;
		}
		
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		UseCallable useCallable=new UseCallable();
		FutureTask<Integer> futureTask=new FutureTask<Integer>(useCallable);
		new Thread(futureTask).start();
		TimeUnit.SECONDS.sleep(1);
		if(random.nextBoolean()){//随机决定获得结果还是终止任务
			System.out.println("Get UseCallable result="+futureTask.get());
		}else{
			System.out.println("中断计算");
			futureTask.cancel(true);
		}
	}
}
