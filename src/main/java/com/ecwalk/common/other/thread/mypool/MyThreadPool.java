package com.ecwalk.common.other.thread.mypool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import oracle.net.aso.r;

public class MyThreadPool {
	//默认线程数5
	private static int WORK_NUM=5;
	//默认任务100
	private static int TASK_COUNT=100;
	//工作线程
	private WorkThread[] workThreads;
	//任务队列，作为一个缓冲
	private final BlockingQueue<Runnable> taskQueue;
	//用户在构造这个池，希望启动的线程数
	private final int worker_num;



	public MyThreadPool() {
		this(WORK_NUM,TASK_COUNT);
	}



	public MyThreadPool(int worker_num,int taskCount) {
		if(worker_num<=0){
			worker_num=WORK_NUM;
		}
		if(taskCount<=0){
			taskCount=TASK_COUNT;
		}
		this.worker_num = worker_num;
		taskQueue=new ArrayBlockingQueue<>(taskCount);
		workThreads=new WorkThread[worker_num];
		for(int i=0;i<worker_num;i++){
			workThreads[i]=new WorkThread();
			workThreads[i].start();
		}
		
	}

	public void execute(Runnable task){
		try {
			taskQueue.put(task);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void destory(){
		System.out.println("ready close pool");
		for(int i=0;i<worker_num;i++){
			workThreads[i].stopWorker();
			workThreads[i]=null;
		}
		taskQueue.clear();
	}

	private class WorkThread extends Thread{
		@Override
		public void run(){
			Runnable runnable=null;
			while(!isInterrupted()){
				try {
					runnable=taskQueue.take();
					if(runnable!=null){
						System.out.println(getId()+" ready exec:"+runnable);
						runnable.run();
					}
					runnable=null;//help gc;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void stopWorker(){
			interrupt();
		}
	}

}
