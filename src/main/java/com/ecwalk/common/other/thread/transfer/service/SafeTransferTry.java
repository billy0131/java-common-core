package com.ecwalk.common.other.thread.transfer.service;

import java.util.Random;

import com.ecwalk.common.other.thread.transfer.UserAccount;

/**
 * 死锁 活锁 数据不正确
 * 
 * @author billy
 *
 */
public class SafeTransferTry implements ITransfer{
	
	Random r=new Random();

	@Override
	public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
		String thread=Thread.currentThread().getName();
		
		while(true){
			if(from.getLock().tryLock()){
				System.out.println(Thread.currentThread().getName()+"get"+from.getName());
				try{
					if(to.getLock().tryLock()){
						try{
							System.out.println(Thread.currentThread().getName()+"get"+to.getName());
							from.flyMoney(amount);
							to.addMoney(amount);
							break;//成功才跳出循环
						}finally{
							to.getLock().unlock();
						}
					}
				}finally{
					from.getLock().unlock();
				}
			}
			//活锁
			Thread.sleep(r.nextInt(2));
		}
		
	}

}
