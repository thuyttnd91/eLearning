package com.eas.elearning.business;

import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DocumentTask {

	public static void getDocPages(int ID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.DETAIL_DOCUMENT, ParamBuilder.BuildGetDocDetailParams(ID), handler);
	}
	
	public static void getFarvoriteDoc(int AccountID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.RECEIVE_FAVORITE, new RequestParams(NetworkUtility.ACCOUNT_ID, AccountID + ""), handler);
	}
	
	public static void sendFarvoriteDoc(int AccountID, int DocumentID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.SEND_FAVORITE, ParamBuilder.buildSendFarvDocParam(AccountID, DocumentID), handler);
	}
	
	public static void getDocForSubj(int subjID, int page, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.GET_ADVANCE_DOCUMENTS, ParamBuilder.buildGetDocForSubjParam(subjID, page), handler);
	}
	
	public static void deleteFavorite(int idAccount,int subjID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.DELETE_FARVORITE, ParamBuilder.BuildParamDeleteFavorite(idAccount, subjID), handler);
	}
	
	public static void getRelatedDoc(int DocumentID, AsyncHttpResponseHandler handler){
		RestConnector.post(NetworkUtility.GET_RELATED_DOCUMENT, ParamBuilder.BuildGetDocRelatedParams(DocumentID), handler);
	}

}
