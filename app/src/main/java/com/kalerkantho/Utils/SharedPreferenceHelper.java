/**
 * 
 */
package com.kalerkantho.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Ashraful
 *
 */
public class SharedPreferenceHelper {

	private static String SOCIAL_TAG = "SocialTag";
	
	
	private static final String COLLEGE_CODE = "CollegeCode";
	private static final String USER_EMAIL = "UserEmail";
	private static final String USER_ID = "UserId";
	private static final String USER_FIRST_NAME = "UserFirstName";
	private static final String USER_LAST_NAME = "UserLastName";
	private static final String USER_FULL_NAME = "UserFullName";
	private static final String USER_ABOUT_ME = "UserAboutMe";
	private static final String USER_PRO_PIC_URL = "UserProPicUrl";
	private static final String MIN_CLASS = "MinClass";
	private static final String MAX_CLASS = "MaxClass";
	private static final String SHOW_MAN = "ShowMan";
	private static final String SHOW_WOMAN = "ShowWoman";
	private static final String SHOW_FRIEND = "ShowFriend";
	private static final String SHOW_CLASS = "ShowClass";
	private static final String IN_APP_VERIATION = "InAppVariation";
	private static final String NEW_MATCH = "NewMatch";
	private static final String MESSAGE = "Message";
	private static final String GCM_REG_ID = "GCMRegId";
	private static final String APP_VER = "AppVer";
	
	public static void setAppVersion(Context context, int appVersion){
		SharedPreferences prefs = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt(APP_VER, appVersion);
		editor.commit();
	}
	
	public static int getAppVersion(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getInt(APP_VER, 0);
	}
	
	public static void setGCMId(Context context, String gcmRegId){
		SharedPreferences prefs = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(GCM_REG_ID, gcmRegId);
		editor.commit();
	}
	
	public static String getGCMId(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getString(GCM_REG_ID, "");
	}
	
	public static void setNotifyMessage(Context context, boolean notifyMessage){
		SharedPreferences prefs = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(MESSAGE, notifyMessage);
		editor.commit();
	}
	
	public static boolean getNotifyMessage(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getBoolean(MESSAGE, true);
	}
	
	public static void setNotifyNewMatch(Context context, boolean notifyNewMatch){
		SharedPreferences prefs = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(NEW_MATCH, notifyNewMatch);
		editor.commit();
	}
	
	public static boolean getNotifyNewMatch(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getBoolean(NEW_MATCH, true);
	}
	
	public static void setInappVariation(Context context, boolean useDefaultColor){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(IN_APP_VERIATION, useDefaultColor);
		editor.commit();
	}
	
	public static boolean getInInVariation(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getBoolean(IN_APP_VERIATION, true);
	}
	
	public static void setShowMan(Context context, boolean showMan){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(SHOW_MAN, showMan);
		editor.commit();
	}
	
	public static boolean getShowMan(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getBoolean(SHOW_MAN, true);
	}
	
	public static void setShowWoman(Context context, boolean showMan){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(SHOW_WOMAN, showMan);
		editor.commit();
	}
	
	public static boolean getShowWoman(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getBoolean(SHOW_WOMAN, true);
	}
	
	public static void setShowFriend(Context context, boolean showMan){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(SHOW_FRIEND, showMan);
		editor.commit();
	}
	
	public static boolean getShowFriend(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getBoolean(SHOW_FRIEND, true);
	}
	
	public static void setShowClass(Context context, boolean showClass){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(SHOW_CLASS, showClass);
		editor.commit();
	}
	
	public static boolean getShowClass(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getBoolean(SHOW_CLASS, true);
	}
	
	public static void setMinClass(Context context, int minClass){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(MIN_CLASS, minClass);
		editor.commit();
	}
	
	public static int getMinClass(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getInt(MIN_CLASS, 0);
	}
	
//	public static void setMaxClass(Context context, int minClass){
//		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
//		Editor editor = pref.edit();
//		editor.putInt(MAX_CLASS, minClass);
//		editor.commit();
//	}
//
//	public static int getMaxClass(Context context){
//		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getInt(MAX_CLASS, AllClass.getAllClass().size());
//	}
	
	
	public static void setSelectedCollegeCode(Context context, int collegeCode){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(COLLEGE_CODE, collegeCode);
		editor.commit();
	}
	
	public static int getSelectedCollegeCode(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getInt(COLLEGE_CODE, -1);
	}
	
	public static void setUserEmail(Context context, String email){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(USER_EMAIL, email);
		editor.commit();
	}
	
	public static String getUserEmail(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getString(USER_EMAIL, "");
	}
	
	public static void setUserId(Context context, int userId){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(USER_ID, userId);
		editor.commit();
	}
	
	public static int getUserId(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getInt(USER_ID, -1);
	}
	
	public static void setUserFirstName(Context context, String userFirstName){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(USER_FIRST_NAME, userFirstName);
		editor.commit();
	}
	
	public static String getUserFirstName(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getString(USER_FIRST_NAME, "");
	}
	
	public static void setUserLastName(Context context, String userLastName){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(USER_LAST_NAME, userLastName);
		editor.commit();
	}
	
	public static String getUserLastName(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getString(USER_LAST_NAME, "");
	}
	
	public static void setUserAboutMe(Context context, String userAboutMe){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(USER_ABOUT_ME, userAboutMe);
		editor.commit();
	}
	
	public static String getUserAboutMe(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getString(USER_ABOUT_ME, "");
	}
	
	public static void setUserFullName(Context context, String userFullName){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(USER_FULL_NAME, userFullName);
		editor.commit();
	}
	
	public static String getUserFullName(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getString(USER_FULL_NAME, "");
	}
	
	public static void setUserProPicUrl(Context context, String url){
		SharedPreferences pref = context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(USER_PRO_PIC_URL, url);
		editor.commit();
	}
	
	public static String getUserProPicUrl(Context context){
		return context.getSharedPreferences(SOCIAL_TAG, Context.MODE_PRIVATE).getString(USER_PRO_PIC_URL, "");
	}
	
	
}
