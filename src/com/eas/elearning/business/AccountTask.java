package com.eas.elearning.business;

import org.holoeverywhere.widget.Toast;

import android.content.Context;

import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class AccountTask {
	public static void Login(String url,String username,String password,AsyncHttpResponseHandler handler){
		RestConnector.post(url, ParamBuilder.BuildParamLogin(username, password), handler);
	}
	public static void GetSchools(String url,AsyncHttpResponseHandler handler){
		RestConnector.post(url,null, handler);
	}
	public static void Register(String url,ObjAccount obj,AsyncHttpResponseHandler handler){
		RestConnector.post(url, ParamBuilder.BuildParamsRegister(obj), handler);
	}
	public static void Forget_Password(String url,String email,AsyncHttpResponseHandler handler){
		RestConnector.post(url, ParamBuilder.BuildParamForgetPassword(email), handler);
	}
	public static void Update_Account(String url,ObjAccount obj,AsyncHttpResponseHandler handler){
		RestConnector.post(url, ParamBuilder.BuildParamsUpdate(obj), handler);
	}
	public static void showSuccessToast(Context c,String message){
		Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void showFailToast(Context c,String message){
		Toast.makeText(c,message , Toast.LENGTH_SHORT).show();
	}
}
