package com.ecwalk.common.other.thread.questionPdf.service.question;

import com.ecwalk.common.other.thread.questionPdf.assist.SL_QuestionBank;

public class SingleQstService {

	public static String makeQuestion(Integer questionId){
		return BaseQuestionProcessor.makeQuestion(questionId, SL_QuestionBank.getQuestion(questionId).getDetail());
		
	}
}
