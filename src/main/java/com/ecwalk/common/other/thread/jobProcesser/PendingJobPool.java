package com.ecwalk.common.other.thread.jobProcesser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.ecwalk.common.other.thread.jobProcesser.vo.ITaskProcesser;
import com.ecwalk.common.other.thread.jobProcesser.vo.JobInfo;
import com.ecwalk.common.other.thread.jobProcesser.vo.TaskResult;
import com.ecwalk.common.other.thread.jobProcesser.vo.TaskResultType;

public class PendingJobPool {
	
	private static final int THREAD_COUNTS=Runtime.getRuntime().availableProcessors();
	
	//使用有戒队列
	private static BlockingQueue<Runnable> taskQueue=new ArrayBlockingQueue<>(5000);
	
	private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap=new ConcurrentHashMap<>();
	
	//线程池，固定大小，有界队列,默认使用系统，策略抛错
	private static ExecutorService taskExecutor
		=new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60, TimeUnit.SECONDS, taskQueue);
	
	private static CheckJobProcesser checkJob=CheckJobProcesser.getInstance();
	
	public static Map<String, JobInfo<?>> getMap(){
		return jobInfoMap;
	}
	
	//单例模式---start
	private PendingJobPool(){}
	
	private static class JobPoolHolder{
		public static PendingJobPool pool=new PendingJobPool();
	}
	
	public static PendingJobPool getInstance(){
		return JobPoolHolder.pool;
	}
	//单例模式---end
	

	

	//对工作中任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存以供查询
	private static class PendingTask<T,R> implements Runnable{
		private JobInfo<R> jobInfo;
		private T processData;
		public PendingTask(JobInfo<R> jobInfo, T processData) {
			super();
			this.jobInfo = jobInfo;
			this.processData = processData;
		}
		@Override
		public void run() {
			R r=null;
			ITaskProcesser<T, R> taskProcesser=(ITaskProcesser<T, R>) jobInfo.getTaskProcesser();
			TaskResult<R> result=null;
			try{
				result=taskProcesser.taskExecute(processData);
				if(result==null){
					result=new TaskResult<R>(TaskResultType.Exception, r,
							"result is null");
				}
				if(result.getResultType()==null){
					if(StringUtils.isBlank(result.getReason())){
						result=new TaskResult<R>(TaskResultType.Exception, r,
								"reason is null");
					}else{
						result=new TaskResult<R>(TaskResultType.Exception, r,
								"result is null,but reason:"+result.getReason());
					}
				}
			}catch(Exception e){
				result=new TaskResult<R>(TaskResultType.Exception, r,
						e.getMessage());
			}finally {
				jobInfo.addTaskResult(result,checkJob);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <R> JobInfo<R> getJob(String jobName){
		JobInfo<R> jobInfo=(JobInfo<R>) jobInfoMap.get(jobName);
		if(jobInfo==null){
			throw new RuntimeException(jobName+"是个非法任务");
		}
		return jobInfo;
	}
	
	public <T,R> void putTask(String jobName,T t){
		JobInfo<R> jobInfo=getJob(jobName);
		PendingTask<T, R> task=new PendingTask<T, R>(jobInfo, t);
		taskExecutor.execute(task);
	}
	
	//注册job
	public <R> void registerJob(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser,  long expireTime){
		JobInfo<R> jobInfo=new JobInfo<>(jobName, jobLength, taskProcesser, expireTime);
		if(jobInfoMap.putIfAbsent(jobName, jobInfo)!=null){
			throw new RuntimeException(jobName+"已经注册");
		}
	}
	
	//获得每个任务的详情
	public <R> List<TaskResult<R>> getTaskDetail(String jobName){
		JobInfo<R> jobInfo=getJob(jobName);
		return jobInfo.getTaskDetail();
	}
	
	//获取工作的整体处理进度
	public <R> String getTaskProcess(String jobName){
		JobInfo<R> jobInfo=getJob(jobName);
		return jobInfo.getTotalProcess();
	}
}
