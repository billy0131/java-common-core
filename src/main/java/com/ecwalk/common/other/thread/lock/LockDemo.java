package com.ecwalk.common.other.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
	
	private Lock lock=new ReentrantLock();
	private int count;
	
	public  void increament(){
		lock.lock();
		try {
			count++;
		}finally {
			lock.unlock();
		}
	}
	
	public synchronized void incr2(){
		count++;
		incr2();
	}
	
	public synchronized void incr3(){
		incr2();
	}

}
