package com.ecwalk.common.other.thread.mypool;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletionCase {
	
	private final int POOL_SIZE=Runtime.getRuntime().availableProcessors();
	private final int TOTAL_TASK=Runtime.getRuntime().availableProcessors()*1000;
	
	public void testByCompletion()throws Exception{
		long start=System.currentTimeMillis();
		AtomicInteger count=new AtomicInteger(0);
		
		ExecutorService pool=Executors.newFixedThreadPool(POOL_SIZE);
		CompletionService<Integer> completionService=new ExecutorCompletionService<>(pool);
		for(int i=0;i<TOTAL_TASK;i++){
			completionService.submit(new WorkTask("ExecTask"+i));
		}
		for(int i=0;i<TOTAL_TASK;i++){
			int sleptTime=completionService.take().get();
			System.out.println(" slept "+sleptTime+" ms");
			count.addAndGet(sleptTime);
		}
		pool.shutdown();
		System.out.println(" tasks sleep time "+count.get()+" ms ,and spend time "+(System.currentTimeMillis()-start)+" ms");
	}
	
	public static void main(String[] args) throws Exception {
		CompletionCase tCase=new CompletionCase();
		tCase.testByCompletion();
	}

}
