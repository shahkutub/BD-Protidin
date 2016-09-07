/**
 * 
 */
package com.kalerkantho.Utils;

import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author Ashraful
 *
 */
public class MaskImageWithColor {

	/**
	 * 
	 * @param source 		Main bitmap to be updated. Only white part of the bitmap will be updated.
	 * @param colorCode 	Color to be used to replace white color from source image. Provide color in HTML format (#AARRGGBB)
	 * @return 				Result bitmap after replace white color from source image with given color
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap maskImageWithColor(Bitmap source, String colorCode){
		if(source == null){
			return null;
		}
		Bitmap destImage = source.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(destImage);
		
		int mColor = Color.parseColor(colorCode);
		
		Paint paint = new Paint();
		
//		paint.setARGB(255, 61, 191, 255);
		paint.setColor(mColor);
		paint.setXfermode(new AvoidXfermode(Color.WHITE, 0, AvoidXfermode.Mode.TARGET));
		
		canvas.drawPaint(paint);
		
		
		return destImage;
	}
	
	/**
	 * 
	 * @param source 		Main bitmap to be updated. Only white part of the bitmap will be updated.
	 * @param colorCode 	Color to be used to replace white color from source image. Provide color in HEX format (0xAARRGGBB)
	 * @return 				Result bitmap after replace white color from source image with given color
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap maskImageWithColor(Bitmap source, int colorCode){
		if(source == null){
			return null;
		}
		Bitmap destImage = source.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(destImage);
		
//		int mColor = Color.parseColor(colorCode);
		
		Paint paint = new Paint();
		
//		paint.setARGB(255, 61, 191, 255);
		paint.setColor(colorCode);
		paint.setXfermode(new AvoidXfermode(Color.WHITE, 0, AvoidXfermode.Mode.TARGET));
		
		canvas.drawPaint(paint);
		
		
		return destImage;
	}
	
	/**
	 * 
	 * @param source 		Main bitmap to be updated. Only white part of the bitmap will be updated.
	 * @param colorCode 	Color to be used to replace white color from source image. Provide color in HTML format (#AARRGGBB)
	 * @return 				Result bitmap after replace white color from source image with given color
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap maskImageWithColor(Bitmap source, String originalColor, String colorCode){
		if(source == null){
			return null;
		}
		Bitmap destImage = source.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(destImage);
		
		int mColor = Color.parseColor(colorCode);
		
		Paint paint = new Paint();
		
//		paint.setARGB(255, 61, 191, 255);
		int orClr = Color.parseColor(originalColor);
		paint.setColor(mColor);
		paint.setXfermode(new AvoidXfermode(orClr, 0, AvoidXfermode.Mode.TARGET));
		
		canvas.drawPaint(paint);
		
		
		return destImage;
	}
	
}
