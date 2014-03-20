package com.sunita.pmptestpractice.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveDataToFile {

	public static final String TYPE_STRING = "String";
	public static final String TYPE_INT = "Int";
	
	public static final String CUR_POSITION = "PMPPratCurPosition";
	public static final String TOTAL_QUESTION = "PMPPratQuestCount";
	public static final String TEST_POSITION = "PMPTestQuestCount";

	
	public static void persistData(Context context, String type, String value, String fieldLabel) {
		// We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
	      SharedPreferences settings = context.getSharedPreferences(fieldLabel, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      if(type.equalsIgnoreCase(TYPE_STRING)){
	    	  editor.putString(fieldLabel, value);  
	      }else if(type.equalsIgnoreCase(TYPE_INT)){
	    	  editor.putInt(fieldLabel, Integer.valueOf(value));  
	      } 
	      // Commit the edits!
	      editor.commit();
	}
	
	public static String getData(Context context, String type, String fieldLabel) {
		// Restore preferences
		SharedPreferences settings = context.getSharedPreferences(fieldLabel, 0);
		String pos = "";
		if(type.equalsIgnoreCase(TYPE_STRING)){
			pos = settings.getString(fieldLabel, "");  
		}else if(type.equalsIgnoreCase(TYPE_INT)){
			pos = ""+settings.getInt(fieldLabel, 0);  
		} 
		return pos;
	}
	
	/*public static void persistQuestionCount(Context context, int count) {
		// We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
	      SharedPreferences settings = context.getSharedPreferences(TOTAL_QUESTION, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("total", count);

	      	PrintSysout.printSysout("Set Position : " + count);
	      // Commit the edits!
	      editor.commit();
		
	}
	
	public static void persistPageNumber(Context context, int position2) {
		// We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
	      SharedPreferences settings = context.getSharedPreferences(CUR_POSITION, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("position", position2);

	      	PrintSysout.printSysout("Set Position : " + position2);
	      // Commit the edits!
	      editor.commit();
		
	}
	
	public static int getPreferencePosition(Context context) {
    	// Restore preferences
        SharedPreferences settings = context.getSharedPreferences(CUR_POSITION, 0);
        int pos = settings.getInt("position", 1);
        
        PrintSysout.printSysout("Get Position : " + pos);
        
		return pos;
	}
	
	public static int getTotalQuestionCount(Context context) {
    	// Restore preferences
        SharedPreferences settings = context.getSharedPreferences(TOTAL_QUESTION, 0);
        int count = settings.getInt("total", 1);
        
        PrintSysout.printSysout("Get Count : " + count);
        
		return count;
	}*/
}
