package com.ecwalk.common.other.thread.mypool;

import java.util.Random;
import java.util.concurrent.Callable;

public class WorkTask implements Callable<Integer>{
	private String name;
	public WorkTask(String name) {
		super();
		this.name = name;
	}
	@Override
	public Integer call() throws Exception {
		int sleepTime=new Random().nextInt(1000);
		Thread.sleep(sleepTime);
		return sleepTime;
	}

}
