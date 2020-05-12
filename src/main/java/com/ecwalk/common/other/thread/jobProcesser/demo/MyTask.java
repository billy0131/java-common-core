package com.ecwalk.common.other.thread.jobProcesser.demo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.yetus.audience.InterfaceAudience.Public;

import com.ecwalk.common.other.thread.jobProcesser.vo.ITaskProcesser;
import com.ecwalk.common.other.thread.jobProcesser.vo.TaskResult;
import com.ecwalk.common.other.thread.jobProcesser.vo.TaskResultType;

public class MyTask implements ITaskProcesser<Integer, Integer>{

	@Override
	public TaskResult<Integer> taskExecute(Integer data) {
		Random random=new Random();
		int flag=random.nextInt(500);
		try {
			TimeUnit.MILLISECONDS.sleep(flag);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(flag<=300){
			Integer returnVaue=data.intValue()+flag;
			return new TaskResult<Integer>(TaskResultType.Success, returnVaue);
		}else if(flag>301&&flag<=400){
			return new TaskResult<Integer>(TaskResultType.Failure,-1, "Failure");
		}else{
			try{
				throw new RuntimeException("异常发生了");
			}catch(Exception e){
				return new TaskResult<Integer>(TaskResultType.Exception,-1, e.getMessage());
			}
		}
	}

}
