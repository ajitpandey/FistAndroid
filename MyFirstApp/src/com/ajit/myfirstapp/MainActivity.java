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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
        
        this.qaList = getQuestionAnswerList();
        
        //System.out.println(this.qaList + " - ");
        //System.out.println(this.qaList.size() + " - ");
        if(this.qaList == null || this.qaList.size() < 1){
        	return;
        }
        
        this.txtViewExplanation = (TextView) findViewById(R.id.placeHolderExplanation);
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

	private List<QuestionAnswerVo> getQuestionAnswerList() {
    	List<QuestionAnswerVo> voList = null;
    	XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			    InputStream in_s = getApplicationContext().getAssets().open("level_1.xml");
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);

	            //System.out.println("step - 1");
	            voList = parseXML(parser);
	            //System.out.println("step - 2");
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
        //System.out.println("eventType - " + eventType);
        
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	voList = new ArrayList<QuestionAnswerVo>();
                	//System.out.println("switch - 1");
                    break;
                case XmlPullParser.START_TAG:
                	name = parser.getName();
                	//System.out.println("switch - 2 - " + name);
                    if (name.equals("quiz")){
                    	questionAnswerVo = new QuestionAnswerVo();
                    	//System.out.println("questionAnswerVo : " + questionAnswerVo);
                    } else if (questionAnswerVo != null){
                        if (name.equals("question")){
                        	questionAnswerVo.question = parser.nextText();
                        	//System.out.println("questionAnswerVo.question - " + questionAnswerVo.question);
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
                	//System.out.println("switch - 3 - " + name);
                    if (name.equalsIgnoreCase("quiz") && questionAnswerVo != null){
                    	voList.add(questionAnswerVo);
                    } 
            }
            eventType = parser.next();
        }
    	//System.out.println("switch - 4 - " + voList.size());
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
        	rdbtn = new RadioButton(this);
            rdbtn.setId(1);
            rdbtn.setText(qaVo.option1);
            radioOptions.addView(rdbtn);	
        }
        
        if(qaVo.option2 != null && qaVo.option2 != ""){
	        rdbtn = new RadioButton(this);
	        rdbtn.setId(2);
	        rdbtn.setText(qaVo.option2);
	        radioOptions.addView(rdbtn);
        }

        if(qaVo.option3 != null && qaVo.option3 != ""){
        rdbtn = new RadioButton(this);
        rdbtn.setId(3);
        rdbtn.setText(qaVo.option3);
        radioOptions.addView(rdbtn);
        }
        
        if(qaVo.option4 != null && qaVo.option4 != ""){
        rdbtn = new RadioButton(this);
        rdbtn.setId(4);
        rdbtn.setText(qaVo.option4);
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
			//call the method Check
			RadioButton radioBtn = null;
			QuestionAnswerVo qaVo = qaList.get(position);
			if(qaVo.type.equals("radio")){
				int selectedId = this.radioOptions.getCheckedRadioButtonId();
				
				if(!(selectedId <= 0)){
					//System.out.println(qaVo.answer + " == " + selectedId);
					if(qaVo.answer.equals(""+selectedId)){
						radioBtn = (RadioButton) findViewById(selectedId);
						radioBtn.setBackgroundColor(Color.GREEN);
						
						this.txtViewExplanation.setText(qaVo.explanation);
						this.txtViewExplanation.setVisibility(TextView.VISIBLE);
					}
				}	
			}else if(qaVo.type.equals("text")){
				String textResult = "Incorrect";
				if(qaVo.answer.equals(this.txtViewText.getText().toString())){
					textResult = "Correct";
				}
				this.txtViewExplanation.setText(textResult + " : Answer is " + qaVo.answer + ".\n" + qaVo.explanation);
				this.txtViewExplanation.setVisibility(TextView.VISIBLE);
			}else if(qaVo.type.equals("check")){
				String boxId = "";
				/*TableRow ansOpt =  (TableRow)findViewById(R.id.rowSelectAnswer);
				System.out.println("ansOpt.getChildCount() : " + ansOpt.getChildCount());
				//Get get checked checkbox ids
				
				for(int i = 0; i < ansOpt.getChildCount(); i++){
					View child = ansOpt.getChildAt(i);
					System.out.println("child type : " + child.getClass());
					if (child instanceof CheckBox) {
				        CheckBox box = (CheckBox) child;
				        if(box.isChecked()){
				        	boxId = boxId.length()==0?""+box.getId():"," + box.getId();
				        }
				    }
				}*/
				for(int i = 1; i <= 4; i++){
					View child = findViewById(i);
					System.out.println("child type : " + i + " - " + child.getClass());
					if (child != null && child instanceof CheckBox) {
				        CheckBox box = (CheckBox) child;
				        if(box.isChecked()){
				        	boxId = boxId + (boxId.length()==0?""+box.getId():"," + box.getId());
				        }
				    }
				}
				System.out.println("boxId : " + qaVo.answer + " - " + boxId);
				//Validate Check box
				if(boxId != ""){
					//System.out.println(qaVo.answer + " == " + selectedId);
					String textResult = "Incorrect";
					if(qaVo.answer.equals(boxId)){
						textResult = "Correct";
						
					}
					this.txtViewExplanation.setText(textResult + " : " + qaVo.explanation);
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
		
		//System.out.println("qaVo.option1 : " + qaVo.option1);
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
		Map map = getImageIdFromString(option);
		System.out.println("map.size() :" + map.size());
    	for(int i = 1 ; i <= map.size() ; i++){
    		RawStringContent rawStringContent = (RawStringContent)map.get("" + i);
    		if(rawStringContent.type.equals("text")){
    			cb.setText(rawStringContent.value);
    		}else if(rawStringContent.type.equals("image")){
    			String[] strArr = rawStringContent.value.split(",");
    			Bitmap bit = decodeSampledBitmapFromResource(getResources(), getImageId(this, strArr[0]), 50, 50);
    			
    			Drawable d = new BitmapDrawable(getResources(), bit);
    			
    			cb.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
    		}
    	}
	}

	private void printQuestion(String question) {
		String rawQuestion = question;

		String strImage = "_s-img_";
		String strImageNameCountSeparator = "-";

		// get the screen size
		DisplayMetrics mDisplayMetrics = this.getResources().getDisplayMetrics();
		TableRow questiontr =  (TableRow)findViewById(R.id.rowQuestion);
		questiontr.removeAllViews();
		
		TableLayout tblayout = new TableLayout(this);
		
		if(rawQuestion.indexOf(strImage) == -1){
			System.out.println("IMage not found");
			createQuestionTextView(rawQuestion, tblayout, mDisplayMetrics);			
		}else{
			 //_s-img_flower_blue.jpg,1_e-img_  _s-img_flower_red.jpg,2_e-img_ _s-img_car_red.jpg,1_e-img_
			System.out.println("Image Found");
			createQuestionView(rawQuestion, tblayout, mDisplayMetrics);
			
		}

		
		
		
		questiontr.addView(tblayout);
	}

	private void createQuestionTextView(String rawQuestion, TableLayout tblayout, DisplayMetrics mDisplayMetrics) {
		TableRow row1 = new TableRow(this);
		TextView qTextView = new TextView(this);
		qTextView.setText(rawQuestion);
		// force view width to only be as wide as the screen
		qTextView.setMaxWidth(mDisplayMetrics.widthPixels);
		row1.addView(qTextView);
		tblayout.addView(row1);
	}

	private void createQuestionView(String rawQuestion, TableLayout tblayout, DisplayMetrics mDisplayMetrics) {
		String strImage = "_s-img_";
		String[] splittedImageString = rawQuestion.split(strImage);
	    System.out.println(splittedImageString.length);
		for(String str : splittedImageString){
			System.out.println(str);
			String pattern = "(.*),(\\d)_e-img_(.*)";

		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		      Matcher m = r.matcher(str);
		      if (m.find( )) {
		    	  System.out.println("ImageName: " + m.group(1) );
			         System.out.println("ImageCount: " + m.group(2) );
			         System.out.println("more text : " + m.group(3) );
		    	  int imageCount = (new Integer(m.group(2))).intValue();
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
	
	private Map<String, RawStringContent> getImageIdFromString(String rawString){
		Map map = new HashMap<String, RawStringContent>();
		RawStringContent rawStringContent = null;
		String strImage = "_s-img_";
		String[] splittedImageString = rawString.split(strImage);
	    System.out.println(splittedImageString.length);
	    int count = 1;
		for(String str : splittedImageString){
			System.out.println(str);
			String pattern = "(.*),(\\d)_e-img_(.*)";

		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		      Matcher m = r.matcher(str);
		      if (m.find( )) {
		    	  System.out.println("ImageName: " + m.group(1) );
			         System.out.println("ImageCount: " + m.group(2) );
			         System.out.println("more text : " + m.group(3) );
			         rawStringContent = new RawStringContent();
			         rawStringContent.type = "image";
			         rawStringContent.value = m.group(1) +","+ m.group(2);
			         map.put(""+count, rawStringContent);
		         
		      } else {
		         rawStringContent = new RawStringContent();
		         rawStringContent.type = "text";
		         rawStringContent.value = str;
		         map.put(""+count, rawStringContent);
		      }
		      count++;
		}
		
		return map;
	}

	private void createQuestionImageView(String imageName,
			TableRow row2, DisplayMetrics mDisplayMetrics) {
		
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(
			    decodeSampledBitmapFromResource(getResources(), getImageId(this, imageName), 50, 50));
		row2.addView(imageView);
		
		
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
		    // Raw height and width of image
		    final int height = options.outHeight;
		    final int width = options.outWidth;
		    int inSampleSize = 1;
		
		    if (height > reqHeight || width > reqWidth) {
		
		        final int halfHeight = height / 2;
		        final int halfWidth = width / 2;
		
		        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
		        // height and width larger than the requested height and width.
		        while ((halfHeight / inSampleSize) > reqHeight
		                && (halfWidth / inSampleSize) > reqWidth) {
		            inSampleSize *= 2;
		        }
		    }
		
		    return inSampleSize;
	}
    
	public int getImageId(Context context, String imageName) {
	    //int imageId =  context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		int imageId =  (this.map.get(imageName)).intValue();
	    System.out.println("Image Id : " + imageId);
	    return imageId;
	}
}

