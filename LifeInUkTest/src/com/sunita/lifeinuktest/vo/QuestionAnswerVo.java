package com.sunita.lifeinuktest.vo;

public class QuestionAnswerVo {
	public String type;
	public String question;
	public String answer;
	public String option1;
	public String option2;
	public String option3;
	public String option4;
	public String explanation;
	
	public String selectedAnswer;

	public boolean isAnswerCoorect(){
		return answer.equalsIgnoreCase(selectedAnswer)?true:false;
	}
	
	public String showAnswer(){
		String ans = "";

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
		return ans;
	}
}
