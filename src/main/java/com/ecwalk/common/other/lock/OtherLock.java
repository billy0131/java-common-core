package com.ecwalk.common.other.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 手写jdk锁
 * jvm引用 强-软-弱-虚
 * jvm判断回收- GCROOTS
 * 强引用-女儿 OOM 不会断掉
 * 软引用-老婆 GC垃圾回收 离婚 OOM 离婚 防止GC
 * 弱引用-女朋友 一旦GC回收 ThreadLock 
 * 虚引用-加了微信-随时断掉
 * HTTP 三次握手 保证双全工
 * @author billy
 *
 */
public class OtherLock implements Lock{
	//线程安全 唯一性 原子性
	AtomicReference<Thread> owner=new AtomicReference<>();
	//等待队列 可重入锁，使用了抽象类 加锁+1 解锁-1
	LinkedBlockingQueue<Thread> waiter=new LinkedBlockingQueue<>();

	/**
	 * 实现加锁方法
	 */
	@Override
	public void lock() {
		//原子性 如果不为空 期望为空，变更为当前线程
		while(!owner.compareAndSet(null, Thread.currentThread())){
			waiter.add(Thread.currentThread());//加入等待列表
			LockSupport.park();//这个方法 让当前线程阻塞
			waiter.remove(Thread.currentThread());//remove 让它走GC
		}
		
	}
	
	@Override
	public void unlock() {
		//解锁的时候，应该是持有锁的线程才能成功，其他都是失败
		if(owner.compareAndSet(Thread.currentThread(), null)){
			//唤醒其他的等待线程
			for(Object object:waiter.toArray()){
				Thread next=(Thread) object;
				LockSupport.unpark(next);//唤醒线程
			}
		}
		
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
