package com.sunita.pmptestpractice.vo;

import java.util.ArrayList;
import java.util.List;

public class InterviewQuestAnsVo {

	private String question;
	private List<String> answers;
	
	public InterviewQuestAnsVo() {
		// TODO Auto-generated constructor stub
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void addAnswer(String answer) {
		if(this.answers == null){
			this.answers = new ArrayList<String>();
		}
		this.answers.add(answer);
	}

	

	
}
