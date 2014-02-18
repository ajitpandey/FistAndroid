package com.ajit.myfirstapp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ajit.myfirstapp.vo.RawStringContent;

public class StringUtil {

	public static Map<String, RawStringContent> getImageIdFromString(String rawString){
		Map<String, RawStringContent> map = new HashMap<String, RawStringContent>();
		RawStringContent rawStringContent = null;
		String strImage = "_s-img_";
		String[] splittedImageString = rawString.split(strImage);
	    PrintSysout.printSysout(splittedImageString.length);
	    int count = 1;
		for(String str : splittedImageString){
			PrintSysout.printSysout(str);
			String pattern = "(.*),(\\d)_e-img_(.*)";

		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		      Matcher m = r.matcher(str);
		      if (m.find( )) {
		    	  PrintSysout.printSysout("ImageName: " + m.group(1) );
			         PrintSysout.printSysout("ImageCount: " + m.group(2) );
			         PrintSysout.printSysout("more text : " + m.group(3) );
			         rawStringContent = new RawStringContent();
			         rawStringContent.type = "image";
			         rawStringContent.value = m.group(1) +","+ m.group(2);
			         map.put(""+count, rawStringContent);
		         
		      } else {
		         rawStringContent = new RawStringContent();
		         rawStringContent.type = "text";
		         rawStringContent.value = str;
		         map.put(""+count, rawStringContent);
		      }
		      count++;
		}
		
		return map;
	}
}
