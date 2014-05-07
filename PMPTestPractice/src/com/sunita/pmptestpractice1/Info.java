package com.sunita.pmptestpractice1;

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
import com.sunita.pmptestpractice1.constant.StaticConstants;
import com.sunita.pmptestpractice1.util.SaveDataToFile;


public class Info extends Activity //implements OnClickListener
{
	private AdView adView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        
        if(StaticConstants.DISPLAY_ADD){
        	addAd();	
        }
           
        //webinfo=(WebView)findViewById(R.id.webinfo);
        //provide the URL path pointing to info.html
        //webinfo.loadUrl("file:///android_asset/info.html");
        
        findViewById(R.id.btnLevel1).setOnClickListener(btnLevel1_OnClickListener);
        findViewById(R.id.btnLevel2).setOnClickListener(btnLevel2_OnClickListener);
        findViewById(R.id.btnLevel3).setOnClickListener(btnLevel3_OnClickListener);
        findViewById(R.id.btnAboutUs).setOnClickListener(btnAboutUs_OnClickListener);
        
        updateButtonLabel();
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

	private void updateButtonLabel() {
		int position =  Integer.valueOf(SaveDataToFile.getData(this, SaveDataToFile.TYPE_INT, SaveDataToFile.CUR_POSITION));
		int count = Integer.valueOf(SaveDataToFile.getData(this, SaveDataToFile.TYPE_INT, SaveDataToFile.TOTAL_QUESTION));
		if(position != 0 && count != 0){
			Button btnLevel1 = (Button) findViewById(R.id.btnLevel1);
			btnLevel1.setText(btnLevel1.getText() + " - " + position + " / " + count);	
		}
		
	}

	//On click listener for btnLevel1
    final OnClickListener btnLevel1_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				main.putExtra("appLevel", StaticConstants.ACTIVITY_PRACTICE);
	    		startActivity(main);        
        }
    };
    
  //On click listener for btnLevel2
    final OnClickListener btnLevel2_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), FormulaActivity.class);
				main.putExtra("filename", "pmp_formula.xml");
	    		startActivity(main);        
        }
    };
  
  //On click listener for btnLevel2
    final OnClickListener btnLevel3_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), FormulaActivity.class);
				main.putExtra("filename", "pmp_glossary.xml");
	    		startActivity(main);        
        }
    };
    
  //On click listener for btnLevel1
    final OnClickListener btnAboutUs_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), AboutUs.class);
	    		startActivity(main);        
        }
    };
    
    @Override
    public void onPause() {
    	if(StaticConstants.DISPLAY_ADD)
    		adView.pause();
      super.onPause();
    }

    @Override
    public void onResume() {
      super.onResume();
      if(StaticConstants.DISPLAY_ADD)
    	  adView.resume();
    }

    @Override
    public void onDestroy() {
    	if(StaticConstants.DISPLAY_ADD)
    		adView.destroy();
      super.onDestroy();
    }
}