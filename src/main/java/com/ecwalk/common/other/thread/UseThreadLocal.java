package com.ecwalk.common.other.thread;

import java.util.HashMap;
import java.util.Map;

public class UseThreadLocal {

	static ThreadLocal<Integer> threadLocal=new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue(){
			return 1;
		}
	};
	
	Map<Thread, Integer> threadMap=new HashMap<Thread, Integer>();//threadLocal

	public void StartThreadArray(){
		Thread[] runs=new Thread[3]	;
		for(int i=0;i<runs.length;i++){
			runs[i]=new Thread(new TestThread(i));
		}
		for(int i=0;i<runs.length;i++){
			runs[i].start();
		}
	}
	
	public static class TestThread implements Runnable{
		int id;
		public TestThread(int id){
			this.id=id;
		}
		@Override
		public void run(){
			System.out.println(Thread.currentThread().getName()+":start");
			Integer s=threadLocal.get();//获得变量的值
			s=s+id;
			threadLocal.set(s);
			System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
		}
	}
	
	public static void main(String[] args){
		UseThreadLocal test=new UseThreadLocal();
		test.StartThreadArray();
	}

}
