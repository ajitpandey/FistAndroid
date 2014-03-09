package com.sunita.lifeinuktest.vo;

import java.io.Serializable;
import java.util.List;


public class QuizLevel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5272440495759342823L;
	public List<QuestionAnswerVo> quizes;
	
	public QuizLevel(List<QuestionAnswerVo> qaVoList) {
		this.quizes = qaVoList;
	}
	
	public List<QuestionAnswerVo> getQuizes() {
		return quizes;
	}
	public void setQuizes(List<QuestionAnswerVo> quizes) {
		this.quizes = quizes;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("<quiz_level>");
		if(this.quizes != null && this.quizes.size() > 0){
			for(QuestionAnswerVo qaVo : this.quizes){
				strBuf.append(qaVo.toString());
			}
		}
		strBuf.append("</quiz_level>");
		return strBuf.toString();
	}

	
	
	
}
