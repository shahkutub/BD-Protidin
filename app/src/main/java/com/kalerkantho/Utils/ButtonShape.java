/**
 * 
 */
package com.kalerkantho.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * @author Ashraful
 *
 */
public class ButtonShape {

	public static Drawable getHomeEditBtnBgShape(Context context, String mainColor){
		GradientDrawable gDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{Color.parseColor(mainColor), Color.parseColor(mainColor), Color.parseColor(mainColor)});
		gDrawable.setGradientType(GradientDrawable.RECTANGLE);
//		gDrawable.setGradientRadius(10f);
		gDrawable.setCornerRadius(100f);
		
		return gDrawable;
	}
	
	public static Drawable getBgShape(Context context){
		GradientDrawable gDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{ 
				Color.parseColor("#FF0000"),
				Color.parseColor("#FF0000"), 
				Color.parseColor("#FF0000") });
		gDrawable.setGradientType(GradientDrawable.RECTANGLE);
//		gDrawable.setGradientRadius(10f);
		gDrawable.setCornerRadius(100f);
		
		return gDrawable;
	}
	
	public static Drawable getActionBarBgShape(Context context, String mainColor){
		GradientDrawable gDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{Color.parseColor(mainColor), Color.parseColor(mainColor), Color.parseColor(mainColor)});
		gDrawable.setGradientType(GradientDrawable.RECTANGLE);
//		gDrawable.setGradientRadius(10f);
//		gDrawable.setCornerRadius(100f);
		
		return gDrawable;
	}
	
	public static Drawable getHomeBgShape(Context context, String customColor){
		GradientDrawable gDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{Color.parseColor(customColor), Color.parseColor(customColor), Color.parseColor(customColor)});
		gDrawable.setGradientType(GradientDrawable.RECTANGLE);
//		gDrawable.setGradientRadius(10f);
		gDrawable.setCornerRadius(100f);
		
		return gDrawable;
	}
	
	public static Drawable getHomeEditBtnBgShapeTransparent(Context context, String mainColor){
		mainColor = mainColor.replace("#", "");
		mainColor = "#99" + mainColor;
		
		GradientDrawable gDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{Color.parseColor(mainColor), Color.parseColor(mainColor), Color.parseColor(mainColor)});
		gDrawable.setGradientType(GradientDrawable.RECTANGLE);
//		gDrawable.setGradientRadius(10f);
		gDrawable.setCornerRadius(100f);
		
		return gDrawable;
	}
	
}
