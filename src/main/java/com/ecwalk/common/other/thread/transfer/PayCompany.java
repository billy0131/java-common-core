package com.ecwalk.common.other.thread.transfer;

import com.ecwalk.common.other.thread.transfer.service.ITransfer;
import com.ecwalk.common.other.thread.transfer.service.SafeTransferTry;

public class PayCompany {
	
	//提高转账速度，每一笔转账业务，由线程执行
	private static class TransferThread extends Thread{
		private String name;
		
		private UserAccount from;
		
		private UserAccount to;
		
		private int amount;
		
		private ITransfer transfer;
		
		public TransferThread (String name,UserAccount from,UserAccount to,int amount,ITransfer transfer){
			this.name=name;
			this.from=from;
			this.to=to;
			this.amount=amount;
			this.transfer=transfer;
		}
		
		public void run(){
			Thread.currentThread().setName(name);
			try {
				transfer.transfer(from, to, amount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		UserAccount zhangsan=new UserAccount("张三", 20000);
		
		UserAccount lisi=new UserAccount("李四", 20000);
		
		ITransfer transfer=new SafeTransferTry();
		
		TransferThread zhangsanToLisi=new TransferThread("张三_to_李四", zhangsan, lisi, 2000, transfer);
		
		TransferThread lisiToZhangsan=new TransferThread("李四_to_张三", lisi, zhangsan, 4000, transfer);
		
		zhangsanToLisi.start();
		lisiToZhangsan.start();
	}
}
