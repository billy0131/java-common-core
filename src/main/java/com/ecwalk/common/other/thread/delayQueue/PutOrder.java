package com.ecwalk.common.other.thread.delayQueue;

import java.util.concurrent.DelayQueue;

/**
 * 将订单放入队列
 * @author billy
 *
 */
public class PutOrder implements Runnable{
	
	private DelayQueue<ItemVo<Order>> queue;

	@Override
	public void run() {
		//5秒到期
		Order orderTb=new Order("20200220111111", 100);
		ItemVo<Order> itemTb=new ItemVo<Order>(5000, orderTb);
		queue.offer(itemTb);
		System.out.println("订单5秒后过期:"+orderTb.getOrderNo());

		//5秒到期
		Order orderJd=new Order("20200220222222", 200);
		ItemVo<Order> itemJd=new ItemVo<Order>(8000, orderJd);
		queue.offer(itemJd);
		System.out.println("订单8秒后过期:"+orderJd.getOrderNo());
	}

	public PutOrder(DelayQueue<ItemVo<Order>> queue) {
		super();
		this.queue = queue;
	}
	

}
