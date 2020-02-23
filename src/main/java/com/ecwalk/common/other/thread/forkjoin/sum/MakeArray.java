package com.ecwalk.common.other.thread.forkjoin.sum;

import java.util.Random;

public class MakeArray {
	
	public static final int ARRAY_LENGTH=4000;
	public static Random random=new Random();
	
	public static int[] makeArray(){
		int[] result=new int[ARRAY_LENGTH];
		for(int i=0;i<ARRAY_LENGTH;i++){
			result[i]=random.nextInt(ARRAY_LENGTH*3);
		}
		return result;
	}

}
