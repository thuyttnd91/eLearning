package com.eas.elearning.business;

import java.util.ArrayList;

import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class DataNetwork {

	public static void getNewDocuments(int page,AsyncHttpResponseHandler handler){
			
			RestConnector.post("GET_DOCUMENTS", ParamBuilder.buildNewDocumentParam(page), handler);

	}
	
	public static void searchDocument(String title,int page, AsyncHttpResponseHandler handler){
		RestConnector.post("GET_ADVANCE_DOCUMENTS", ParamBuilder.buildSearchDocumentParam(title, page), handler);
	}
	
	public static void getAdvanceDocuments(int page, String Class, ArrayList<Integer> Subject, String order, String Title,AsyncHttpResponseHandler handler){
		
		RestConnector.post("GET_ADVANCE_DOCUMENTS", ParamBuilder.buildDocumentAdvanceParam(page, Class, Subject, order, Title), handler);

}

	
}
