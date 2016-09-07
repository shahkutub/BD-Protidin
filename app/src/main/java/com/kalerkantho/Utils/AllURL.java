package com.kalerkantho.Utils;

import android.annotation.SuppressLint;

import java.util.Vector;

@SuppressLint("DefaultLocale")
public class AllURL {

	private static String getcommonURLWithParamAndAction(String action, Vector<KeyValue> params) {

		if (params == null || params.size() == 0) {
			return BaseURL.HTTP + action;
		} else {
			String pString = "";

			for (final KeyValue obj : params) {

				pString += obj.getKey().trim() + "=" + obj.getValue().trim()
						+ "&";
			}

			if (pString.endsWith("&")) {
				pString = pString.subSequence(0, pString.length() - 1)
						.toString();
			}

			return BaseURL.HTTP + action + "?" + UrlUtils.encode(pString);
		}
	}




	public static String getCatList(String cat,int pagNumber) {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("my_news"+"/"+cat+"/"+pagNumber, temp);
	}


	public static String getMenuList() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("menu_list", temp);
	}
	public static String pushIDURL() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("push-id-register", temp);
	}

	public static String registrationURL() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("reg", temp);
	}
	public static String loginURL() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("login", temp);
	}
	public static String getDetails(String id,String user) {
		final Vector<KeyValue> temp = new Vector();
		//temp.add(new KeyValue("token",id));
		return getcommonURLWithParamAndAction("news_details/"+id+"/"+user, temp);
	}

	public static String getPhotoList(String cat) {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("photogallery"+"/"+cat, temp);
	}


	public static String getNirbachitoList(String rURL,int pagNumber) {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("my_news"+"/"+rURL+"/"+pagNumber,temp);
	}

	public static String getHomeNews() {
		final Vector<KeyValue> temp = new Vector();
		//temp.add(new KeyValue("token",token));
		return getcommonURLWithParamAndAction("homenews", temp);
	}
	public static String addFriendUrl(String token,String UserId,String matched_id,String  passed) {
		final Vector<KeyValue> temp = new Vector();
		temp.add(new KeyValue("token",token));
		temp.add(new KeyValue("UserId",UserId));
		temp.add(new KeyValue("matched_id",matched_id));
		temp.add(new KeyValue("passed",passed));
		return getcommonURLWithParamAndAction("add-friend", temp);
	}

	public static String commentListUrl(String news_id,String user_id,int pagNumber) {
		final Vector<KeyValue> temp = new Vector();
//		temp.add(new KeyValue("news_id",news_id));
//		temp.add(new KeyValue("user_id",user_id));
		return getcommonURLWithParamAndAction("comment-on-news"+"/"+news_id+"/"+pagNumber+"/"+user_id, temp);
	}

	public static String submitFeedbackURL() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("submit-feedback", temp);
	}

	public static String commentURL() {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("comment", temp);
	}

	public static String getHelp() {
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		return getcommonURLWithParamAndAction("help", temp);
	}



	public static String getLikeDislike(String userId,String newsId,String likeDislike,String dislikelike) {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("like-dislike"+"/"+userId+"/"+newsId+"/"+likeDislike+"/"+dislikelike, temp);
	}

	public static String getUpdateSetting(String userId,String notificationStatus) {
		final Vector<KeyValue> temp = new Vector();
		return getcommonURLWithParamAndAction("notification"+"/"+userId+"/"+notificationStatus, temp);
	}

}