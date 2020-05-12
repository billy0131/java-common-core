package com.ecwalk.common.other.thread.jobProcesser.vo;

public interface ITaskProcesser<T,R> {

	TaskResult<R> taskExecute(T data);
}
