package com.sunita.pmptestpractice1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutUs extends Activity {
	//private WebView webinfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        
        findViewById(R.id.btnAboutUsBack).setOnClickListener(btnBack_OnClickListener);
    }
    
  //On click listener for btnLevel1
    final OnClickListener btnBack_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), Info.class);
	    		startActivity(main);        
        }
    };
}