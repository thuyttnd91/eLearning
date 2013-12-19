package com.eas.elearning.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.eas.elearning.R;
import com.eas.elearning.bean.ObjSchool;
import com.eas.elearning.bean.ObjSubject;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.Parser;
import com.eas.elearning.network.ResponseTranslater;
import com.eas.elearning.network.RestConnector;
import com.loopj.android.http.AsyncHttpResponseHandler;
public class SplashActivity extends Activity {
	private String TAG="SplashActivity";
	ArrayList<ObjSchool> arrSchool;
	DBAccount mdb;
	String ver_area="0",ver_school="0",ver_class="0",ver_subject="0",firstTime="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		arrSchool=new ArrayList<ObjSchool>();
		mdb=new DBAccount(this);
		MyPreference.getInstance().Initialize(this);
		firstTime = MyPreference.getInstance().getString("FIRST_TIME");
		if(!(firstTime.equals(""))){
			getVersion();	
		}
		
		getkey();
		
		getInfomation();
		
		/*if(mdb.CheckIDSCHOOL()){
			Intent i=new Intent(SplashActivity.this,HomeActivity.class);
			startActivity(i);
			finish();
		}
		else{
			if(NetworkUtility.checkNetworkState(this)){
				getSchool();
			}
		}*/
		
		
	}
	public void getVersion(){
		ver_area = MyPreference.getInstance().getString("VER_AREA");
		ver_school = MyPreference.getInstance().getString("VER_SCHOOL");
		ver_class = MyPreference.getInstance().getString("VER_CLASS");
		ver_subject = MyPreference.getInstance().getString("VER_SUBJECT");
	}
	public void getInfomation(){
		RestConnector.post(NetworkUtility.INFOMATION, ParamBuilder.BuildParamGetInfomation(ver_area, ver_school, ver_class, ver_subject), new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) {
				//Toast.makeText(getBaseContext(), "content: "+content, Toast.LENGTH_SHORT).show();
				if (content==null){
					
					return;
				}
				if(ResponseTranslater.checkSuccess(content)){
					
					ResponseTranslater.SaveInfomation(content,SplashActivity.this);
					MyPreference.getInstance().writeString("FIRST_TIME","second");
					Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
					
					startActivity(intent);
					finish();
				}
				
				
			}
			@Override
			@Deprecated
			public void onFailure(int statusCode, Throwable error,
					String content) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, error, content);
				Log.e(TAG, "Error: "+content);
				ArrayList<ObjSubject> listSubject=mdb.getSubject();
				if(listSubject.size()>0){
					Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
					startActivity(intent);
					finish();
				}
				else{
					Toast.makeText(getBaseContext(), "Không thể tải dữ liệu cần thiết, vui lòng khởi chạy lại ứng dụng", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	 public void getkey(){ 
	 PackageInfo info;
	 try {
	     info = getPackageManager().getPackageInfo("com.eas.elearning", PackageManager.GET_SIGNATURES);
	     for (Signature signature : info.signatures) {
	         MessageDigest md;
	         md = MessageDigest.getInstance("SHA");
	         md.update(signature.toByteArray());
	         String something = new String(Base64.encode(md.digest(), 0));
	         //String something = new String(Base64.encodeBytes(md.digest()));
	         Log.e("hash key", something);
	     }
	 } catch (NameNotFoundException e1) {
	     Log.e("name not found", e1.toString());
	 } catch (NoSuchAlgorithmException e) {
	     Log.e("no such an algorithm", e.toString());
	 } catch (Exception e) {
	     Log.e("exception", e.toString());
	 }
}
}
