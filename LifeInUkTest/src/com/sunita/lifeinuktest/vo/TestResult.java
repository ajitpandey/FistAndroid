package com.sunita.lifeinuktest.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestResult implements  Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5415719880476942507L;
	private List<QuestionAnswerVo> qaVoList;
	private String score;
	private String dateTime;
	
	public TestResult(List<QuestionAnswerVo> qaList) {
		qaVoList = new ArrayList<QuestionAnswerVo>();
		addQaVoList(qaList);
		dateTime = new Date().toString();
	}

	public TestResult() {
		qaVoList = new ArrayList<QuestionAnswerVo>();
	}

	public void addQaVoList(List<QuestionAnswerVo> qaVoList){
		this.qaVoList.addAll(qaVoList);
		testResultScore();
	}
	
	public void addQaVo(QuestionAnswerVo qaVo){
		this.qaVoList.add(qaVo);
		testResultScore();
	}
	
	public List<QuestionAnswerVo> getQaVoList() {
		return qaVoList;
	}
	public void setQaVoList(List<QuestionAnswerVo> qaVoList) {
		this.qaVoList = qaVoList;
		testResultScore();
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	private void testResultScore() {
		boolean hasAnswer = true;;
		int correctAnswerCount = 0;
		for(QuestionAnswerVo qaVo : this.qaVoList){
			if(qaVo.answer != null && !qaVo.answer.equals("")){
				if(qaVo.isAnswerCoorect()){
					correctAnswerCount++;
				}
			}else{
				hasAnswer = false;
				break;
			}
			
		}
		if(hasAnswer)
			this.setScore(correctAnswerCount + "");
	}
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("<trslt>");
		strBuf.append("<dt>" +this.dateTime+ "</dt>");
		strBuf.append("<score>" +this.score+ "</score>");
		if(this.qaVoList != null && this.qaVoList.size() > 0){
			for(QuestionAnswerVo qaVo : this.qaVoList){
				strBuf.append("<quiz>" + qaVo.filteredToString() + "</quiz>");
			}
		}
		strBuf.append("</trslt>");
		return strBuf.toString();
	}
	
}
