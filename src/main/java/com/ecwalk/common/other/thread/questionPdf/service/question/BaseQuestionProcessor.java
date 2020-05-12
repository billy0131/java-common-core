package com.ecwalk.common.other.thread.questionPdf.service.question;

import java.util.Random;

import com.ecwalk.common.other.thread.questionPdf.assist.SL_Busi;

public class BaseQuestionProcessor {

	public static String makeQuestion(Integer questionId,String questionSrc){
		Random random=new Random();
		SL_Busi.business(450+random.nextInt(100));
		return "CompleteQuestion[id="+questionId+" content=:"+questionSrc+"]";
	}
}
