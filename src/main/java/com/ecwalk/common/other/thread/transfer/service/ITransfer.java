package com.ecwalk.common.other.thread.transfer.service;

import com.ecwalk.common.other.thread.transfer.UserAccount;

public interface ITransfer {
	
	/**
	 * 
	 * @param from 转出账户
	 * @param to 转入
	 * @param amount 金额
	 * @throws InterruptedException
	 */
	void transfer(UserAccount from,UserAccount to,int amount) throws InterruptedException;

}
