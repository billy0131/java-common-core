package com.ecwalk.common.other.protocol.pushNews;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping(produces="text/html;")
public class PushNewsController {
	
	ExecutorService executorService=Executors.newFixedThreadPool(2);
	
	/**
	 * ajax 长轮询
	 * @return
	 */
	@RequestMapping("/pushnews")
	public DeferredResult<String> pushnews(){
		final DeferredResult<String> deferredResult=new DeferredResult<String>();
		executorService.submit((Runnable)()->{
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			deferredResult.setResult("11");
		});
		return deferredResult;
	}
	
	@RequestMapping(value="/SSEpushnews",produces="text/")
	public DeferredResult<String> SSEpushnews(){
		final DeferredResult<String> deferredResult=new DeferredResult<String>();
		executorService.submit((Runnable)()->{
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			deferredResult.setResult("11");
		});
		return deferredResult;
	}

}
