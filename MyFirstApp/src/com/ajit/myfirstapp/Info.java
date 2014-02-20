package com.ajit.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class Info extends Activity //implements OnClickListener
{
	private WebView webinfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        
        //webinfo=(WebView)findViewById(R.id.webinfo);
        //provide the URL path pointing to info.html
        //webinfo.loadUrl("file:///android_asset/info.html");
        
        findViewById(R.id.btnLevel1).setOnClickListener(btnLevel1_OnClickListener);
        findViewById(R.id.btnLevel2).setOnClickListener(btnLevel2_OnClickListener);
        findViewById(R.id.btnLevel3).setOnClickListener(btnLevel3_OnClickListener);
        findViewById(R.id.btnLevel4).setOnClickListener(btnLevel4_OnClickListener);
        findViewById(R.id.btnAboutUs).setOnClickListener(btnAboutUs_OnClickListener);
    }
    
    //On click listener for btnLevel1
    final OnClickListener btnLevel1_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				main.putExtra("appLevel", "level_1");
	    		startActivity(main);        
        }
    };
    
  //On click listener for btnLevel2
    final OnClickListener btnLevel2_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				main.putExtra("appLevel", "level_2");
	    		startActivity(main);        
        }
    };
    
  //On click listener for btnLevel3
    final OnClickListener btnLevel3_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				main.putExtra("appLevel", "level_3");
	    		startActivity(main);        
        }
    };
    
  //On click listener for btnLevel4
    final OnClickListener btnLevel4_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				main.putExtra("appLevel", "level_4");
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
}