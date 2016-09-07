/**
 * 
 */
package com.kalerkantho.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Ashraful
 *
 */
public class ColorUpdater {

	@SuppressWarnings("deprecation")
	public static void updateButtonColor(Context context, View v){
		if(SharedPreferenceHelper.getInInVariation(context)){
			if(v instanceof TextView){
				((TextView)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
			else if(v instanceof Button){
				((Button)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
//		v.setBackgroundColor(context.getResources().getColor(AppConstant.COLOR_MAIN));
			v.setBackgroundDrawable(ButtonShape.getHomeEditBtnBgShape(context, AppConstant.COLOR_MAIN));
//			v.setback
		}
		else{
			if(v instanceof TextView){
				((TextView)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
//		v.setBackgroundColor(context.getResources().getColor(AppConstant.COLOR_MAIN));
			v.setBackgroundDrawable(ButtonShape.getHomeEditBtnBgShape(context, AppConstant.COLOR_DEFAULT_MAIN));
		}
	}
	
	public static void updateActionBarColor(Context context, View v){
		if(SharedPreferenceHelper.getInInVariation(context)){
			if(v instanceof TextView){
				((TextView)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
//		v.setBackgroundColor(context.getResources().getColor(AppConstant.COLOR_MAIN));
			v.setBackgroundDrawable(ButtonShape.getActionBarBgShape(context, AppConstant.COLOR_MAIN));
		}
		else{
			if(v instanceof TextView){
				((TextView)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
//		v.setBackgroundColor(context.getResources().getColor(AppConstant.COLOR_MAIN));
			v.setBackgroundDrawable(ButtonShape.getActionBarBgShape(context, AppConstant.COLOR_DEFAULT_MAIN));
		}
	}
	
	public static void updateTextColor(Context context, TextView txtVw){
		if(SharedPreferenceHelper.getInInVariation(context)){
			txtVw.setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
		}
		else{
			txtVw.setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
		}
	}
	
	public static void updateTextColorMain(Context context, TextView txtVw){
		if(SharedPreferenceHelper.getInInVariation(context)){
			txtVw.setTextColor(Color.parseColor(AppConstant.COLOR_MAIN));
		}
		else{
			txtVw.setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_MAIN));
		}
	}
	
	public static void updateRectButtonColor(Context context, Button btn){
		if(SharedPreferenceHelper.getInInVariation(context)){
			btn.setBackgroundColor(Color.parseColor(AppConstant.COLOR_MAIN));
			btn.setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
		}
		else{
			btn.setBackgroundColor(Color.parseColor(AppConstant.COLOR_DEFAULT_MAIN));
			btn.setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
		}
	}
	
	public static void updateRectViewColor(Context context, View v){
		if(SharedPreferenceHelper.getInInVariation(context)){
			v.setBackgroundColor(Color.parseColor(AppConstant.COLOR_MAIN));
		}
		else{
			v.setBackgroundColor(Color.parseColor(AppConstant.COLOR_DEFAULT_MAIN));
		}
	}
	
	public static void updateSettingsButton(Context context, View v){
		v.setBackgroundDrawable(ButtonShape.getHomeBgShape(context, AppConstant.COLOR_SETTINGS));
	}
	
	public static void updateShareButton(Context context, View v){
		v.setBackgroundDrawable(ButtonShape.getHomeBgShape(context, AppConstant.COLOR_SHARE));
	}
	
	private static Drawable updateImageObjectColor(Context context, Bitmap bmp){
		if(SharedPreferenceHelper.getInInVariation(context)){
			
			return new BitmapDrawable(context.getResources(), MaskImageWithColor.maskImageWithColor(bmp, AppConstant.BTN_COLOR_ORIGIN, AppConstant.COLOR_MAIN));
		}
		else{
			
			return new BitmapDrawable(context.getResources(), MaskImageWithColor.maskImageWithColor(bmp, AppConstant.BTN_COLOR_ORIGIN, AppConstant.COLOR_DEFAULT_MAIN));
		}
	}
	
	public static void makeToggleDrawable(Context context, Bitmap on_bmp, Bitmap off_bmp, View v){
		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[] {android.R.attr.state_checked}, updateImageObjectColor(context, on_bmp));
		sld.addState(new int[] { }, new BitmapDrawable(context.getResources(), off_bmp));
		
		v.setBackground(sld);
	}
	
	public static void updateButtonTextColor(Context context, Button btn){
		btn.setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
	}
	
	public static void updateOvalViewWithAlpha(Context context, View v){
		if(SharedPreferenceHelper.getInInVariation(context)){
			if(v instanceof TextView){
				((TextView)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
			else if(v instanceof Button){
				((Button)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
//		v.setBackgroundColor(context.getResources().getColor(AppConstant.COLOR_MAIN));
			v.setBackgroundDrawable(ButtonShape.getHomeEditBtnBgShapeTransparent(context, AppConstant.COLOR_MAIN));
//			v.setback
		}
		else{
			if(v instanceof TextView){
				((TextView)v).setTextColor(Color.parseColor(AppConstant.COLOR_DEFAULT_SECONDARY));
			}
//		v.setBackgroundColor(context.getResources().getColor(AppConstant.COLOR_MAIN));
			v.setBackgroundDrawable(ButtonShape.getHomeEditBtnBgShapeTransparent(context, AppConstant.COLOR_DEFAULT_MAIN));
		}
	}
}
