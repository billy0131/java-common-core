package com.ecwalk.common.other.thread.forkjoin.sum;

import java.util.concurrent.TimeUnit;

public class SumNormal {
	public static void main (String[] args){
		int count=0;
		int[] src=MakeArray.makeArray();
		
		long start=System.currentTimeMillis();
		for(int i=0;i<src.length;i++){
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count=count+src[i];
		}
		System.out.println("The count is "+count+" spend time:"+(System.currentTimeMillis()-start)+"ms");
	}
}
