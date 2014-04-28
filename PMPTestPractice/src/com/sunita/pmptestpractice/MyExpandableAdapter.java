package com.sunita.pmptestpractice;



import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunita.pmptestpractice.vo.InterviewQuestAnsVo;
import com.sunita.pmptestpractice.vo.InterviewQuestAnsVoList;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	//private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	//private ArrayList<String> parentItems
	private ArrayList<String> child;
	private InterviewQuestAnsVoList interviewQuestAnsVoList;

	/*public MyExpandableAdapter(ArrayList<String> parents, ArrayList<Object> childern) {
		this.parentItems = parents;
		this.childtems = childern;
	}*/

	public MyExpandableAdapter(InterviewQuestAnsVoList interviewQuestAnsVoList) {
		this.interviewQuestAnsVoList = interviewQuestAnsVoList;
	}
	
	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		InterviewQuestAnsVo interviewQuestAnsVo = interviewQuestAnsVoList.getInterviewQuestAnsVoList().get(groupPosition);
		child = (ArrayList<String>) interviewQuestAnsVo.getAnswers();
		

		TextView textView = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.group, null);
		}

		textView = (TextView) convertView.findViewById(R.id.textView1);
		textView.setText(child.get(childPosition));
		textView.setSingleLine(false);
		
		//textView.setText(interviewQuestAnsVo.getAnswers().get(childPosition));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(activity, child.get(childPosition),
						Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row, null);
		}
		
		InterviewQuestAnsVo interviewQuestAnsVo = interviewQuestAnsVoList.getInterviewQuestAnsVoList().get(groupPosition);

		//((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
		((CheckedTextView) convertView).setText(interviewQuestAnsVo.getQuestion());
		((CheckedTextView) convertView).setChecked(isExpanded);
		((CheckedTextView) convertView).setBackgroundResource(R.drawable.buttonbg);

		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		//return 1; //There will be only one answer for each question
		//return ((ArrayList<String>) childtems.get(groupPosition)).size();
		return ((InterviewQuestAnsVo)interviewQuestAnsVoList.getInterviewQuestAnsVoList().get(groupPosition)).getAnswers().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return interviewQuestAnsVoList.getInterviewQuestAnsVoList().size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
