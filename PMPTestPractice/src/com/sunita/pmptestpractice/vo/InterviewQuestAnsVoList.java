package com.sunita.pmptestpractice.vo;

import java.util.ArrayList;
import java.util.List;



public class InterviewQuestAnsVoList {
	private String topic;
	
	private List<InterviewQuestAnsVo> interviewQuestAnsVoList;

	public String getTopic() {
		return topic;
	}

	public List<InterviewQuestAnsVo> getInterviewQuestAnsVoList() {
		return interviewQuestAnsVoList;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setInterviewQuestAnsVoList(
			List<InterviewQuestAnsVo> interviewQuestAnsVoList) {
		this.interviewQuestAnsVoList = interviewQuestAnsVoList;
	}
	public void addInterQAVo(InterviewQuestAnsVo interQAVo){
		if(this.interviewQuestAnsVoList == null){
			this.interviewQuestAnsVoList =  new ArrayList<InterviewQuestAnsVo>();
		}
		this.interviewQuestAnsVoList.add(interQAVo);
	}

}
