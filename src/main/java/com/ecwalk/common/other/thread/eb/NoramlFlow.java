package com.ecwalk.common.other.thread.eb;

public class NoramlFlow {

	public static void main(String[] args){
		long start=System.currentTimeMillis();
		Business business=new Business();
		business.depotOper();
		business.sendMail();
		business.sendSms();
		System.out.println("共耗时"+(System.currentTimeMillis()-start)+"ms");
	}
}
