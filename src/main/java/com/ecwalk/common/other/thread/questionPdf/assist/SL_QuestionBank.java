package com.ecwalk.common.other.thread.questionPdf.assist;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.ecwalk.common.other.thread.questionPdf.vo.QuestionInDBVo;

public class SL_QuestionBank {
	
	private static ConcurrentHashMap<Integer, QuestionInDBVo> questionBankMap=
			new ConcurrentHashMap<>();
	
	private static ScheduledExecutorService updateQuestionBank=new ScheduledThreadPoolExecutor(1);

	public static void initBank(){
		for(int i=0;i<Consts.SIZE_OF_QUESTION_BANK;i++){
			String questionContent=makeQuestionDetail(Consts.QUESTION_LENGTH);
			questionBankMap.put(i, new QuestionInDBVo(i, questionContent));
		}
	}
	
	private static String makeQuestionDetail(int length){
		String base="abcdefghijklmnopqrstuvwxyz0123456789";
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			int number=random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static QuestionInDBVo getQuestion(int i){
		SL_Busi.business(20);
		return questionBankMap.get(i);
	}
	
	private static class UpdateBank implements Runnable{

		@Override
		public void run() {
			Random random=new Random();
			int questionId=random.nextInt(Consts.SIZE_OF_QUESTION_BANK);
			String questionConent=makeQuestionDetail(Consts.QUESTION_LENGTH);
			questionBankMap.put(questionId, new QuestionInDBVo(questionId,questionConent));
			System.out.println("题目【"+questionId+"被更新】");
		}
		
	}
	
	private static void updateQuestionTimer(){
		System.out.println("开始定时更新题库");
		updateQuestionBank.scheduleAtFixedRate(new UpdateBank(), 15, 5, TimeUnit.SECONDS);
	}
}
