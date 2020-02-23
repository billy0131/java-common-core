package com.ecwalk.common.other.thread.readWriteLock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BusiApp {

	static final int readWriteRatio=10;//读写线程比例
	static final int minthreadCount=3;//最少线程数
	static Random random=new Random();
	
	//读操作
	private static class GetThread implements Runnable{
		private GoodService goodService;
		
		public GetThread(GoodService goodService){
			this.goodService=goodService;
		}
		@Override
		public void run() {
			long start=System.currentTimeMillis();
			for(int i=0;i<100;i++){
				goodService.getNum();
			}
			System.out.println(Thread.currentThread().getName()+" 读取商品数据耗时："+(System.currentTimeMillis()-start)+"ms");
		}
		
	}
	
	//写操作
	private static class SetThread implements Runnable{
		private GoodService goodService;
		
		public SetThread(GoodService goodService){
			this.goodService=goodService;
		}
		@Override
		public void run() {
			long start=System.currentTimeMillis();
			for(int i=0;i<10;i++){
				try {
					TimeUnit.MILLISECONDS.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goodService.setNum(random.nextInt(10));
			}
			System.out.println(Thread.currentThread().getName()+" 写商品数据耗时："+(System.currentTimeMillis()-start)+"ms");
		
			
		}
		
	}
	
	public static void main (String[] args) throws InterruptedException{
		GoodInfo goodInfo =new GoodInfo("Cup", 10000, 10000);
//		GoodService goodService=new UseSync(goodInfo);//synchronized
		GoodService goodService=new UseRwLock(goodInfo);//读写锁
		for(int i=0;i<minthreadCount;i++){
			Thread setT =new Thread(new SetThread(goodService));
			for(int j=0;j<readWriteRatio;j++){
				Thread getT=new Thread(new GetThread(goodService));
				getT.start();
			}
			TimeUnit.MILLISECONDS.sleep(100);
			setT.start();
		}
	}
}
