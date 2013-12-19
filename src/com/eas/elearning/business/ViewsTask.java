package com.eas.elearning.business;

import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ViewsTask {
	
	public static void AddViewCount(int DocumentID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.ADD_VIEW_COUNT, ParamBuilder.BuildViewCountParams(DocumentID), handler);
	}
	
	public static void AddDownloadCount(int DocumentID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.ADD_DOWNLOAD_COUNT, ParamBuilder.BuildDownloadCountParams(DocumentID), handler);
	}

}
