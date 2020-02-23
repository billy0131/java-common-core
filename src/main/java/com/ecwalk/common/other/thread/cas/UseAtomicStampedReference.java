package com.ecwalk.common.other.thread.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

public class UseAtomicStampedReference {
	
	static AtomicStampedReference<String> asr=new AtomicStampedReference<String>("Billy", 0);//值和初始化版本号
	
	public static void main (String[] args) throws InterruptedException{
		final int oldStam=asr.getStamp();//拿初始的版本号
		final String oldReference=asr.getReference();
		
		System.out.println(oldReference+"====="+oldStam);
		
		Thread rightStampThread=new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()
						+" 当前变量值:"+oldReference+" 当前版本"+oldStam+"-"
						+asr.compareAndSet(oldReference, oldReference+"Java", oldStam, oldStam+1));
				
			}
		});
		
		Thread errorStampThread=new Thread(new Runnable() {
			@Override
			public void run() {
				//compareAndSet 版本号一样 返回false
				System.out.println(Thread.currentThread().getName()
						+" 当前变量值:"+asr.getReference()+" 当前版本"+asr.getStamp()+"-"
						+asr.compareAndSet(asr.getReference(), asr.getReference()+"C", oldStam, oldStam+1));
				
			}
		});
		
		rightStampThread.start();
		rightStampThread.join();//等待rightStampThread执行完才执行errorStampThread
		errorStampThread.start();
		
		System.out.println(asr.getReference()+"====="+asr.getStamp());
	}

}
