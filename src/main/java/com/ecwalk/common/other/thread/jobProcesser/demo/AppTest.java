package com.ecwalk.common.other.thread.jobProcesser.demo;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.ecwalk.common.other.thread.jobProcesser.PendingJobPool;
import com.ecwalk.common.other.thread.jobProcesser.vo.TaskResult;

import oracle.net.aso.r;

public class AppTest {
	
	private final static String JOB_NAME="计算数值";
	private final static int JOB_LENGTH=1000;

	private static class QueryResult implements Runnable{
		
		private PendingJobPool pool;

		public QueryResult(PendingJobPool pool) {
			super();
			this.pool = pool;
		}

		@Override
		public void run() {
			int i=0;
			while(i<350){
				List<TaskResult<String>> taskDetail=pool.getTaskDetail(JOB_NAME);
				if(!taskDetail.isEmpty()){
					System.out.println(pool.getTaskProcess(JOB_NAME));
					System.out.println(taskDetail);
				}
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		MyTask myTask=new MyTask();
		PendingJobPool pool=PendingJobPool.getInstance();
		pool.registerJob(JOB_NAME, JOB_LENGTH, myTask, 1000*5);
		Random random=new Random();
		for(int i=0;i<JOB_LENGTH;i++){
			pool.putTask(JOB_NAME, random.nextInt(1000));
		}
		Thread thread=new Thread(new QueryResult(pool));
		thread.start();
	}
}
