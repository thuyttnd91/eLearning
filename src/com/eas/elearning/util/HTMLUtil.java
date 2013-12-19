package com.eas.elearning.util;

public class HTMLUtil {
	public static String getFileNameInContentDispositionTag(String content_disposition){
		return content_disposition.substring(43, content_disposition.length()-1);
	}
	
	public static String getFullUrl(String url){
		return ("http://54.243.244.187:8080" + url);
	}
	
	public static String getFileNameFromURL(String url){
		return url.substring(50, url.length());
	}
	
	public static String getFileExtensionFromURL(String url){
		String filename  = getFileNameFromURL(url);
		return filename.substring(filename.indexOf("."));
	}
	
	public static String getYoutubeVideoURLID(String url){
		return url.substring(29);
	}
}
