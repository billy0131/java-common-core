package com.ecwalk.common.other.thread.scheduledWork;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledCase {
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor=new ScheduledThreadPoolExecutor(1);
		
		scheduledThreadPoolExecutor.scheduleAtFixedRate(new ScheduledWorker(ScheduledWorker.Normal),
				1000, 3000, TimeUnit.MILLISECONDS);
		scheduledThreadPoolExecutor.scheduleAtFixedRate(new ScheduledWorker(ScheduledWorker.HasException),
				1000, 3000, TimeUnit.MILLISECONDS);
		scheduledThreadPoolExecutor.scheduleAtFixedRate(new ScheduledWorker(ScheduledWorker.ProcessException),
				1000, 3000, TimeUnit.MILLISECONDS);
	}
}
