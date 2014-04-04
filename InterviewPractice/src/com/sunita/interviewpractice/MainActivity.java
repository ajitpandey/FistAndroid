package com.sunita.interviewpractice;



import java.io.IOException;
import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.sunita.interviewpractice.util.SAXXMLParser;
import com.sunita.interviewpractice.vo.InterviewQuestAnsVoList;

public class MainActivity extends ExpandableListActivity{

	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// this is not really  necessary as ExpandableListActivity contains an ExpandableList
		//setContentView(R.layout.main);

		ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		InterviewQuestAnsVoList interviewQuestAnsVoList;
		try {
			interviewQuestAnsVoList = SAXXMLParser.parse(getAssets().open("java_interview.xml"));
			MyExpandableAdapter adapter = new MyExpandableAdapter(interviewQuestAnsVoList);
			adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
			expandableList.setAdapter(adapter);
			expandableList.setOnChildClickListener(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
}