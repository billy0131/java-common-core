package com.ecwalk.common.other.thread.jobProcesser.vo;

public class TaskResult<R> {
	

	private final TaskResultType resultType;
	private final R returenValue;
	private final String reason;
	
	public TaskResult(TaskResultType resultType, R returenValue, String reason) {
		super();
		this.resultType = resultType;
		this.returenValue = returenValue;
		this.reason = reason;
	}
	
	public TaskResult(TaskResultType resultType, R returenValue) {
		super();
		this.resultType = resultType;
		this.returenValue = returenValue;
		this.reason = "Success";
	}

	public TaskResultType getResultType() {
		return resultType;
	}

	public R getReturenValue() {
		return returenValue;
	}

	public String getReason() {
		return reason;
	}
}
