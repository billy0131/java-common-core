package com.ecwalk.common.other.designPattern.template;

public class SendSms extends SendCustom{

	@Override
	public void to() {
		System.out.println("Billy");
		
	}

	@Override
	public void from() {
		System.out.println("Jia");
		
	}

	@Override
	public void content() {
		System.out.println("Love");
		
	}

	@Override
	public void send() {
		System.out.println("Send sms");
		
	}
	
	public static void main(String[] args) {
		SendCustom sendCustom=new SendSms();
		sendCustom.sendMessage();
	}

}
