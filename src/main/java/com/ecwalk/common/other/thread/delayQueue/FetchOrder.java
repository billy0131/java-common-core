package com.ecwalk.common.other.thread.delayQueue;

import java.util.concurrent.DelayQueue;

/**
 * 取出到期订单的功能
 * @author billy
 *
 */
public class FetchOrder implements Runnable{
	
	private DelayQueue<ItemVo<Order>> queue;

	@Override
	public void run() {
		while(true){
			try {
				ItemVo<Order> itemVo=queue.take();
				Order order=(Order)itemVo.getDate();
				System.out.println("get from queue:"+order.getOrderNo());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
		super();
		this.queue = queue;
	}

}
