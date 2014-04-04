package com.sunita.interviewpractice.vo;

public class InterviewQuestAnsVo {

	private String question, answer;
	
	public InterviewQuestAnsVo() {
		// TODO Auto-generated constructor stub
	}
	
	public InterviewQuestAnsVo(String q,String a){
		this.answer = a;
		this.question = q;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
