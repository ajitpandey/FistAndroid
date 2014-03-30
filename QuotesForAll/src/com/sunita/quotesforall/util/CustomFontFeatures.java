package com.sunita.quotesforall.util;

import android.app.Activity;

public class CustomFontFeatures {
	
	public static float fontSize(Activity activity, int size){
		return size * activity.getResources().getDisplayMetrics().density;
	}
}
