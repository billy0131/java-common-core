package com.ecwalk.common.other.thread.readWriteLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseRwLock implements GoodService{
	
	private final ReadWriteLock lock=new ReentrantReadWriteLock();
	private final Lock getLock=lock.readLock();//读锁
	private final Lock setLock=lock.writeLock();//写锁
	private GoodInfo goodInfo;

	public UseRwLock(GoodInfo goodInfo){
		this.goodInfo=goodInfo;
	}

	@Override
	public  GoodInfo getNum() {
		getLock.lock();
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}finally {
			getLock.unlock();
		}
		return this.goodInfo;
	}

	@Override
	public  void setNum(int number) {
		setLock.lock();
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}finally {
			setLock.unlock();
		}
		goodInfo.changeNumber(number);

	}



}
