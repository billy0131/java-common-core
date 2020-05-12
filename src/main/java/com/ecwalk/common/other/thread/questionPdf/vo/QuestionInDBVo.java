package com.ecwalk.common.other.thread.questionPdf.vo;

public class QuestionInDBVo {
	
	private final int id;
	
	private final String detail;

	public QuestionInDBVo(int id, String detail) {
		
		this.id = id;
		this.detail = detail;
	}

	public int getId() {
		return id;
	}

	public String getDetail() {
		return detail;
	}

}
