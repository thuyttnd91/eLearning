package com.eas.elearning.business;

import org.holoeverywhere.widget.Toast;

import android.content.Context;

import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class CommentTask {
	
	public static void getDocComment(int DocumentID, AsyncHttpResponseHandler handle){
		RestConnector.post(NetworkUtility.GET_COMMENT_DOC_URL, ParamBuilder.BuildGetCommentParams(DocumentID), handle);
	}
	
	public static void CommentDoc(int AccountID, int DocumentID, String msg, AsyncHttpResponseHandler handle){
		RestConnector.post(NetworkUtility.COMMENT_DOC_URL, ParamBuilder.BuildCommentParams(AccountID, DocumentID, msg), handle);
	}
	
	public static void showCmtFailToast(Context c){
		Toast.makeText(c, "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
	}
	
	public static void getDetailComment(int DocDetailID, AsyncHttpResponseHandler handle){
		RestConnector.post(NetworkUtility.GET_DETAIL_COMMENT, ParamBuilder.BuildGetDetailCommentParams(DocDetailID), handle);
	}
	
	public static void CommentDocDetail(int AccountID, int DocDetailID, String msg, AsyncHttpResponseHandler handle){
		RestConnector.post(NetworkUtility.COMMENT_DETAIL, ParamBuilder.BuildCommentDetailParams(AccountID, DocDetailID, msg), handle);
	}
	

}
