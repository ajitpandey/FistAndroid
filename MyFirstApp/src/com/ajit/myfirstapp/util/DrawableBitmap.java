package com.ajit.myfirstapp.util;

import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class DrawableBitmap {

	public static Bitmap getBitmapImage(Resources resources, Map<String, Integer> mapImage, String option1) {
    	String[] strArr = option1.split(",");
    	Bitmap bm = joinImages(resources, mapImage, strArr[0], Integer.valueOf(strArr[1]).intValue());
    	
		return bm;
	}
	
	public static Drawable getDrawableImage(Resources resources, Map<String, Integer> mapImage, String option1) {
    	String[] strArr = option1.split(",");
    	Bitmap bm = joinImages(resources, mapImage, strArr[0], Integer.valueOf(strArr[1]).intValue());
    	Drawable d = new BitmapDrawable(resources, bm);
		return d;
	}

	public static Bitmap joinImages(Bitmap bmp1, Bitmap bmp2)
	{
	    if (bmp1 == null){
	    	return bmp2;
	    }else if(bmp2 == null){
	    	return bmp1;
	    }
	        
	    int height = bmp1.getHeight();
	    if (height < bmp2.getHeight())
	        height = bmp2.getHeight();

	    Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth() + bmp2.getWidth(), height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(bmOverlay);
	    canvas.drawBitmap(bmp1, 0, 0, null);
	    canvas.drawBitmap(bmp2, bmp1.getWidth(), 0, null);
	    return bmOverlay;
	}
	
	private static Bitmap joinImages(Resources resources, Map<String, Integer> mapImage, Bitmap bmp1, String image1)
	{
	    Bitmap bmp2;
	    //bmp1 = decodeSampledBitmapFromResource(getResources(), getImageId(this, image1), 50, 50);
	    bmp2 = decodeSampledBitmapFromResource(resources, getImageId(mapImage, image1), 30, 30);
	    if (bmp1 == null || bmp2 == null)
	        return bmp1;
	    int height = bmp1.getHeight();
	    if (height < bmp2.getHeight())
	        height = bmp2.getHeight();

	    Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth() + bmp2.getWidth(), height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(bmOverlay);
	    canvas.drawBitmap(bmp1, 0, 0, null);
	    canvas.drawBitmap(bmp2, bmp1.getWidth(), 0, null);
	    return bmOverlay;
	}
	
	private static Bitmap joinImages(Resources resources, Map<String, Integer> mapImage, String image1, int count)
	{
		Bitmap bm = decodeSampledBitmapFromResource(resources, getImageId(mapImage, image1), 30, 30);
		for(int i = 1 ; i < count; i++){
			bm = joinImages(resources, mapImage, bm, image1);
		}
		
	    return bm;
	}
	
	public static int getImageId(Map<String, Integer> mapImage, String imageName) {
	    //int imageId =  context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		int imageId =  Integer.valueOf("" + mapImage.get(imageName)).intValue();
	    //PrintSysout.printSysout("Image Id : " + imageId);
	    return imageId;
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
		    // Raw height and width of image
		    final int height = options.outHeight;
		    final int width = options.outWidth;
		    int inSampleSize = 1;
		
		    if (height > reqHeight || width > reqWidth) {
		
		        final int halfHeight = height / 2;
		        final int halfWidth = width / 2;
		
		        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
		        // height and width larger than the requested height and width.
		        while ((halfHeight / inSampleSize) > reqHeight
		                && (halfWidth / inSampleSize) > reqWidth) {
		            inSampleSize *= 2;
		        }
		    }
		
		    return inSampleSize;
	}
}
