package com.ecwalk.common.other.thread.questionPdf.vo;

import java.io.Serializable;
import java.util.List;

public class SrcDocVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String docName;
	
	private List<Integer> questionList;

	public String getDocName() {
		return docName;
	}

	public SrcDocVo(String docName, List<Integer> questionList) {
		super();
		this.docName = docName;
		this.questionList = questionList;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public List<Integer> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Integer> questionList) {
		this.questionList = questionList;
	}


	
	

}
