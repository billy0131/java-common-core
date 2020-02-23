package com.ecwalk.common.other.thread.readWriteLock;

import java.util.concurrent.TimeUnit;

public class UseSync implements GoodService{

	private GoodInfo goodInfo;

	public UseSync(GoodInfo goodInfo){
		this.goodInfo=goodInfo;
	}

	@Override
	public synchronized GoodInfo getNum() {
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return this.goodInfo;
	}

	@Override
	public synchronized void setNum(int number) {
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		goodInfo.changeNumber(number);

	}

}
