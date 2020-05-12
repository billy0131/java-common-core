package com.ecwalk.common.other.thread.questionPdf.assist;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ecwalk.common.other.thread.questionPdf.vo.SrcDocVo;

public class CreatePendingDocs {

	public static List<SrcDocVo> makePendingDoc(int count){
		Random random=new Random();
		
		List<SrcDocVo> docList=new LinkedList<SrcDocVo>();
		for(int i=0;i<count;i++){
			List<Integer> questionList=new LinkedList<>();
			for(int j=0;j<Consts.QUESTION_COUNT_IN_DOC;j++){
				int questionId=random.nextInt(Consts.SIZE_OF_QUESTION_BANK);
				questionList.add(questionId);
			}
			SrcDocVo pendingDocVo=new SrcDocVo("pending_"+i,questionList);
			docList.add(pendingDocVo);
		}
		return docList;
	}
}
