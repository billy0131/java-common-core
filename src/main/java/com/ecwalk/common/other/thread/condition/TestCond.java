package com.ecwalk.common.other.thread.condition;

import com.ecwalk.common.other.thread.wn.Express;

public class TestCond {
	
	private static Express express=new Express(0, Express.CITY);
	/**
	 * 检查里程数变化的线程，不满足条件，线程一直等待
	 * @author billy
	 *
	 */
	private static class CheckKm extends Thread{
		@Override
		public void run(){
			express.waitKmCond();
		}
	}
	
	/**
	 * 检查地点变化的线程，不满足条件，线程一直等待
	 * @author billy
	 *
	 */
	private static class CheckSite extends Thread{
		@Override
		public void run(){
			express.waitSiteCond();
		}
	}
	
	public static void main (String[] args) throws InterruptedException{
		for(int i=0;i<3;i++){
			new CheckSite().start();
		}
		for(int i=0;i<3;i++){
			new CheckKm().start();
		}
		Thread.sleep(1000);
		express.changeKmCond();//里程变化
		
	}

}
