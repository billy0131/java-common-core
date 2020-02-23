package com.ecwalk.common.other.thread.wn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Express {
	

	public final static String CITY="GuangZhou";
	private int km;//快递运输里程数
	private String site;//快递到达地点
	private Lock lock=new ReentrantLock();
	private Condition keCond=lock.newCondition();
	private Condition siteCond=lock.newCondition();
	
	
	public Express() {
		
	}
	
	public Express(int km,String site) {
		this.km=km;
		this.site=site;
	}
	
	/**
	 * 变化公里数，然后通知处于wait的状态并需要处理公里数的线程进行业务处理
	 */
	public synchronized void changKm(){//上锁
		this.km=101;
//		notify(); 随机唤醒一个
		notifyAll();// 全部唤醒
	}
	
	/**
	 * 变化地点，然后通知处于wait的状态并需要处理公里数的线程进行业务处理
	 */
	public synchronized void changSite(){
		this.site="BeiJing";
//		notify();
		notifyAll();
	}
	
	public synchronized void waitKm(){
		while(this.km<=100){
			try {
				wait();
				System.out.println("check km thread["+Thread.currentThread().getId()+"] is be notified");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("the km is "+this.km);
	}
	
	public synchronized void waitSite(){
		while(CITY.equals(this.site)){
			try {
				wait();
				System.out.println("check site thread["+Thread.currentThread().getId()+"] is be notified");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("the site is "+this.site);
	}
	
	//=============Condition
	public void changeKmCond(){
		lock.lock();
		try {
			this.km=101;
			keCond.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public  void changeSiteCond(){
		lock.lock();
		try {
			this.site="BeiJing";
			siteCond.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public  void waitKmCond(){
		lock.lock();
		try {
			while(this.km<=100){
				try {
					keCond.await();
					System.out.println("check km thread["+Thread.currentThread().getId()+"] is be notified");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			lock.unlock();
		}

		System.out.println("the km is "+this.km+",i will change db");
	}
	
	public  void waitSiteCond(){
		lock.lock();
		try {
			while(CITY.equals(this.site)){
				try {
					siteCond.await();
					System.out.println("check site thread["+Thread.currentThread().getId()+"] is be notified");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			lock.unlock();
		}

		System.out.println("the site is "+this.site+",i will call user");
	}

}
