package com.ecwalk.common.other.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MainProcess {
	
	public static void main (String[] args){
		//java虚拟机的线程管理接口
		ThreadMXBean threadMXBean=ManagementFactory.getThreadMXBean();
		//获取线程信息的方法
		ThreadInfo[] threadInfos=threadMXBean.dumpAllThreads(false, false);
//		7:JDWP Command Reader 守护线程
//		6:JDWP Event Helper Thread 守护线程
//		5:JDWP Transport Listener: dt_socket 守护线程
//		4:Signal Dispatcher 守护线程
//		3:Finalizer 守护线程
//		2:Reference Handler 守护线程
//		1:main 用户线程
		for(ThreadInfo threadInfo:threadInfos){
			System.out.println(threadInfo.getThreadId()+":"+threadInfo.getThreadName());
		}
		
		UseRun useRun=new UseRun();
		new Thread(useRun).start();
		Thread t=new Thread(useRun);
		t.interrupt();//中断一个线程，并不是强心关闭，中断标志位为true
		t.isInterrupted();// 判断是否处于中断状态
		t.interrupted();//判断是否处于中断状态 中断标志位为false
		
	}
	
	private static class UseRun implements Runnable{

		@Override
		public void run() {
			System.out.println("i am implements Runnable");
			
		}
		
	}

}
