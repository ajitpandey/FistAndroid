package com.sunita.infantlearning;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {
	private LinearLayout linearLayout;
	private AdView adView;
	  //private DisplayMetrics displayMetrics = new DisplayMetrics();
	  private final Integer fontSize1 = 36;
	  private int fontColour = Color.GREEN;
	  //private int minusDpxWidth = 45;
	  private int columnCount = 6;
	  private Map<String, String> audioMap = new HashMap<String, String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Integer fontSize = (int) (fontSize1 * getResources().getDisplayMetrics().density);
        
	    populateHasMap();
	    linearLayout = (LinearLayout)findViewById(R.id.linearLayout1);
	    TableLayout tb = new TableLayout(this);
	    //tb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    //tb.setOrientation(LinearLayout.VERTICAL);
	    //tb.setGravity(Gravity.TOP);
	    
        //tb.setStretchAllColumns(true);
	    linearLayout.addView(tb);
	    linearLayout.setGravity(Gravity.CENTER);
	    TableRow tr = null;
	    //LayoutParams params = linearLayout.getLayoutParams();
	 // Changes the height and width to the specified *pixels*
	 //params.height = 200;
	 //params.width = 200;
	 int counter = 1;
	    String[] strArr = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for(String str : strArr){
			TextView txt = new TextView(this);
			txt.setText(str);
			//System.out.println(displayMetrics.widthPixels + " - " +displayMetrics.widthPixels/4 + " * " + displayMetrics.heightPixels + " - " + displayMetrics.heightPixels/7);
			//System.out.println(rawWidth + " - " +rawWidth/4 + " * " + rawHeight + " - " + rawHeight/7);
	        //txt.setWidth(rawWidth/4);
	        //txt.setWidth(rawHeight/7);
	        txt.setTextColor(fontColour);
	        //txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
	        txt.setTextSize(fontSize);
	        txt.setGravity(Gravity.CENTER_HORIZONTAL);
	        txt.setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
	        final MediaPlayer mp = MediaPlayer.create(this, getId(str.toLowerCase(), R.raw.class));

	        txt.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                mp.start();
	            }
	        });
	        if(counter == 1){
	        	tr = new TableRow(this);
	        	tr.setGravity(Gravity.CENTER);
	        	tb.addView(tr);
	        	counter++;
	        }else if(counter >= columnCount){
	        	counter = 1;
	        }else if(counter > 1 && counter < columnCount){
	        	counter++;
	        }
	        
	        tr.addView(txt);
		}
		
		if(StaticConstants.DISPLAY_ADD){
        	addAd();	
        }
	}

	public int getId(String resourceName, Class<?> c) {
	    try {
	        Field idField = c.getDeclaredField(resourceName);
	        return idField.getInt(idField);
	    } catch (Exception e) {
	        throw new RuntimeException("No resource ID found for: "
	                + resourceName + " / " + c, e);
	    }
	}
	
	private void populateHasMap() {
		audioMap.put("A", "a.mp3");
		audioMap.put("B", "b.mp3");
		audioMap.put("C", "c.mp3");
		audioMap.put("D", "d.mp3");
		audioMap.put("E", "e.mp3");
		audioMap.put("F", "f.mp3");
		audioMap.put("G", "g.mp3");
		audioMap.put("H", "h.mp3");
		audioMap.put("I", "i.mp3");
		audioMap.put("J", "j.mp3");
		audioMap.put("K", "k.mp3");
		audioMap.put("L", "l.mp3");
		audioMap.put("M", "m.mp3");
		audioMap.put("N", "n.mp3");
		audioMap.put("O", "o.mp3");
		audioMap.put("P", "p.mp3");
		audioMap.put("Q", "q.mp3");
		audioMap.put("R", "r.mp3");
		audioMap.put("S", "s.mp3");
		audioMap.put("T", "t.mp3");
		audioMap.put("U", "u.mp3");
		audioMap.put("V", "v.mp3");
		audioMap.put("W", "w.mp3");
		audioMap.put("X", "x.mp3");
		audioMap.put("Y", "y.mp3");
		audioMap.put("Z", "z.mp3");
		
	}
	private void addAd() {
    	// Create the adView.
        adView = new AdView(this);
        adView.setAdUnitId(StaticConstants.ADD_1);
        adView.setAdSize(AdSize.BANNER);
        
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);
        //layout = (LinearLayout)findViewById(R.id.groupLinearLayout);
        // Add the adView to it.
        layout.addView(adView);

        // Initiate a generic request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the adView with the ad request.
        adView.loadAd(adRequest);
	}
}
