package com.ajit.myfirstapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ajit.myfirstapp.util.DrawableBitmap;
import com.ajit.myfirstapp.util.PrintSysout;
import com.ajit.myfirstapp.util.StringUtil;
import com.ajit.myfirstapp.vo.QuestionAnswerVo;
import com.ajit.myfirstapp.vo.RawStringContent;

public class MainActivity extends Activity  implements OnClickListener {

	  private RadioGroup radioOptions;
	  private TextView txtViewExplanation;
	  private TextView txtViewText;
	  private Button btnDisplay;
	  private ImageButton btnprevious, btnnext;
	  private int position = 0;
	  private List<QuestionAnswerVo> qaList = new ArrayList<QuestionAnswerVo>();
	  private HashMap<String, Integer> map;

	  
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        loadImageInMap();
        
        Intent mIntent = getIntent();
        String level = mIntent.getStringExtra("appLevel");
        this.qaList = getQuestionAnswerList(level + ".xml");
        
        //PrintSysout.printSysout(this.qaList + " - ");
        //PrintSysout.printSysout(this.qaList.size() + " - ");
        if(this.qaList == null || this.qaList.size() < 1){
        	return;
        }
        
        this.txtViewExplanation = (TextView) findViewById(R.id.placeHolderExplanation);
        this.txtViewExplanation.setTextColor(Color.BLACK);
        //this.radioOptions = (RadioGroup)findViewById(R.id.radioOption);
        //this.txtViewText = (TextView) findViewById(R.id.placeHolderText);
        
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

    private void loadImageInMap() {
    	HashMap<String, Integer> images = new HashMap<String, Integer>();
        images.put( "flower.jpg", Integer.valueOf( R.drawable.flower ) );
        images.put( "flower_blue.jpg", Integer.valueOf( R.drawable.flower_blue ) );
        images.put( "flower_red.jpg", Integer.valueOf( R.drawable.flower_red ) );
        images.put( "flower_yellow.jpg", Integer.valueOf( R.drawable.flower_yellow ) );
        images.put( "car_blue.jpg", Integer.valueOf( R.drawable.car_blue ) );
        images.put( "car_red.jpg", Integer.valueOf( R.drawable.car_red ) );
        images.put( "car_yellow.jpg", Integer.valueOf( R.drawable.car_yellow ) );
        
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
            rdbtn.setText(qaVo.option1);
            rdbtn.setTextColor(Color.BLACK);
            //rdbtn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, DrawableBitmap.getDrawableImage(getResources(), this.map, qaVo.option1), null);
            radioOptions.addView(rdbtn);	
        }
        
        if(qaVo.option2 != null && qaVo.option2 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(2);
	        rdbtn.setText(qaVo.option2);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
        }

        if(qaVo.option3 != null && qaVo.option3 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(3);
	        rdbtn.setText(qaVo.option3);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
        }
        
        if(qaVo.option4 != null && qaVo.option4 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(4);
	        rdbtn.setText(qaVo.option4);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
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
			String textResult = "Incorrect";
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
					this.txtViewExplanation.setText(textResult + " : " + qaVo.explanation);
					this.txtViewExplanation.setVisibility(TextView.VISIBLE);
				}	
			}else if(qaVo.type.equals("text")){
				if(qaVo.answer.equals(this.txtViewText.getText().toString())){
					textResult = "Correct";
				}
				this.txtViewExplanation.setText(textResult + " : Answer is " + qaVo.answer + ".\n" + qaVo.explanation);
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
					this.txtViewExplanation.setText(textResult + " : Answer is " + qaVo.answer + ".\n" + qaVo.explanation);
					this.txtViewExplanation.setVisibility(TextView.VISIBLE);
				}	
			}
			
		}
	}
	
	//this method is to change the number that appear on the screen
    //after navigation button is clicked
    private void changePosition(int position){
    	QuestionAnswerVo qaVo = qaList.get(position);
		
    	printQuestion(qaVo.question);
		
		//this.radioOptions.setVisibility(View.GONE);
		this.txtViewExplanation.setVisibility(View.GONE);
		//this.txtViewText.setVisibility(View.GONE);
		
		//PrintSysout.printSysout("qaVo.option1 : " + qaVo.option1);
		if(qaVo.type.equals("text")){
			displayTextEdit(position);
		}else if(qaVo.type.equals("radio")){
			displayRadioButton(position);	
		}else if(qaVo.type.equals("check")){
			displayCheckBoxButton(position);	
		}
		
        /*this.txtViewExplanation.setVisibility(TextView.INVISIBLE);*/
		this.txtViewExplanation.setVisibility(View.GONE);
        
        
    }//end changeNumber

    private void displayTextEdit(int position2) {
    	TableRow ansOpt =  (TableRow)findViewById(R.id.rowSelectAnswer);
		ansOpt.removeAllViews();
		
		this.txtViewText = new EditText(this);
		ansOpt.addView(this.txtViewText);
		
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
        	tr.addView(cb);
        	tb.addView(tr);
        }
        if(qaVo.option2 != null && qaVo.option2 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(2);
        	setCheckBoxViewData(cb,qaVo.option2);
        	tb.addView(tr);
        	tr.addView(cb);
        }

        if(qaVo.option3 != null && qaVo.option3 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(3);
        	setCheckBoxViewData(cb,qaVo.option3);
        	tb.addView(tr);
        	tr.addView(cb);
        }
        
        if(qaVo.option4 != null && qaVo.option4 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(4);
        	setCheckBoxViewData(cb,qaVo.option4);
        	tb.addView(tr);
        	tr.addView(cb);
        }
	}

	private void setCheckBoxViewData(CheckBox cb, String option) {
		Map<String, RawStringContent> map = StringUtil.getImageIdFromString(option);
		PrintSysout.printSysout("map.size() :" + map.size());
    	for(int i = 1 ; i <= map.size() ; i++){
    		RawStringContent rawStringContent = (RawStringContent)map.get("" + i);
    		if(rawStringContent.type.equals("text")){
    			cb.setText(rawStringContent.value);
    		}else if(rawStringContent.type.equals("image")){
    			cb.setCompoundDrawablesWithIntrinsicBounds(null, null, DrawableBitmap.getDrawableImage(getResources(), this.map, rawStringContent.value), null);
    			
    		}
    	}
	}

	private void printQuestion(String question) {
		String rawQuestion = question;

		String strImage = "_s-img_";
		//String strImageNameCountSeparator = "-";

		// get the screen size
		DisplayMetrics mDisplayMetrics = this.getResources().getDisplayMetrics();
		TableRow questiontr =  (TableRow)findViewById(R.id.rowQuestion);
		questiontr.removeAllViews();
		
		TableLayout tblayout = new TableLayout(this);
		
		if(rawQuestion.indexOf(strImage) == -1){
			PrintSysout.printSysout("IMage not found");
			createQuestionTextView(rawQuestion, tblayout, mDisplayMetrics);			
		}else{
			 //_s-img_flower_blue.jpg,1_e-img_  _s-img_flower_red.jpg,2_e-img_ _s-img_car_red.jpg,1_e-img_
			PrintSysout.printSysout("Image Found");
			createQuestionView(rawQuestion, tblayout, mDisplayMetrics);
			
		}

		
		
		
		questiontr.addView(tblayout);
	}

	private void createQuestionTextView(String rawQuestion, TableLayout tblayout, DisplayMetrics mDisplayMetrics) {
		TableRow row1 = new TableRow(this);
		TextView qTextView = new TextView(this);
		//qTextView.setText(rawQuestion.replace("\\n", System.getProperty("line.separator")));
		qTextView.setText(rawQuestion.replace("\\n", "\r\n"));
		// force view width to only be as wide as the screen
		qTextView.setMaxWidth(mDisplayMetrics.widthPixels);
		qTextView.setTextColor(Color.BLACK);
		qTextView.setSingleLine(false);
		qTextView.setInputType(qTextView.getInputType()|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		//qTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		row1.addView(qTextView);
		tblayout.addView(row1);
	}

	private void createQuestionView(String rawQuestion, TableLayout tblayout, DisplayMetrics mDisplayMetrics) {
		String strImage = "_s-img_";
		String[] splittedImageString = rawQuestion.split(strImage);
	    PrintSysout.printSysout(splittedImageString.length);
		for(String str : splittedImageString){
			PrintSysout.printSysout(str);
			String pattern = "(.*),(\\d)_e-img_(.*)";

		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		      Matcher m = r.matcher(str);
		      if (m.find( )) {
		    	  PrintSysout.printSysout("ImageName: " + m.group(1) );
			         PrintSysout.printSysout("ImageCount: " + m.group(2) );
			         PrintSysout.printSysout("more text : " + m.group(3) );
			         int imageCount = Integer.valueOf(m.group(2));
		    	  String imageName = m.group(1);
		    	  
		    	  TableRow row2 = new TableRow(this);
		    	  for(int i = 1; i <= imageCount ; i ++){
		    		  createQuestionImageView(imageName, row2, mDisplayMetrics);
		    	  }
		    	  tblayout.addView(row2);
		         
		      } else {
		         createQuestionTextView(str, tblayout, mDisplayMetrics);
		      }
		}
	}

	private void createQuestionImageView(String imageName,
			TableRow row2, DisplayMetrics mDisplayMetrics) {
		
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(DrawableBitmap.decodeSampledBitmapFromResource(getResources(), DrawableBitmap.getImageId(this.map, imageName), 50, 50));
		row2.addView(imageView);
		
		
	}
}

