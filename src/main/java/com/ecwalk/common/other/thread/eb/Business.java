package com.ecwalk.common.other.thread.eb;

public class Business {
	
	public void sleepUtils(int sleepTimes){
		try{
			Thread.sleep(sleepTimes);
		}catch(InterruptedException e){
			
		}
	}
	
	public void depotOper(){
		System.out.println("库存已减");
		sleepUtils(30);
	}
	
	public void sendMail(){
		System.out.println("邮件已发送");
		sleepUtils(100);
	}

	public void sendSms(){
		System.out.println("短信已发送");
		sleepUtils(100);
	}
}
