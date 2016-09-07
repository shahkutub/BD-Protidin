package com.kalerkantho.Utils;


public class BaseURL {

	public static String HTTP = "http://www.kalerkantho.com/appapi/";
	//public static String ADD_API_TOKEN="?api_token=";

	/**
	 * @return the hTTP
	 */
	public static String makeHTTPURL(String param) {
		return BaseURL.HTTP + UrlUtils.encode(param);
	}

	/**
	 * @param hTTP
	 *            the hTTP to set
	 */
	public static void setHTTP(final String hTTP) {
		BaseURL.HTTP = hTTP;
	}

}
