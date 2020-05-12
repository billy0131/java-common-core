package com.ecwalk.common.other.thread.JMM;

public class JMMTest {
	//主内存中 volatile MESI
	//lock指令（汇编指令）
	//1.重排序
	//2.强制修改的值立马写入内存
	//3.写操作，导致其他的CPU对应的缓存失效
	private volatile boolean flag=false;
	
	
	public static void main(String[] args) {
		JMMTest jmmTest=new JMMTest();//对象
		new Thread(
				()->{//run方法
					System.out.println("Tread1--start");
					while(!jmmTest.flag){//read->load->use
						
					}
					System.out.println("Tread1--end");
				}).start();
		
		new Thread(
				()->{//run方法
					//read 读到内存
					//load 加载到内存
					System.out.println("Tread2--start");
					jmmTest.flag=true;//read->load->use->assgin->store->write
					System.out.println("Tread2--end");
				}).start();
	}

}
