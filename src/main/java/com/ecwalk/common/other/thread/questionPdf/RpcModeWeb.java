package com.ecwalk.common.other.thread.questionPdf;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ecwalk.common.other.thread.questionPdf.assist.Consts;
import com.ecwalk.common.other.thread.questionPdf.assist.CreatePendingDocs;
import com.ecwalk.common.other.thread.questionPdf.assist.SL_QuestionBank;
import com.ecwalk.common.other.thread.questionPdf.service.ProduceDocService;
import com.ecwalk.common.other.thread.questionPdf.vo.SrcDocVo;

/**
 * 
 * @author billy
 * rpc服务端,采用生产者消费者模式，生产者消费者还会级联
 */
public class RpcModeWeb {
	//负责生成文档
	private static ExecutorService docMakeService=Executors.newFixedThreadPool(Consts.CPU_COUNT*2);
	
	//负责上传文档
	private static ExecutorService docUploadService=Executors.newFixedThreadPool(Consts.CPU_COUNT*2);
	
	private static CompletionService<String> docCs=new ExecutorCompletionService<>(docMakeService);
	
	private static CompletionService<String> docUploadCs=new ExecutorCompletionService<>(docUploadService);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("题库开始初始化....");
		SL_QuestionBank.initBank();
		System.out.println("题库初始化完成....");
		
		List<SrcDocVo> docList=CreatePendingDocs.makePendingDoc(60);
		long startTotal=System.currentTimeMillis();
		for(SrcDocVo doc:docList){
			docCs.submit(new MakeDocTask(doc));
		}
		for(SrcDocVo doc:docList){
			Future<String> future=docCs.take();
			docUploadCs.submit(new UploadDocTask(future.get()));
		}
		
		//可以不要，打印耗时
		for(SrcDocVo doc:docList){
			docUploadCs.take().get();
		}
		System.out.println("-------共耗时:"+(System.currentTimeMillis()-startTotal));
	}
	
	//生成文档的任务
	private static class MakeDocTask implements Callable<String>{

		private SrcDocVo pendingDocVo;
		
		public MakeDocTask(SrcDocVo pendingDocVo) {
			super();
			this.pendingDocVo = pendingDocVo;
		}

		@Override
		public String call() throws Exception {
			long start=System.currentTimeMillis();
			String localName=ProduceDocService.makeDoc(pendingDocVo);
			System.out.println("文档"+localName+"生成耗时:"+(System.currentTimeMillis()-start));
			return localName;
		}
		
	}
	
	//上传文档
	private static class UploadDocTask implements Callable<String>{

		private String filePath;
		
		public UploadDocTask(String filePath) {
			super();
			this.filePath = filePath;
		}

		@Override
		public String call() throws Exception {
			long start=System.currentTimeMillis();
			String remoteUrl=ProduceDocService.upLoadDoc(filePath);
			System.out.println("已上传至["+remoteUrl+"]耗时:"+(System.currentTimeMillis()-start));
			return remoteUrl;
		}
		
	}
}
