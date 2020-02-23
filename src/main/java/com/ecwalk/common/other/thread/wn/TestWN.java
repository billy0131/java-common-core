package com.ecwalk.common.other.thread.wn;

public class TestWN {
	private static Express express=new Express(0, Express.CITY);
	
	/*
	 * 检查里程数，不满足条件，一直等待
	 */
	private static class CheckKm extends Thread{
		@Override
		public void run(){
			express.waitKm();
		}
	}
	
	private static class CheckSite extends Thread{
		@Override
		public void run(){
			express.waitSite();
		}
	}
	
	public static void main (String[] args) throws InterruptedException{
		for(int i=0;i<3;i++){
			new CheckSite().start();
		}
		for(int i=0;i<3;i++){//里程数变化
			new CheckKm().start();
		}
		Thread.sleep(1000);
		express.changKm();
	}
}
