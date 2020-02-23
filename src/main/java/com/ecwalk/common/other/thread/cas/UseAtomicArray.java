package com.ecwalk.common.other.thread.cas;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class UseAtomicArray {
	
	static int[] value=new int[]{1,2};
	
	
	static AtomicIntegerArray ai=new AtomicIntegerArray(value);
	public static void main (String[] args){
		ai.getAndSet(0, 3);//改index为0的值为3
		System.out.println(ai.get(0));
		System.out.println(value[0]);
	}

}
