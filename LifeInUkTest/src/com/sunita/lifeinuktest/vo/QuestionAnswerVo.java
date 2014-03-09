package com.sunita.lifeinuktest.vo;

import java.io.Serializable;

public class QuestionAnswerVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5671357846711576510L;
	public String id;
	public String type;
	public String question;
	public String answer;
	public String option1;
	public String option2;
	public String option3;
	public String option4;
	public String explanation;
	
	public String selectedAnswer;
	
	public QuestionAnswerVo() {
		id=null;
		type=null;
		question=null;
		answer=null;
		option1=null;
		option2=null;
		option3=null;
		option4=null;
		explanation=null;
	}

	public boolean isAnswerCoorect(){
		return answer.equalsIgnoreCase(selectedAnswer)?true:false;
	}
	
	public String showAnswer(){
		String ans = "";

		if(this.answer != null){
			String[] ansArr = this.answer.split(",");
			for(String str : ansArr){
				if(str.matches("[0-9]")){
					switch (Integer.valueOf(str)) {
					case 1:
						ans = ans + (ans.equals("")?"":" \n") + option1;
						break;
					case 2:
						ans = ans + (ans.equals("")?"":" \n") + option2;
						break;
					case 3:
						ans = ans + (ans.equals("")?"":" \n") + option3;
						break;
					case 4:
						ans = ans + (ans.equals("")?"":" \n") + option4;
						break;
					}	
				}
			}	
		}
		
		return ans;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append("<id>" + id + "</id>");
		strBuf.append("<type>" + type + "</type>");
		strBuf.append("<question>" + question + "</question>");
		strBuf.append("<answer>" + answer + "</answer>");
		strBuf.append("<option1>" + option1 + "</option1>");
		strBuf.append("<option2>" + option2 + "</option2>");
		strBuf.append("<option3>" + option3 + "</option3>");
		strBuf.append("<option4>" + option4 + "</option4>");
		strBuf.append("<explanation>" + explanation + "</explanation>");
		
		return strBuf.toString();
	}
}
