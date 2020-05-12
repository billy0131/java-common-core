package com.ecwalk.common.other.thread.questionPdf;

import java.util.List;

import com.ecwalk.common.other.thread.questionPdf.assist.CreatePendingDocs;
import com.ecwalk.common.other.thread.questionPdf.assist.SL_QuestionBank;
import com.ecwalk.common.other.thread.questionPdf.service.ProduceDocService;
import com.ecwalk.common.other.thread.questionPdf.vo.SrcDocVo;

public class SingleWeb {
	
	public static void main(String[] args) {
		System.out.println("题库开始初始化....");
		SL_QuestionBank.initBank();
		System.out.println("题库初始化完成....");
		
		List<SrcDocVo> docList=CreatePendingDocs.makePendingDoc(2);
		long startTotal=System.currentTimeMillis();
		for(SrcDocVo doc:docList){
			System.out.println("开始处理文档:"+doc.getDocName()+"...");
			long start=System.currentTimeMillis();
			String localName=ProduceDocService.makeDoc(doc);
			System.out.println("文档"+localName+"生成耗时:"+(System.currentTimeMillis()-start));
			start=System.currentTimeMillis();
			String remoteUrl=ProduceDocService.upLoadDoc(localName);
			System.out.println("已上传至["+remoteUrl+"]耗时:"+(System.currentTimeMillis()-start));
		}
		System.out.println("-------共耗时:"+(System.currentTimeMillis()-startTotal));
	}

}
