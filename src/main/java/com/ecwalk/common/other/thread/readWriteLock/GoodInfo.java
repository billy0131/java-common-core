package com.ecwalk.common.other.thread.readWriteLock;

public class GoodInfo {
	
	private final String name;
	private double totalMoney;
	private int storeNumber;
	
	
	public  GoodInfo(String name,double totalMoney,int storeNumber){
		this.name=name;
		this.storeNumber=storeNumber;
		this.totalMoney=totalMoney;
	}


	public double getTotalMoney() {
		return totalMoney;
	}


	public int getStoreNumber() {
		return storeNumber;
	}
	
	public void changeNumber(int sellNumber){
		this.totalMoney+=sellNumber*25;
		this.storeNumber-=sellNumber;
	}
}
