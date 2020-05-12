package com.ecwalk.common.other.thread.questionPdf.assist;

public class SL_Busi {
	
	public static void business(int sleepTime){
		try {
			Thread.sleep(sleepTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
