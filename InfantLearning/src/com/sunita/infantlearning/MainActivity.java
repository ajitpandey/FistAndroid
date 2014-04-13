package com.sunita.infantlearning;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private LinearLayout linearLayout;

	  private DisplayMetrics displayMetrics = new DisplayMetrics();
	  private Integer fontSize = 40;
	  private int fontColour = Color.GREEN;
	  private int minusDpxWidth = 30;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    linearLayout = (LinearLayout)findViewById(R.id.linearLayout1);
	    String[] strArr = new String[]{"A","B","C","D","E","F","G"," H","I","J","K","L","M","N","O","P","Q"};
		for(String str : strArr){
			TextView txt = new TextView(this);
			txt.setText(str);
	        //txt.setMaxWidth(displayMetrics.widthPixels - minusDpxWidth);
	        txt.setTextColor(fontColour);
	        txt.setTextSize(fontSize);
	        //txt.setWidth(10);
	        //txt.setMaxWidth(30);
	        //txt.setMaxEms(3);
	        txt.setEms(1);
	        //txt.set
	        txt.setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
	        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a);

	        txt.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                //Log.v(TAG, "Playing sound...");
	                mp.start();
	            }
	        });
	        linearLayout.addView(txt);
		}
	}
}
