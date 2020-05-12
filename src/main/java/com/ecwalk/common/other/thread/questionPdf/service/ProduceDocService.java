package com.ecwalk.common.other.thread.questionPdf.service;

import java.util.Random;

import com.ecwalk.common.other.thread.questionPdf.assist.SL_Busi;
import com.ecwalk.common.other.thread.questionPdf.service.question.SingleQstService;
import com.ecwalk.common.other.thread.questionPdf.vo.SrcDocVo;

public class ProduceDocService {
	
	public static String upLoadDoc(String docFileName){
		Random random=new Random();
		SL_Busi.business(9000+random.nextInt(400));
		return "http://www.xxx.com/file/upload/"+docFileName;
	}
	
	public static String makeDoc(SrcDocVo pendingDocVo){
		System.out.println("开始处理文档"+pendingDocVo.getDocName());
		StringBuffer sBuffer=new StringBuffer();
		for(Integer questionId:pendingDocVo.getQuestionList()){
			sBuffer.append(SingleQstService.makeQuestion(questionId));
		}
		return "complete_"+System.currentTimeMillis()+"_"+pendingDocVo.getDocName()+".pdf";
	}

}
