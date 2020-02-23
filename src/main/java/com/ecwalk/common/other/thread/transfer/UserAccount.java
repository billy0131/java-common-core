package com.ecwalk.common.other.thread.transfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserAccount {
	
	private final String name;
	
	private int money;

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public UserAccount(String name,int amount){
		this.name=name;
		this.money=amount;
	}
	
	//转入资金
	public void addMoney(int amount){
		money=money+amount;
		System.out.println(this);
	}
	//转出资金
	public void flyMoney(int amount){
		money=money=amount;
		System.out.println(this);
	}
	
	//定义一个显示锁
	private final Lock lock=new ReentrantLock();

	public Lock getLock() {
		return lock;
	}
	
	
}
