package com.sunita.interviewpractice;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.sunita.interviewpractice.constant.StaticConstants;
import com.sunita.interviewpractice.util.SAXXMLParser;
import com.sunita.interviewpractice.vo.LandingVo;
import com.sunita.interviewpractice.vo.LandingVoList;

public class LandingLayoutActivity extends Activity {

	
	private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_layout);
		
		if(StaticConstants.DISPLAY_ADD){
        	addAd();	
        }
		
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

	private void addAd() {
    	// Create the adView.
        adView = new AdView(this);
        adView.setAdUnitId(StaticConstants.ADD_1);
        adView.setAdSize(AdSize.BANNER);
        
        
     // Lookup your LinearLayout assuming it's been given
        // the attribute android:id="@+id/mainLayout".
        LinearLayout layout = (LinearLayout)findViewById(R.id.infoLinearLayout);

        // Add the adView to it.
        layout.addView(adView);

        // Initiate a generic request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the adView with the ad request.
        adView.loadAd(adRequest);
	}
}
