package com.ecwalk.common.other.thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInt {
	static AtomicInteger atomicInteger=new AtomicInteger(10);
	public static void main (String[] args){
		System.out.println(atomicInteger.getAndIncrement());//10->11 返回10
		System.out.println(atomicInteger.incrementAndGet());//11->12 返回12
		System.out.println(atomicInteger.get());
	}
}
