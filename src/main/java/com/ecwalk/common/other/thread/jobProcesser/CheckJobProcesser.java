package com.ecwalk.common.other.thread.jobProcesser;

import java.util.concurrent.DelayQueue;

import com.ecwalk.common.other.thread.delayQueue.ItemVo;

public class CheckJobProcesser {
	
	//存放任务队列
	private static DelayQueue<ItemVo<String>> queue=new DelayQueue<ItemVo<String>>();
	
	//单例模式
	private CheckJobProcesser(){}
	
	private static class ProcesserHolder {
		public static CheckJobProcesser processer=new CheckJobProcesser();
	}
	
	public static CheckJobProcesser getInstance(){
		return ProcesserHolder.processer;
	}
	//单例模式
	
	//处理队列中到期任务线程
	private static class FetchJob implements Runnable{
		@Override
		public void run() {
			while(true){
				try {
					ItemVo<String> itemVo=queue.take();
					String jobName=itemVo.getDate();
					PendingJobPool.getMap().remove(jobName);
					System.out.println(jobName+" is out of date,remove from map!");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void putJob(String jobName,long expireTime){
		ItemVo<String> itemVo=new ItemVo<String>(expireTime, jobName);
		queue.offer(itemVo);//推进队列
		System.out.println("Job["+jobName+"已经放入了过期检查缓存，过期时长："+expireTime+"]");
	}

	static{
		Thread thread=new Thread(new FetchJob());
		thread.setDaemon(true);//设置守护线程
		thread.start();
		System.out.println("开启任务过期检查守护线程");
	}
}
