package com.ajit.myfirstapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ajit.myfirstapp.vo.QuestionAnswerVo;

public class MainActivity extends Activity  implements OnClickListener {

	  private RadioGroup radioOptions;
	  private Button btnDisplay;
	  private ImageButton btnprevious, btnnext;
	  private int position = 0;
	  private List<QuestionAnswerVo> qaList = new ArrayList<QuestionAnswerVo>();
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.qaList = getQuestionAnswerList();
        
        System.out.println(this.qaList + " - ");
        System.out.println(this.qaList.size() + " - ");
        if(this.qaList == null || this.qaList.size() < 1){
        	return;
        }
        
        this.radioOptions = (RadioGroup)findViewById(R.id.radioOption);
        
        //Display view 
        changePosition(this.position);

        
        
        //initialize the object for the button
        btnprevious=(ImageButton)findViewById(R.id.btnprevious);
        //this button will innitially be disabled
        btnprevious.setEnabled(false);
        //add listener to the button
        btnprevious.setOnClickListener(this);
        
        btnnext=(ImageButton)findViewById(R.id.btnnext);
        btnnext.setOnClickListener(this);
        
        btnDisplay = (Button) findViewById(R.id.btn_check);
        btnDisplay.setOnClickListener(this);
        //addListenerOnButton();
        
        
    }

    private List<QuestionAnswerVo> getQuestionAnswerList() {
    	List<QuestionAnswerVo> voList = null;
    	XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			    InputStream in_s = getApplicationContext().getAssets().open("level_1.xml");
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);

	            System.out.println("step - 1");
	            voList = parseXML(parser);
	            System.out.println("step - 2");
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voList;
	}
    
    private List<QuestionAnswerVo> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
		List<QuestionAnswerVo> voList = null;
        int eventType = parser.getEventType();
        QuestionAnswerVo questionAnswerVo = null;
        System.out.println("eventType - " + eventType);
        
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	voList = new ArrayList();
                	System.out.println("switch - 1");
                    break;
                case XmlPullParser.START_TAG:
                	name = parser.getName();
                	System.out.println("switch - 2 - " + name);
                    if (name.equals("quiz")){
                    	questionAnswerVo = new QuestionAnswerVo();
                    } else if (questionAnswerVo != null){
                        if (name.equals("question")){
                        	questionAnswerVo.question = parser.nextText();
                        	System.out.println("questionAnswerVo.question - " + questionAnswerVo.question);
                        } else if (name.equals("answer")){
                        	questionAnswerVo.answer = parser.nextText();
                        } else if (name.equals("option1")){
                        	questionAnswerVo.option1 = parser.nextText();
                        } else if (name.equals("option2")){
                        	questionAnswerVo.option2 = parser.nextText();
                        } else if (name.equals("option3")){
                        	questionAnswerVo.option3 = parser.nextText();
                        } else if (name.equals("option4")){
                        	questionAnswerVo.option4 = parser.nextText();
                        } else if (name.equals("explanation")){
                        	questionAnswerVo.explanation = parser.nextText();
                        }  
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                	System.out.println("switch - 3 - " + name);
                    if (name.equalsIgnoreCase("quiz") && questionAnswerVo != null){
                    	voList.add(questionAnswerVo);
                    } 
            }
            eventType = parser.next();
        }
    	System.out.println("switch - 4 - " + voList.size());
        return voList;
	}


	public void displayRadioButton(int position) {
    	
    	QuestionAnswerVo qaVo = qaList.get(position);
    	
    	this.radioOptions.removeAllViews();
    	
        RadioButton rdbtn = null;
        rdbtn = new RadioButton(this);
        rdbtn.setId(1);
        rdbtn.setText(qaVo.option1);
        this.radioOptions.addView(rdbtn);
        
        rdbtn = new RadioButton(this);
        rdbtn.setId(2);
        rdbtn.setText(qaVo.option2);
        this.radioOptions.addView(rdbtn);

        rdbtn = new RadioButton(this);
        rdbtn.setId(3);
        rdbtn.setText(qaVo.option3);
        this.radioOptions.addView(rdbtn);
        
        rdbtn = new RadioButton(this);
        rdbtn.setId(4);
        rdbtn.setText(qaVo.option4);
        this.radioOptions.addView(rdbtn);

   }
    
    private List<QuestionAnswerVo> getQuestionAnswerList1() {
    	//List<QuestionAnswerVo> qaList = new ArrayList<QuestionAnswerVo>();
		QuestionAnswerVo qa = null;
		
		qa = new QuestionAnswerVo();
		qa.question = "Question 1";
		qa.answer = "1";
		qa.option1 = "Option A";
		qa.option2 = "Option B";
		qa.option3 = "Option C";
		qa.option4 = "Option D";
		qa.explanation = "Explanation for Answer";
		qaList.add(qa);
		
		qa = new QuestionAnswerVo();
		qa.question = "Question 2";
		qa.answer = "2";
		qa.option1 = "Option A";
		qa.option2 = "Option B";
		qa.option3 = "Option C";
		qa.option4 = "Option D";
		qa.explanation = "Explanation for Answer";
		qaList.add(qa);
		
		qa = new QuestionAnswerVo();
		qa.question = "Question 3";
		qa.answer = "3";
		qa.option1 = "Option A";
		qa.option2 = "Option B";
		qa.option3 = "Option C";
		qa.option4 = "Option D";
		qa.explanation = "Explanation for Answer";
		qaList.add(qa);
		
		qa = new QuestionAnswerVo();
		qa.question = "Question 4";
		qa.answer = "4";
		qa.option1 = "Option A";
		qa.option2 = "Option B";
		qa.option3 = "Option C";
		qa.option4 = "Option D";
		qa.explanation = "Explanation for Answer";
		qaList.add(qa);
		
		qa = new QuestionAnswerVo();
		qa.question = "Question 5";
		qa.answer = "2";
		qa.option1 = "Option A";
		qa.option2 = "Option B";
		qa.option3 = "Option C";
		qa.option4 = "Option D";
		qa.explanation = "Explanation for Answer";
		qaList.add(qa);
		
		
		return qaList;
	}

	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onClick(View arg0) {
		//when btnprevious is clicked
		if(arg0.getId()==R.id.btnprevious){
			//minus 1 to the screennumber
			position--;
			changePosition(position);
			btnprevious.setEnabled(position==0?false:true);
			//changePosition(position);
			btnnext.setEnabled(true);
		}
		//when btnnext is clicked
		else if(arg0.getId()==R.id.btnnext){
			position++;//add 1 to the screennumber
			changePosition(position);
			btnnext.setEnabled(position==qaList.size()-1?false:true);
			//changePosition(position);
			btnprevious.setEnabled(true);
					
		}
		//when btnCheck is clicked
		else if(arg0.getId()==R.id.btn_check){
			//call the method Check
			RadioButton radioBtn = null;
			QuestionAnswerVo qaVo = qaList.get(position);
			int selectedId = radioOptions.getCheckedRadioButtonId();
			
			TextView txtViewExplanation = (TextView)findViewById(R.id.placeHolderExplanation);
			txtViewExplanation.setText(qaVo.explanation + " - " + selectedId);
			txtViewExplanation.setVisibility(TextView.VISIBLE);
			
			if(!(selectedId <= 0)){
				System.out.println(qaVo.answer + " == " + selectedId);
				if(qaVo.answer.equals(""+selectedId)){
					radioBtn = (RadioButton) findViewById(selectedId);
					radioBtn.setBackgroundColor(Color.GREEN);
				}
				
			}else{
				txtViewExplanation = (TextView)findViewById(R.id.placeHolderExplanation);
				txtViewExplanation.setText(qaVo.explanation + " - " + selectedId);
				txtViewExplanation.setVisibility(TextView.VISIBLE);
			}

			
			
		}
	}
	
	//this method is to change the number that appear on the screen
    //after navigation button is clicked
    private void changePosition(int position){
    	QuestionAnswerVo qaVo = qaList.get(position);
		TextView txtViewQuestion = (TextView)findViewById(R.id.placeHolderQuestion);
		txtViewQuestion.setText(qaVo.question);
		
		displayRadioButton(position);
		
		TextView txtViewExplanation = (TextView) findViewById(R.id.placeHolderExplanation);
        txtViewExplanation.setVisibility(TextView.INVISIBLE);
    }//end changeNumber
    
}

