package com.ecwalk.common.other.thread.eb;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadFlow {

	private static class DepotOper implements Callable<Boolean>{

		private Business business;

		public DepotOper(Business business){
			this.business=business;
		}

		@Override
		public Boolean call() throws Exception {
			business.depotOper();
			return true;
		}

	}

	private static class SendMail implements Runnable {

		private Business business;

		public SendMail(Business business){
			this.business=business;
		}

		@Override
		public void run() {
			business.sendMail();
		}

	}

	private static class SendSms  implements Runnable {
		private Business business;

		public SendSms(Business business){
			this.business=business;
		}

		@Override
		public void run() {
			business.sendSms();
		}
	}
	
	public static void main(String[] args)throws ExecutionException,InterruptedException{
		long start=System.currentTimeMillis();
		Business business=new Business();
		//减库存业务返回结果的占位类
		FutureTask<Boolean> futureTask=new FutureTask<>(new DepotOper(business));
		//负责减库存的业务线程
		Thread depotOper=new Thread(futureTask);
		
		Thread sendMail=new Thread(new SendMail(business));
		
		Thread sendSms=new Thread(new SendSms(business));
		
		depotOper.start();
		if(futureTask.get()){
			System.out.println("修改库存结果"+futureTask.get());
			sendMail.start();
			sendSms.start();
		}
		System.out.println("共耗时"+(System.currentTimeMillis()-start)+"ms");
	}
}
