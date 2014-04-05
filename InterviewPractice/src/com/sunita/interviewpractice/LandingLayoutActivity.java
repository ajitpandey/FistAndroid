package com.sunita.interviewpractice;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sunita.interviewpractice.util.SAXXMLParser;
import com.sunita.interviewpractice.vo.LandingVo;
import com.sunita.interviewpractice.vo.LandingVoList;

public class LandingLayoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_layout);
		
		try {
			LandingVoList landingVoList = SAXXMLParser.parseLanding(getAssets().open("landing.xml"));
			buildButtonOnScreen(landingVoList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//findViewById(R.id.btnLevel1).setOnClickListener(btnLevel1_OnClickListener);
	}
	
	private void buildButtonOnScreen(LandingVoList landingVoList) {
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.infoLinearLayout);
		
		for(LandingVo landingVo : landingVoList.getVoList()){
			final String value = landingVo.getValue();
			Button btn = new Button(this);
			btn.setText(landingVo.getText());
			linearLayout.addView(btn);
			//Adding click listner
			btn.setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	//invoke the Info activity
					Intent main = new Intent(getApplicationContext(), MainActivity.class);
					//v.getId()
					main.putExtra("filename", value);
		    		startActivity(main); 
		        }
		    });
		}
	}

	//On click listener for btnLevel1
    final OnClickListener btnLevel1_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				//v.getId()
				main.putExtra("filename", "java_interview.xml");
	    		startActivity(main);        
        }
    };

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_layout, menu);
		return true;
	}*/

}
