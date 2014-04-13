package com.sunita.interviewpractice;

import java.io.IOException;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.sunita.interviewpractice.constant.StaticConstants;
import com.sunita.interviewpractice.util.SAXXMLParser;
import com.sunita.interviewpractice.vo.InterviewQuestAnsVoList;

public class MainActivity extends ExpandableListActivity{

	private AdView adView;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);


		if(StaticConstants.DISPLAY_ADD){
        	addAd();	
        }
		
		// this is not really  necessary as ExpandableListActivity contains an ExpandableList
		//setContentView(R.layout.main);

		ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		//Get passed parameter
		//Check which button pressed
        Intent mIntent = getIntent();
        String fileName = mIntent.getStringExtra("filename");
		if(fileName == null){
			return;
		}
		
		
		InterviewQuestAnsVoList interviewQuestAnsVoList;
		try {
			interviewQuestAnsVoList = SAXXMLParser.parse(getAssets().open(fileName));
			MyExpandableAdapter adapter = new MyExpandableAdapter(interviewQuestAnsVoList);
			adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
			expandableList.setAdapter(adapter);
			expandableList.setOnChildClickListener(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	private void addAd() {
    	// Create the adView.
        adView = new AdView(this);
        adView.setAdUnitId(StaticConstants.ADD_1);
        adView.setAdSize(AdSize.BANNER);
        
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLinearLayout);
        layout = (LinearLayout)findViewById(R.id.groupLinearLayout);
        // Add the adView to it.
        layout.addView(adView);

        // Initiate a generic request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the adView with the ad request.
        adView.loadAd(adRequest);
	}
}