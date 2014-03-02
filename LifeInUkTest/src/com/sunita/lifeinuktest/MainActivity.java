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
import android.content.SharedPreferences;
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
import com.sunita.lifeinuktest.util.StringUtil;
import com.sunita.lifeinuktest.vo.QuestionAnswerVo;
import com.sunita.lifeinuktest.vo.RawStringContent;

public class MainActivity extends Activity  implements OnClickListener {
		private AdView adView;
	  private RadioGroup radioOptions;
	  private TextView txtViewExplanation;
	  private TextView txtViewText;
	  private TextView txtPlaceHolderQuestionCount;
	  private Button btnDisplay;
	  private ImageButton btnprevious, btnnext;
	  private TextView txtHelp;
	  private int position = 0;
	  private List<QuestionAnswerVo> qaList = new ArrayList<QuestionAnswerVo>();
	  private HashMap<String, Integer> map;
	  private Integer fontSize = 20;
	  
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addAd();
        
        loadImageInMap();
        
        Intent mIntent = getIntent();
        String level = mIntent.getStringExtra("appLevel");
        this.qaList = getQuestionAnswerList(level + ".xml");
        
        //PrintSysout.printSysout(this.qaList + " - ");
        //PrintSysout.printSysout(this.qaList.size() + " - ");
        if(this.qaList == null || this.qaList.size() < 1){
        	return;
        }
        
        this.txtPlaceHolderQuestionCount = (TextView) findViewById(R.id.placeHolderQuestionCount);
        this.txtHelp = (TextView) findViewById(R.id.placeHolderHelp);
        this.txtViewExplanation = (TextView) findViewById(R.id.placeHolderExplanation);
        this.btnprevious=(ImageButton)findViewById(R.id.btnprevious);
        this.btnnext=(ImageButton)findViewById(R.id.btnnext);
        this.btnprevious.setVisibility(Button.INVISIBLE);
        this.btnnext.setVisibility(Button.INVISIBLE);
        //btnnext=(ImageButton)findViewById(R.id.btnnext);
        btnnext.setOnClickListener(this);
        
        btnDisplay = (Button) findViewById(R.id.btn_check);
        btnDisplay.setOnClickListener(this);
        
        this.txtHelp.setTextColor(Color.BLACK);
        this.txtPlaceHolderQuestionCount.setTextColor(Color.BLACK);
        this.txtViewExplanation.setTextColor(Color.BLACK);
        this.txtViewExplanation.setTextSize(fontSize);
        this.txtHelp.setVisibility(TextView.INVISIBLE);
        this.txtHelp.setText("Press -> to Proceed to next question.");
        
        
        this.position = getPreferencePosition();
     
        
        //Display view 
        changePosition(this.position);

        
        
        //initialize the object for the button
        //this button will innitially be disabled
        //btnprevious.setEnabled(false);
        //add listener to the button
        btnprevious.setOnClickListener(this);
        

        //addListenerOnButton();
        
        
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
	}



	private void loadImageInMap() {
    	HashMap<String, Integer> images = new HashMap<String, Integer>();
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
        }
        
        if(qaVo.option2 != null && qaVo.option2 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(2);
	        rdbtn.setMaxWidth(mDisplayMetrics.widthPixels);
            rdbtn.setSingleLine(false);
	        setCheckBoxViewData(rdbtn, qaVo.option2);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
        }

        if(qaVo.option3 != null && qaVo.option3 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(3);
	        rdbtn.setMaxWidth(mDisplayMetrics.widthPixels);
            rdbtn.setSingleLine(false);
	        setCheckBoxViewData(rdbtn, qaVo.option3);
	        rdbtn.setTextColor(Color.BLACK);
	        radioOptions.addView(rdbtn);
        }
        
        if(qaVo.option4 != null && qaVo.option4 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(4);
	        rdbtn.setMaxWidth(mDisplayMetrics.widthPixels);
            rdbtn.setSingleLine(false);
	        setCheckBoxViewData(rdbtn, qaVo.option4);
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
			
			persistPageNumber(position);
		}
		//when btnnext is clicked
		else if(arg0.getId()==R.id.btnnext){
			position++;//add 1 to the screennumber
			changePosition(position);
			btnnext.setEnabled(position==qaList.size()-1?false:true);
			//changePosition(position);
			btnprevious.setEnabled(true);
			
			persistPageNumber(position);
					
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
					this.txtViewExplanation.setText(textResult + (qaVo.explanation == null?"":" : " + qaVo.explanation));
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
					this.txtViewExplanation.setText(textResult + (qaVo.explanation == null?"":" : " + qaVo.explanation));
					this.txtViewExplanation.setVisibility(TextView.VISIBLE);
				}	
			}
			
		}
	}
	
	private  static final String PREFS_NAME = "LifeInUKPratCurPosition";
	private void persistPageNumber(int position2) {
		// We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
	      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("position", position2);

	      	PrintSysout.printSysout("Set Position : " + position2);
	      // Commit the edits!
	      editor.commit();
		
	}
	
	private int getPreferencePosition() {
    	// Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int pos = settings.getInt("position", 1);
        
        PrintSysout.printSysout("Get Position : " + pos);
        
		return pos;
	}

	//this method is to change the number that appear on the screen
    //after navigation button is clicked
    private void changePosition(int position){
    	QuestionAnswerVo qaVo = qaList.get(position);
		
    	printQuestion(qaVo.question);
    	
    	this.txtPlaceHolderQuestionCount.setText(position+1 + " / " + qaList.size());
		
		//PrintSysout.printSysout("qaVo.option1 : " + qaVo.option1);
		if(qaVo.type.equals("text")){
			displayTextEdit(qaVo, position);
		}else if(qaVo.type.equals("radio")){
			displayRadioButton(position);	
		}else if(qaVo.type.equals("check")){
			displayCheckBoxButton(position);	
		}
		
        /*this.txtViewExplanation.setVisibility(TextView.INVISIBLE);*/
		this.txtViewExplanation.setVisibility(View.GONE);
		
		
		
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
        }

        if(qaVo.option3 != null && qaVo.option3 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(3);
        	setCheckBoxViewData(cb,qaVo.option3);
        	cb.setTextColor(Color.BLACK);
        	tb.addView(tr);
        	tr.addView(cb);
        }
        
        if(qaVo.option4 != null && qaVo.option4 != ""){
        	tr = new TableRow(this);
        	cb = new CheckBox(this);
        	cb.setId(4);
        	setCheckBoxViewData(cb,qaVo.option4);
        	cb.setTextColor(Color.BLACK);
        	tb.addView(tr);
        	tr.addView(cb);
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
    			
    			//cb.setCompoundDrawablesWithIntrinsicBounds(null, null, DrawableBitmap.getDrawableImage(getResources(), this.map, rawStringContent.value), null);
    			
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

      persistPageNumber(position);
    }

}

