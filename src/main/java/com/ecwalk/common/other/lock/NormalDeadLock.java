package com.ecwalk.common.other.lock;

public class NormalDeadLock {
	
	private static Object No13=new Object();//第一个锁
	private static Object No14=new Object();//第二个锁
	
	//第一个拿锁
	private static void  jamesDo() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		synchronized (No13) {
			System.out.println(threadName+"get No13");
			Thread.sleep(100);
			synchronized (No14) {
				System.out.println(threadName+"get No14");	
			}
		}
	}

//	//第二个拿锁 死锁
//	private static void  lisonDo() throws InterruptedException{
//		String threadName=Thread.currentThread().getName();
//		synchronized (No14) {
//			System.out.println(threadName+"get No14");
//			Thread.sleep(100);
//			synchronized (No13) {
//				System.out.println(threadName+"get No13");	
//			}
//		}
//	}
	
	//第二个拿锁 强制拿锁顺序 解决锁
	private static void  lisonDo() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		synchronized (No13) {
			System.out.println(threadName+"get No13");
			Thread.sleep(100);
			synchronized (No14) {
				System.out.println(threadName+"get No14");	
			}
		}
	}
	
	private static class James extends Thread{
		private String name;
		
		public James(String name){
			this.name=name;
		}
		
		@Override
		public void run(){
			Thread.currentThread().setName(name);
			try {
				jamesDo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		//主线程
		Thread.currentThread().setName("Lison");
		James james=new James("James");
		james.start();
		lisonDo();
	}
}
