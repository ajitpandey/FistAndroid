package com.ajit.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Start extends Activity {
	private WebView webinfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        
    }
}