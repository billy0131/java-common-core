package com.ecwalk.common.other.thread.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

public class UseExchange {
	
	private static final Exchanger<Set<String>> exchange=new Exchanger<Set<String>>();
	
	public static void main(String[] args){
		//第一个线程
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Set<String> setA=new HashSet<String>(); //存放数据的容器
				try {
					//添加数据
					setA=exchange.exchange(setA);//交换数据
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
		
		//第二个线程
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Set<String> setB=new HashSet<String>(); //存放数据的容器
				try {
					//添加数据
					setB=exchange.exchange(setB);//交换数据
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	}
}
