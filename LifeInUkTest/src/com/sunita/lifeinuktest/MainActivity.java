package com.sunita.lifeinuktest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.sunita.lifeinuktest.util.DrawableBitmap;
import com.sunita.lifeinuktest.util.PrintSysout;
import com.sunita.lifeinuktest.util.SaveDataToFile;
import com.sunita.lifeinuktest.util.StringUtil;
import com.sunita.lifeinuktest.vo.QuestionAnswerVo;
import com.sunita.lifeinuktest.vo.RawStringContent;

public class MainActivity extends Activity  implements OnClickListener {
		private AdView adView;
		private AdView adView2;
	  private RadioGroup radioOptions;
	  private TextView txtViewExplanation;
	  private TextView txtViewText;
	  private TextView txtPlaceHolderQuestionCount;
	  private TextView txtTestResult;
	  private Button btnDisplay;
	  private ImageButton btnprevious, btnnext;
	  private TextView txtHelp;
	  private int position = 0;
	  private List<QuestionAnswerVo> qaList = new ArrayList<QuestionAnswerVo>();
	  private HashMap<String, Integer> map;
	  private Integer fontSize = 20;
	  private TableLayout mainTableLayout, testResultTableLayout;
	  private String pageType = "";
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addAd();
        
        loadImageInMap();
        
        //Check which button pressed
        Intent mIntent = getIntent();
        this.pageType = mIntent.getStringExtra("appLevel");
        this.qaList = getQuestionAnswerList("level_1.xml");
        if(this.pageType.equalsIgnoreCase("test")){
        	filterQAList();
        }
        
        if(this.qaList == null || this.qaList.size() < 1){
        	return;
        }
        
        
        this.mainTableLayout = (TableLayout)findViewById(R.id.mainTableLayout);
		this.testResultTableLayout = (TableLayout)findViewById(R.id.testResultTableLayout);
        this.txtPlaceHolderQuestionCount = (TextView) findViewById(R.id.placeHolderQuestionCount);
        this.txtHelp = (TextView) findViewById(R.id.placeHolderHelp);
        this.txtViewExplanation = (TextView) findViewById(R.id.placeHolderExplanation);
        this.txtTestResult = (TextView) findViewById(R.id.txtTestResult);
        this.btnprevious=(ImageButton)findViewById(R.id.btnprevious);
        this.btnnext=(ImageButton)findViewById(R.id.btnnext);
        this.btnprevious.setVisibility(Button.INVISIBLE);
        this.btnnext.setVisibility(Button.INVISIBLE);
        this.btnDisplay = (Button) findViewById(R.id.btn_check);
        btnDisplay.setOnClickListener(this);
        btnnext.setOnClickListener(this);
        
        
        this.txtHelp.setTextColor(Color.BLACK);
        this.txtPlaceHolderQuestionCount.setTextColor(Color.BLACK);
        this.txtViewExplanation.setTextColor(Color.BLACK);
        this.txtViewExplanation.setTextSize(fontSize);
        this.txtHelp.setVisibility(TextView.INVISIBLE);
        this.txtHelp.setText("Press -> to Proceed to next question.");
        
        if(this.pageType.equalsIgnoreCase("test")){
        	//For Test cahnge button name
        	btnDisplay.setText("Finish");
        }else{
        	SaveDataToFile.persistData(this, SaveDataToFile.TYPE_INT, "" + qaList.size(), SaveDataToFile.TOTAL_QUESTION);
        	this.position = Integer.valueOf(SaveDataToFile.getData(this, SaveDataToFile.TYPE_INT, SaveDataToFile.CUR_POSITION));
        }
        
        //Display view 
        changePosition(this.position);

        
        
        //initialize the object for the button
        //this button will innitially be disabled
        //btnprevious.setEnabled(false);
        //add listener to the button
        btnprevious.setOnClickListener(this);
           
    }

    


	private void filterQAList() {
		int testStartPosition = Integer.valueOf(SaveDataToFile.getData(this, SaveDataToFile.TYPE_INT, SaveDataToFile.TEST_POSITION));
		if(testStartPosition == 0 ){
			testStartPosition = this.qaList.size() - 25;
			SaveDataToFile.persistData(this, SaveDataToFile.TYPE_INT, "" + testStartPosition, SaveDataToFile.TEST_POSITION);	
		}else{
			testStartPosition = testStartPosition - 25;
			if(testStartPosition < 1){
				testStartPosition = this.qaList.size() - 25;
			}
			SaveDataToFile.persistData(this, SaveDataToFile.TYPE_INT, "" + testStartPosition, SaveDataToFile.TEST_POSITION);
		}
		//PrintSysout.printSysout("testStartPosition : " + testStartPosition);
		List<QuestionAnswerVo> voList = new ArrayList<QuestionAnswerVo>();
		for(int i = testStartPosition ; i < testStartPosition + 24 ; i++){
			//PrintSysout.printSysout("i : " + i);	
			voList.add(this.qaList.get(i));
		}
		this.qaList = voList;
		//PrintSysout.printSysout("Test Question Count : " + this.qaList);
	}



	private void addAd() {
		 // Create the adView.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-4300070308662571/2467683040");
        adView.setAdSize(AdSize.BANNER);
        
        
     // Lookup your LinearLayout assuming it's been given
        // the attribute android:id="@+id/mainLayout".
        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLinearLayout);

        // Add the adView to it.
        layout.addView(adView);

        // Initiate a generic request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the adView with the ad request.
        adView.loadAd(adRequest);
        
        
        // Create the adView.
        adView2 = new AdView(this);
        adView2.setAdUnitId("ca-app-pub-4300070308662571/9785153443");
        adView2.setAdSize(AdSize.BANNER);
        
        
        // Add the adView to it.
        layout.addView(adView2, 0);

        // Initiate a generic request.
        adRequest = new AdRequest.Builder().build();

        // Load the adView with the ad request.
        adView2.loadAd(adRequest);
	}



	private void loadImageInMap() {
    	HashMap<String, Integer> images = new HashMap<String, Integer>();
    	images.put( "resultButton1", Integer.valueOf( R.id.btnQuestion1) );
    	images.put( "resultButton2", Integer.valueOf( R.id.btnQuestion2) );
    	images.put( "resultButton3", Integer.valueOf( R.id.btnQuestion3) );
    	images.put( "resultButton4", Integer.valueOf( R.id.btnQuestion4) );
    	images.put( "resultButton5", Integer.valueOf( R.id.btnQuestion5) );
    	images.put( "resultButton6", Integer.valueOf( R.id.btnQuestion6) );
    	images.put( "resultButton7", Integer.valueOf( R.id.btnQuestion7) );
    	images.put( "resultButton8", Integer.valueOf( R.id.btnQuestion8) );
    	images.put( "resultButton9", Integer.valueOf( R.id.btnQuestion9) );
    	images.put( "resultButton10", Integer.valueOf( R.id.btnQuestion10) );
    	images.put( "resultButton11", Integer.valueOf( R.id.btnQuestion11) );
    	images.put( "resultButton12", Integer.valueOf( R.id.btnQuestion12) );
    	images.put( "resultButton13", Integer.valueOf( R.id.btnQuestion13) );
    	images.put( "resultButton14", Integer.valueOf( R.id.btnQuestion14) );
    	images.put( "resultButton15", Integer.valueOf( R.id.btnQuestion15) );
    	images.put( "resultButton16", Integer.valueOf( R.id.btnQuestion16) );
    	images.put( "resultButton17", Integer.valueOf( R.id.btnQuestion17) );
    	images.put( "resultButton18", Integer.valueOf( R.id.btnQuestion18) );
    	images.put( "resultButton19", Integer.valueOf( R.id.btnQuestion19) );
    	images.put( "resultButton20", Integer.valueOf( R.id.btnQuestion20) );
    	images.put( "resultButton21", Integer.valueOf( R.id.btnQuestion21) );
    	images.put( "resultButton22", Integer.valueOf( R.id.btnQuestion22) );
    	images.put( "resultButton23", Integer.valueOf( R.id.btnQuestion23) );
    	images.put( "resultButton24", Integer.valueOf( R.id.btnQuestion24) );
        this.map = images;
	}

	private List<QuestionAnswerVo> getQuestionAnswerList(String level) {
    	List<QuestionAnswerVo> voList = null;
    	XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			    InputStream in_s = getApplicationContext().getAssets().open(level);
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);

	            //PrintSysout.printSysout("step - 1");
	            voList = parseXML(parser);
	            //PrintSysout.printSysout("step - 2");
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
        //PrintSysout.printSysout("eventType - " + eventType);
        
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	voList = new ArrayList<QuestionAnswerVo>();
                	//PrintSysout.printSysout("switch - 1");
                    break;
                case XmlPullParser.START_TAG:
                	name = parser.getName();
                	//PrintSysout.printSysout("switch - 2 - " + name);
                    if (name.equals("quiz")){
                    	questionAnswerVo = new QuestionAnswerVo();
                    	//PrintSysout.printSysout("questionAnswerVo : " + questionAnswerVo);
                    } else if (questionAnswerVo != null){
                        if (name.equals("question")){
                        	questionAnswerVo.question = parser.nextText();
                        	//PrintSysout.printSysout("questionAnswerVo.question - " + questionAnswerVo.question);
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
                        } else if (name.equals("type")){
                        	questionAnswerVo.type = parser.nextText();
                        }  
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                	//PrintSysout.printSysout("switch - 3 - " + name);
                    if (name.equalsIgnoreCase("quiz") && questionAnswerVo != null){
                    	voList.add(questionAnswerVo);
                    } 
            }
            eventType = parser.next();
        }
    	//PrintSysout.printSysout("switch - 4 - " + voList.size());
        return voList;
	}


	public void displayRadioButton(int position) {
		TableRow ansOpt =  (TableRow)findViewById(R.id.rowSelectAnswer);
		ansOpt.removeAllViews();
		DisplayMetrics mDisplayMetrics = this.getResources().getDisplayMetrics();
		this.radioOptions = new RadioGroup(this);
		ansOpt.addView(radioOptions);
		//radioOptions.setVisibility(RadioGroup.VISIBLE);
    	QuestionAnswerVo qaVo = qaList.get(position);
    	
    	//radioOptions.removeAllViews();
    	
        RadioButton rdbtn = null;
        if(qaVo.option1 != null && qaVo.option1 != ""){
        	PrintSysout.printSysout("qaVo.option1 : " + qaVo.option1);
        	rdbtn = new RadioButton(this);
            rdbtn.setId(1);
            rdbtn.setMaxWidth(mDisplayMetrics.widthPixels);
            rdbtn.setSingleLine(false);
            rdbtn.setTextColor(Color.BLACK);
            setCheckBoxViewData(rdbtn, qaVo.option1);
            radioOptions.addView(rdbtn);
            if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("1")){
            	rdbtn.setSelected(true);
            }
            if(this.pageType.equalsIgnoreCase("testResult")){
            	rdbtn.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("1")){
            		rdbtn.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("1")){
            		rdbtn.setBackgroundColor(Color.RED);
            	}
            }
        }
        
        if(qaVo.option2 != null && qaVo.option2 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(2);
	        rdbtn.setMaxWidth(mDisplayMetrics.widthPixels);
            rdbtn.setSingleLine(false);
	        setCheckBoxViewData(rdbtn, qaVo.option2);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
	        if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("2")){
            	rdbtn.setSelected(true);
            }
	        if(this.pageType.equalsIgnoreCase("testResult")){
            	rdbtn.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("2")){
            		rdbtn.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("2")){
            		rdbtn.setBackgroundColor(Color.RED);
            	}
            }
        }

        if(qaVo.option3 != null && qaVo.option3 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(3);
	        rdbtn.setMaxWidth(mDisplayMetrics.widthPixels);
            rdbtn.setSingleLine(false);
	        setCheckBoxViewData(rdbtn, qaVo.option3);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
	        if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("3")){
            	rdbtn.setSelected(true);
            }
	        if(this.pageType.equalsIgnoreCase("testResult")){
            	rdbtn.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("3")){
            		rdbtn.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("3")){
            		rdbtn.setBackgroundColor(Color.RED);
            	}
            }
        }
        
        if(qaVo.option4 != null && qaVo.option4 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(4);
	        rdbtn.setMaxWidth(mDisplayMetrics.widthPixels);
            rdbtn.setSingleLine(false);
	        setCheckBoxViewData(rdbtn, qaVo.option4);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
	        if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("4")){
            	rdbtn.setSelected(true);
            }
	        if(this.pageType.equalsIgnoreCase("testResult")){
            	rdbtn.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("4")){
            		rdbtn.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("4")){
            		rdbtn.setBackgroundColor(Color.RED);
            	}
            }
        }

   }
    
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onClick(View arg0) {
		populateSelectedAnswer();
		PrintSysout.printSysout("Click Id : " + arg0.getId());
		//when btnprevious is clicked
		if(arg0.getId()==R.id.btnprevious){
			//minus 1 to the screennumber
			position--;
			changePosition(position);
			btnprevious.setEnabled(position==0?false:true);
			//changePosition(position);
			btnnext.setEnabled(true);
			
			if(!this.pageType.equalsIgnoreCase("test")){
				SaveDataToFile.persistData(this, SaveDataToFile.TYPE_INT,""+position, SaveDataToFile.CUR_POSITION);	
			}
		}
		//when btnnext is clicked
		else if(arg0.getId()==R.id.btnnext){
			position++;//add 1 to the screennumber
			changePosition(position);
			btnnext.setEnabled(position==qaList.size()-1?false:true);
			//changePosition(position);
			btnprevious.setEnabled(true);
			//populateSelectedAnswer();
			
			if(!this.pageType.equalsIgnoreCase("test")){
				SaveDataToFile.persistData(this, SaveDataToFile.TYPE_INT,""+position, SaveDataToFile.CUR_POSITION);	
			}
					
		}
		//when btnCheck is clicked
		else if(arg0.getId()==R.id.btn_check){
			if(this.btnDisplay.getText().equals("Check")){
				btnDisplayCheck();
			}else if(this.btnDisplay.getText().equals("Finish")){
				this.pageType = "testResult";
				btnDisplayTestFinish();
			}
		}
		//when result button is clicked
		else if(arg0.getId()==R.id.btnQuestion1 || arg0.getId()==R.id.btnQuestion2 || arg0.getId()==R.id.btnQuestion3 || arg0.getId()==R.id.btnQuestion4 || arg0.getId()==R.id.btnQuestion5 || arg0.getId()==R.id.btnQuestion6
				|| arg0.getId()==R.id.btnQuestion7 || arg0.getId()==R.id.btnQuestion8 || arg0.getId()==R.id.btnQuestion9 || arg0.getId()==R.id.btnQuestion10 || arg0.getId()==R.id.btnQuestion11 || arg0.getId()==R.id.btnQuestion12
				|| arg0.getId()==R.id.btnQuestion13 || arg0.getId()==R.id.btnQuestion14 || arg0.getId()==R.id.btnQuestion15 || arg0.getId()==R.id.btnQuestion16 || arg0.getId()==R.id.btnQuestion17 || arg0.getId()==R.id.btnQuestion18
				|| arg0.getId()==R.id.btnQuestion19 || arg0.getId()==R.id.btnQuestion20 || arg0.getId()==R.id.btnQuestion21 || arg0.getId()==R.id.btnQuestion22 || arg0.getId()==R.id.btnQuestion23 || arg0.getId()==R.id.btnQuestion24){
			PrintSysout.printSysout("Result");
			this.mainTableLayout.setVisibility(View.VISIBLE);
			this.testResultTableLayout.setVisibility(View.GONE);
			this.txtViewExplanation.setVisibility(TextView.VISIBLE);
			Button btn = (Button)findViewById(arg0.getId());
			PrintSysout.printSysout("Result - " + btn.getText() );
			position = Integer.valueOf(btn.getText().toString());
			position--;
			btnnext.setEnabled(position==qaList.size()-1?false:true);
			btnprevious.setEnabled(position==0?false:true);
			changePosition(position);
		}
	}
	
	
	
	

	private void populateSelectedAnswer() {
		QuestionAnswerVo qaVo = qaList.get(position);
		if(qaVo.type.equals("radio")){
			int selectedId = this.radioOptions.getCheckedRadioButtonId();
			//when some value is selected - validate
			if(!(selectedId <= 0)){
				qaVo.selectedAnswer = "" + selectedId;
			}	
		}else if(qaVo.type.equals("text")){
			String textAnswer = "";
			if(qaVo.answer.matches("[0-9]*")){
				textAnswer = "" + this.numPicker.getValue();
			}else{
				textAnswer = this.txtViewText.getText().toString();
			}
			qaVo.selectedAnswer = textAnswer;
		}else if(qaVo.type.equals("check")){
			String boxId = "";
			for(int i = 1; i <= 4; i++){
				View child = findViewById(i);
				PrintSysout.printSysout("child type : " + i + " - " + child.getClass());
				if (child != null && child instanceof CheckBox) {
			        CheckBox box = (CheckBox) child;
			        if(box.isChecked()){
			        	boxId = boxId + (boxId.length()==0?""+box.getId():"," + box.getId());
			        }
			    }
			}
			//PrintSysout.printSysout("boxId : " + qaVo.answer + " - " + boxId);
			//Validate Check box
			if(boxId != ""){
				//PrintSysout.printSysout(qaVo.answer + " == " + selectedId);
				qaVo.selectedAnswer = boxId;
			}	
		}
	}


	private void btnDisplayTestFinish() {
		this.mainTableLayout.setVisibility(View.GONE);
		this.testResultTableLayout.setVisibility(View.VISIBLE);
		this.testResultTableLayout.setPadding(10, 10, 10, 10);
		if(this.txtTestResult.getText().toString().equals("")){
			this.txtTestResult.setText(testResult(this.qaList));	
		}
		// TODO Auto-generated method stub
		for(int i = 1 ; i <= 24 ; i++){
			Button btn = (Button)findViewById(this.map.get("resultButton" + i));
			btn.setOnClickListener(this);
			btn.setPadding(20, 20, 20, 20);
			PrintSysout.printSysout(btn.getText() + " - " + btn.getId());
			QuestionAnswerVo qaVo = this.qaList.get(i - 1);
			if(qaVo.isAnswerCoorect()){
				btn.setTextColor(Color.GREEN);
				btn.setText(btn.getText());
			}else{
				btn.setTextColor(Color.RED);
				btn.setText(btn.getText());
			}
		}
	}


	private String testResult(List<QuestionAnswerVo> qaList2) {
		String result = "";
		int correctAnswerCount = 0;
		for(QuestionAnswerVo qaVo : qaList2){
			if(qaVo.isAnswerCoorect()){
				correctAnswerCount++;
			}
		}
		
		result=result+"You have scored " + correctAnswerCount + " out of " + qaList2.size() ;
		if(correctAnswerCount <= 17){
			result=result+" \nYou need to score atlest 18 to pass the test. ";
		}else{
			result=result+" \nYou passed the practice test";
		}
		return result;
	}




	private void btnDisplayCheck() {
		String textResult = "Incorrect \n";
		//call the method Check
		RadioButton radioBtn = null;
		QuestionAnswerVo qaVo = qaList.get(position);
		if(qaVo.type.equals("radio")){
			int selectedId = this.radioOptions.getCheckedRadioButtonId();
			
			//when some value is selected - validate
			if(!(selectedId <= 0)){
				PrintSysout.printSysout(qaVo.answer + " == " + selectedId);
				if(qaVo.answer.equals(""+selectedId)){
					radioBtn = (RadioButton) findViewById(selectedId);
					radioBtn.setBackgroundColor(Color.GREEN);
					textResult = "Correct";
				}
				this.txtViewExplanation.setText(textResult  + " \nCorrect answer is :" + qaVo.showAnswer() + " \n" + (qaVo.explanation == null?"":" : " + qaVo.explanation));
				this.txtViewExplanation.setVisibility(TextView.VISIBLE);
			}	
		}else if(qaVo.type.equals("text")){
			String textAnswer = "";
			if(qaVo.answer.matches("[0-9]*")){
				textAnswer = "" + this.numPicker.getValue();
			}else{
				textAnswer = this.txtViewText.getText().toString();
			}
			if(qaVo.answer.equalsIgnoreCase(textAnswer)){
				textResult = "Correct";
			}
			this.txtViewExplanation.setText(textResult + (qaVo.explanation == null?"":" : Answer is " + qaVo.answer + ".\n" + qaVo.explanation));
			this.txtViewExplanation.setVisibility(TextView.VISIBLE);
		}else if(qaVo.type.equals("check")){
			String boxId = "";
			for(int i = 1; i <= 4; i++){
				View child = findViewById(i);
				PrintSysout.printSysout("child type : " + i + " - " + child.getClass());
				if (child != null && child instanceof CheckBox) {
			        CheckBox box = (CheckBox) child;
			        if(box.isChecked()){
			        	boxId = boxId + (boxId.length()==0?""+box.getId():"," + box.getId());
			        }
			    }
			}
			PrintSysout.printSysout("boxId : " + qaVo.answer + " - " + boxId);
			//Validate Check box
			if(boxId != ""){
				//PrintSysout.printSysout(qaVo.answer + " == " + selectedId);
				if(qaVo.answer.equals(boxId)){
					textResult = "Correct";
					
				}
				this.txtViewExplanation.setText(textResult + " \nCorrect answer is: \n" + qaVo.showAnswer() + " \n" + (qaVo.explanation == null?"":" : " + qaVo.explanation));
				this.txtViewExplanation.setVisibility(TextView.VISIBLE);
			}	
		}
	}


	//this method is to change the number that appear on the screen
    //after navigation button is clicked
    private void changePosition(int position){
    	QuestionAnswerVo qaVo = qaList.get(position);
		
    	printQuestion(qaVo.question);
    	
    	this.txtPlaceHolderQuestionCount.setText(position+1 + " / " + qaList.size());
		
		PrintSysout.printSysout("qaVo.option1 : " + qaVo.option1);
		if(qaVo.type.equals("text")){
			displayTextEdit(qaVo, position);
		}else if(qaVo.type.equals("radio")){
			displayRadioButton(position);	
		}else if(qaVo.type.equals("check")){
			displayCheckBoxButton(position);	
		}
		
        /*this.txtViewExplanation.setVisibility(TextView.INVISIBLE);*/
		if(this.pageType.equalsIgnoreCase("testResult")){
			this.txtViewExplanation.setVisibility(View.VISIBLE);
			this.txtViewExplanation.setText("Answer : " + qaVo.showAnswer() + ((qaVo.explanation != null && !qaVo.explanation.equals(""))?" \nExplanation :" + qaVo.explanation:""));
		}else{
			this.txtViewExplanation.setVisibility(View.GONE);	
		}
		
		
		
		
		if(position==0){
			this.btnprevious.setVisibility(Button.INVISIBLE);
			this.btnnext.setVisibility(Button.VISIBLE);
			this.txtHelp.setText("Press -> to Proceed to next question.");
			this.txtHelp.setVisibility(TextView.VISIBLE);
		}else if(position==qaList.size()-1){
			this.btnprevious.setVisibility(Button.VISIBLE);
			btnnext.setVisibility(Button.INVISIBLE);
			this.txtHelp.setText("Press return to go back to first screen.");
			this.txtHelp.setVisibility(TextView.VISIBLE);
		}else{
			this.btnprevious.setVisibility(Button.VISIBLE);
			this.btnnext.setVisibility(Button.VISIBLE);
			this.txtHelp.setVisibility(View.GONE);
		}
        
    }//end changeNumber

    private NumberPicker numPicker = null;
    
    private void displayTextEdit(QuestionAnswerVo qaVo, int position2) {
    	TableRow ansOpt =  (TableRow)findViewById(R.id.rowSelectAnswer);
		ansOpt.removeAllViews();
		
		if(qaVo.answer.matches("[0-9]*")){
			this.numPicker = new NumberPicker(this);
			numPicker.setMinValue(0);
			numPicker.setMaxValue(20);
			numPicker.setHorizontalScrollBarEnabled(true);
			ansOpt.addView(this.numPicker);	
		}else{
			this.txtViewText = new EditText(this);
			ansOpt.addView(this.txtViewText);
		}
		
	}

	private void displayCheckBoxButton(int position2) {
		TableRow ansOpt =  (TableRow)findViewById(R.id.rowSelectAnswer);
		//ansOpt.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		//ansOpt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		ansOpt.removeAllViews();
		
		TableLayout tb = new TableLayout(this);
		ansOpt.addView(tb);
		TableRow tr = null;
		
		QuestionAnswerVo qaVo = qaList.get(position);
    	
		CheckBox cb = null;
        if(qaVo.option1 != null && qaVo.option1 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(1);
        	setCheckBoxViewData(cb,qaVo.option1);
        	PrintSysout.printSysout("check box : " + cb.getText());
        	cb.setTextColor(Color.BLACK);
        	tr.addView(cb);
        	tb.addView(tr);
        	if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.indexOf("1") != -1){
            	cb.setSelected(true);
            }
        	if(this.pageType.equalsIgnoreCase("testResult")){
            	cb.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("1")){
            		cb.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("1")){
            		cb.setBackgroundColor(Color.RED);
            	}
            }
        }
        if(qaVo.option2 != null && qaVo.option2 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(2);
        	setCheckBoxViewData(cb,qaVo.option2);
        	PrintSysout.printSysout("check box : " + cb.getText());
        	cb.setTextColor(Color.BLACK);
        	tb.addView(tr);
        	tr.addView(cb);
        	if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.indexOf("2") != -1){
            	cb.setSelected(true);
            }
        	if(this.pageType.equalsIgnoreCase("testResult")){
            	cb.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("2")){
            		cb.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("2")){
            		cb.setBackgroundColor(Color.RED);
            	}
            }
        }

        if(qaVo.option3 != null && qaVo.option3 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(3);
        	setCheckBoxViewData(cb,qaVo.option3);
        	cb.setTextColor(Color.BLACK);
        	tb.addView(tr);
        	tr.addView(cb);
        	if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.indexOf("3") != -1){
            	cb.setSelected(true);
            }
        	if(this.pageType.equalsIgnoreCase("testResult")){
            	cb.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("3")){
            		cb.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("3")){
            		cb.setBackgroundColor(Color.RED);
            	}
            }
        }
        
        if(qaVo.option4 != null && qaVo.option4 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(4);
        	setCheckBoxViewData(cb,qaVo.option4);
        	cb.setTextColor(Color.BLACK);
        	tb.addView(tr);
        	tr.addView(cb);
        	if(qaVo.selectedAnswer != null && qaVo.selectedAnswer.indexOf("4") != -1){
            	cb.setSelected(true);
            }
        	if(this.pageType.equalsIgnoreCase("testResult")){
            	cb.setEnabled(false);
            	if(qaVo.answer.equalsIgnoreCase("4")){
            		cb.setBackgroundColor(Color.GREEN);	
            	}
            	if(!qaVo.isAnswerCoorect() && qaVo.selectedAnswer != null && qaVo.selectedAnswer.equalsIgnoreCase("4")){
            		cb.setBackgroundColor(Color.RED);
            	}
            }
        }
	}

	private void setCheckBoxViewData(CompoundButton cb, String option) {
		Map<String, RawStringContent> map = StringUtil.getImageIdFromString(option);
		RawStringContent rscNext = null;
		PrintSysout.printSysout("map.size() :" + map.size());
    	for(int i = 1 ; i <= map.size() ; i++){
    		RawStringContent rawStringContent = (RawStringContent)map.get("" + i);
    		if(rawStringContent.type.equals("text")){
    			cb.setText(rawStringContent.value);
    			PrintSysout.printSysout("cb text :" + rawStringContent.value);
    		}else if(rawStringContent.type.equals("image")){
    			
    			Bitmap bm = null;
    			if(bm == null){
					bm = DrawableBitmap.getBitmapImage(getResources(), this.map, rawStringContent.value);
				}
				int j = 0;
				for(j = i+1 ; j <= map.size(); j++){
					rscNext = (RawStringContent)map.get(""+j);
					if(rscNext.type.equals(rawStringContent.type)){
						
						bm = DrawableBitmap.joinImages(bm, DrawableBitmap.getBitmapImage(getResources(), this.map, rscNext.value));
					}else{
						break;
					}
				}
				i = j;
				Drawable d = new BitmapDrawable(getResources(), bm);
    			cb.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
    		}
    	}
	}

	private void printQuestion(String question) {
		Map<String, RawStringContent> qMap = StringUtil.getImageIdFromString(question);

		// get the screen size
		DisplayMetrics mDisplayMetrics = this.getResources().getDisplayMetrics();
		TableRow questiontr =  (TableRow)findViewById(R.id.rowQuestion);
		questiontr.removeAllViews();
		
		TableLayout tblayout = new TableLayout(this);
		
		RawStringContent rsc = null;
		//RawStringContent rscPrv = null;
		RawStringContent rscNext = null;
		//String strPrvObjectType  = null;
		TableRow row1 = null;
		
		PrintSysout.printSysout("printQuestion : " + qMap.size() + " - " + question);
		 
		for(int i = 1 ; i <= qMap.size(); i++){
			//strPrvObjectType  = null;
			rsc = (RawStringContent)qMap.get("" + i);
			//if(i <= qMap.size() && i > 1){
				//rscPrv = (RawStringContent)qMap.get("" + (i-1));
				//strPrvObjectType = rscPrv.type;
			//}
			
			PrintSysout.printSysout("rsc.type : " + rsc.type);
			
			if(rsc.type.equals("text")){
				if(row1!=null){
					PrintSysout.printSysout("text addrow row1 : " + row1);
					tblayout.addView(row1);
				}
				row1 = new TableRow(this);
				PrintSysout.printSysout("text");
				View textView = createQuestionTextView(rsc.value, tblayout, mDisplayMetrics);
				
				row1.addView(textView);
			}else if(rsc.type.equals("image")){
				
				Bitmap bm = null;
				PrintSysout.printSysout("i count : " + i);
				if(bm == null){
					PrintSysout.printSysout("1 rsc.value : " + rsc.value);
					bm = DrawableBitmap.getBitmapImage(getResources(), this.map, rsc.value);
					PrintSysout.printSysout("first bm :" + bm);
				}
				int j = 0;
				for(j = i+1 ; j <= qMap.size(); j++){
					PrintSysout.printSysout("j count : " + j);
					rscNext = (RawStringContent)qMap.get(""+j);
					if(rscNext.type.equals(rsc.type) && !rscNext.value.trim().equals("")){
						PrintSysout.printSysout("2 rsc.value : " + rscNext.value);
						bm = DrawableBitmap.joinImages(bm, DrawableBitmap.getBitmapImage(getResources(), this.map, rscNext.value));
						PrintSysout.printSysout("second bm :" + bm);
					}else{
						break;
					}
				}
				i = j;
				
				if(row1!=null){
					PrintSysout.printSysout("image row1 : " + row1);
					tblayout.addView(row1);
				}
				row1 = new TableRow(this);	
				
				PrintSysout.printSysout("thrid bm :" + bm);
				ImageView imageView = new ImageView(this);
				imageView.setImageBitmap(bm);
				row1.addView(imageView);
			}
		}
		//Add last row
		tblayout.addView(row1);
		questiontr.addView(tblayout);
	}
	
	private TextView createQuestionTextView(String rawQuestion, TableLayout tblayout, DisplayMetrics mDisplayMetrics) {
		//TableRow row1 = new TableRow(this);
		TextView qTextView = new TextView(this);
		//qTextView.setText(rawQuestion.replace("\\n", System.getProperty("line.separator")));
		qTextView.setText(rawQuestion.replace("\\n", "\r\n"));
		// force view width to only be as wide as the screen
		qTextView.setMaxWidth(mDisplayMetrics.widthPixels);
		qTextView.setTextColor(Color.BLACK);
		qTextView.setSingleLine(false);
		//qTextView.setInputType(qTextView.getInputType()|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		qTextView.setTextSize(fontSize);
		//qTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		//row1.addView(qTextView);
		//tblayout.addView(row1);
		return qTextView;
	}
	
	@Override
    protected void onStop(){
       super.onStop();

       if(!this.pageType.equalsIgnoreCase("test")){
			SaveDataToFile.persistData(this, SaveDataToFile.TYPE_INT,""+position, SaveDataToFile.CUR_POSITION);	
		}
    }

}
