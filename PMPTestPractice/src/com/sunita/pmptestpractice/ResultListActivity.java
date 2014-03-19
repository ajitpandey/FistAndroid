package com.sunita.pmptestpractice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sunita.pmptestpractice.constant.StaticConstants;
import com.sunita.pmptestpractice.file.io.CreateXMLFile;
import com.sunita.pmptestpractice.util.PrintSysout;
import com.sunita.pmptestpractice.vo.TestResult;

public class ResultListActivity extends Activity  //implements OnClickListener 
{
	private LinearLayout rstListLinearLayout;
	private String pageType;
	  private List<TestResult> trVoList;
	  private Map<String, String> trVOMap = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);

        this.rstListLinearLayout = (LinearLayout)findViewById(R.id.rstListLinearLayout);
		        
        //Check which button pressed
        Intent mIntent = getIntent();
        this.pageType = mIntent.getStringExtra(StaticConstants.ACTIVITY_CONTEXT_VARIABLE);
        

        if(this.pageType.equalsIgnoreCase(StaticConstants.ACTIVITY_RESULT_LIST)){
        	this.trVoList = getTestResultVoList();
        	populateResultTestQA(this, this.trVoList);
        }
        
        if(this.trVoList == null || this.trVoList.size() < 1){
        	return;
        }
              
    }

    private void populateResultTestQA(Activity resultListActivity, List<TestResult> trVoList1) {
    	int count = 1;
    	for(TestResult trVo : trVoList1){
    		Button button = new Button(resultListActivity);
    		button.setId(count);
    		button.setWidth(0);
    		button.setGravity(Gravity.CENTER);
    		button.setText(trVo.getDateTime() + " \n Result " + trVo.getScore() + "/24");
    		button.setOnClickListener(btn_OnClickListener);
    		this.rstListLinearLayout.addView(button);
    		//this.trVOMap.put(""+count, trVo);
    		count++;
    	}
    }

	private List<TestResult> getTestResultVoList() {
		List<TestResult> trVoList = new ArrayList<TestResult>();
			try {
				List<String> xmlList =  CreateXMLFile.readfile(this, StaticConstants.TEST_RESULT_FILE_NAME);
				int count =1;
				for(String str : xmlList){
					PrintSysout.printSysout("TestResult Vo : " + str);
					List<TestResult> trList = CreateXMLFile.parseStringXML(str);
					
					trVOMap.put(""+count, str);
					trVoList.add((TestResult)trList.get(0));
					count++;
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return trVoList;
	}

	/*@Override
	public void onClick(View arg0) {
		String id = ""+arg0.getId();
		Intent main = new Intent(getApplicationContext(), MainActivity.class);
		main.putExtra(StaticConstants.TEST_RESULT_PARM1, this.trVOMap.get(id));
		main.putExtra(StaticConstants.ACTIVITY_CONTEXT_VARIABLE, StaticConstants.TEST_RESULT);
		PrintSysout.printSysout("Start MainActivity");
		this.startActivity(main);
	}*/
	
	//On click listener for btnLevel1
    final OnClickListener btn_OnClickListener = new OnClickListener() {
        public void onClick(final View v) {
        	String id = ""+v.getId();
    		Intent main = new Intent(getApplicationContext(), MainActivity.class);
    		String str = trVOMap.get(id);
    		PrintSysout.printSysout(" trVOMap.get(id) : " + id + " - str : " +str);
    		main.putExtra(StaticConstants.TEST_RESULT_PARM1, str);
    		main.putExtra(StaticConstants.ACTIVITY_CONTEXT_VARIABLE, StaticConstants.TEST_RESULT);
    		PrintSysout.printSysout("Start MainActivity");
    		startActivity(main);      
        }
    };

}

