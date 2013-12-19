package com.eas.elearning.network;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RestConnector {
	private static String IP="54.243.244.187:8080";
	public static String BASE_API_URL = "http://"+IP+"/Services/";
	private static int timeOut=NetworkUtility.DEFAULT_TIME_OUT;
	private static AsyncHttpClient client=new AsyncHttpClient();
	public static void post(String url,RequestParams params,AsyncHttpResponseHandler response){
		client.setTimeout(timeOut);
		client.post(getAbsoluteUrl(url),params,response);
		Log.e("RestConnector", ""+getAbsoluteUrl(url) );
		Log.e("RestConnector", "param"+params );
	}
	private static String getAbsoluteUrl(String relativeUrl) {
        return new String(BASE_API_URL + relativeUrl);
    }
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(timeOut);
    	client.get(getAbsoluteUrl(url), params, responseHandler);
    }
	 public static void cancelRequest(Context c){
	    	client.cancelRequests(c, true);
	    }

	    
	    
	    public void setTimeOut(int timeOut){
	    	RestConnector.timeOut = timeOut;
	    }
	    
	    public static void resetURL(){
	    	BASE_API_URL = "http://"+IP+"/Services/Index/";
	    }

	
}
