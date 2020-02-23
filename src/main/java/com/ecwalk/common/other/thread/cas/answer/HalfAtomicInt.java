package com.ecwalk.common.other.thread.cas.answer;

import java.util.concurrent.atomic.AtomicInteger;

public class HalfAtomicInt {
	
	private AtomicInteger atomicInteger=new AtomicInteger(0);
	
	public void increament(){
		for(;;){
			int i=atomicInteger.get();
			boolean suc=atomicInteger.compareAndSet(i, ++i);
			if(suc){
				break;
			}
		}
	}

}
