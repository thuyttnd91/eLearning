package com.eas.elearning.business;

import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class QATask {

	public static void getPost(int page, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.GET_QA_QUESTIONS, ParamBuilder.buildGetPostParam(page), handler);
	}
	
	public static void Post(int AccountID, String content, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.POST_QUESTION, ParamBuilder.buildPostParam(AccountID, content), handler);
	}
	
	public static void getAnswer(int PostID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.GET_ANSWERS, ParamBuilder.buildGetAnswerParam(PostID), handler);
	}
	
	public static void sendAnswer(int PostID, int AccountID, String content, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.SEND_ANSWERS, ParamBuilder.buildSendAnswerParam(PostID, AccountID, content), handler);
	}
	
}
