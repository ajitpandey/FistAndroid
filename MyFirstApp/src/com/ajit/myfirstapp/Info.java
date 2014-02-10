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
        
        webinfo=(WebView)findViewById(R.id.webinfo);
        //provide the URL path pointing to info.html
        webinfo.loadUrl("file:///android_asset/info.html");
        
        findViewById(R.id.imageButton1).setOnClickListener(mButton1_OnClickListener);
    }
    
  //On click listener for button1
    final OnClickListener mButton1_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	//invoke the Info activity
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				System.out.println("Main : " + main.toString());
	    		startActivity(main);        
        }
    };
    
}