package com.ecwalk.common.other.thread.jobProcesser.vo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.ecwalk.common.other.thread.jobProcesser.CheckJobProcesser;

public class JobInfo<R> {
	//区分唯一工作
	private final String jobName;
	//工作的任务个数
	private final int jobLength;
	//工作任务处理器
	private final ITaskProcesser<?, ?> taskProcesser;
	private AtomicInteger successCount;
	private AtomicInteger taskProcesserCount;
	private LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;//从头拿，尾插入，结果队列
	private final long expireTime;
	
	public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, long expireTime) {
		super();
		this.jobName = jobName;
		this.jobLength = jobLength;
		this.taskProcesser = taskProcesser;
		this.successCount = new AtomicInteger(0);
		this.taskProcesserCount = new AtomicInteger(0);
		this.taskDetailQueue = new LinkedBlockingDeque<TaskResult<R>>(jobLength);
		this.expireTime = expireTime;
	}

	public int getSuccessCount() {
		return successCount.get();
	}

	public int getTaskProcesserCount() {
		return taskProcesserCount.get();
	}
	
	public int getFailCount() {
		return taskProcesserCount.get()-successCount.get();
	}
	
	public String getTotalProcess(){
		return "Success["+successCount.get()+"]/Current["
				+taskProcesserCount.get()+"] Total["+jobLength+"]";
	}
	
	public ITaskProcesser<?, ?> getTaskProcesser() {
		return taskProcesser;
	}

	public List<TaskResult<R>> getTaskDetail(){
		List<TaskResult<R>> taskList=new LinkedList<>();
		TaskResult<R> taskResult;
		while((taskResult=taskDetailQueue.pollFirst())!=null){
			taskList.add(taskResult);
		}
		return taskList;
	}
	
	//从业务应用角度来说，保证最终一致性即可，不对方法加锁
	public void addTaskResult(TaskResult<R> result,CheckJobProcesser checkJob){
		if(TaskResultType.Success.equals(result.getResultType())){
			successCount.incrementAndGet();
		}
		taskDetailQueue.addLast(result);
		taskProcesserCount.incrementAndGet();
		if(taskProcesserCount.get()==jobLength){
			checkJob.putJob(jobName, expireTime);
		}
	}
}
