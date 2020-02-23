package com.ecwalk.common.other.designPattern.template;

import java.util.Date;

public abstract class SendCustom {
	
	public abstract void to();
	
	public abstract void from();
	
	public abstract void content();
	
	public abstract void send();
	
	public  void date(){
		System.out.println(new Date());
	}
	//框架方法
	public void sendMessage(){
		to();
		from();
		content();
		date();
		send();
	}

}
