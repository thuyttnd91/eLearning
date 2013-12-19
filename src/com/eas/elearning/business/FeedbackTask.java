package com.eas.elearning.business;

import org.holoeverywhere.widget.Toast;

import android.content.Context;

import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FeedbackTask {
	
	public static void sendFeedback(Context c, String msg, String email, AsyncHttpResponseHandler handler){	
		RestConnector.post(NetworkUtility.FEEDBACK_URL, ParamBuilder.BuildFeedbackParams(email, msg), handler);		
	}
	
	public static void showSuccessToast(Context c){
		Toast.makeText(c, "Phản hồi đã gửi thành công, cám ơn bạn !", Toast.LENGTH_SHORT).show();
	}
	
	public static void showFailToast(Context c){
		Toast.makeText(c, "Không thể gửi phản hồi", Toast.LENGTH_SHORT).show();
	}

}
