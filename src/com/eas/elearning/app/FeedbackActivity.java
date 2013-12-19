package com.eas.elearning.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.EditText;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eas.elearning.R;
import com.eas.elearning.business.FeedbackTask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FeedbackActivity extends Activity {
	
	private EditText edtFeedback;
	private EditText edtEmail;
	private boolean sendCanceled;
	private DBAccount mDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDb = new DBAccount(this);
		sendCanceled = false;
		setContentView(R.layout.feedback_activity);
		edtFeedback = (EditText) findViewById(R.id.edtFeedback);
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		
		String strID = MyPreference.getInstance().getString("SETTING_ID");
		if (strID!="") {
			edtEmail.setText(mDb.getAccount(Integer.parseInt(strID)).getEmail());
			edtEmail.setFocusable(false);
		}
		
	}
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.tvExit:
			finish();
			break;
		case R.id.btn_Send_Feedback:
			if(edtFeedback.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "Bạn chưa nhập ý kiến phản hồi \n Mời bạn nhập lại", Toast.LENGTH_LONG).show();
				return;
			}
			if (!checkEmail()){
				Toast.makeText(getApplicationContext(), "Bạn chưa nhập email hoặc email không hợp lệ", Toast.LENGTH_LONG).show();
				return;
			}
				sendFeedback();
			
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private boolean checkEmail(){
		String email = edtEmail.getText().toString();
		String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(regexEmail);
		Matcher matcher = pattern.matcher(email);
		if (matcher.find())
			return true;
		else
			return false;
	}
	
	private void sendFeedback(){
		String msg = edtFeedback.getText().toString();
		String email = edtEmail.getText().toString();
		if (NetworkUtility.checkNetworkState(getBaseContext())){
			NetworkUtility.showProgressDialog(this, "", "Đang gửi phản hồi ...", true, new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					sendCanceled = true;
				}
			});
			FeedbackTask.sendFeedback(getBaseContext(), msg, email, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(String content) {
					NetworkUtility.dismissProgressDialog();
					if (content==null||sendCanceled==true){
						sendCanceled = false;
						return;
					}
					
					if(Parser.checkSuccess(content)){
						FeedbackTask.showSuccessToast(getBaseContext());
						finish();
					} else FeedbackTask.showFailToast(getBaseContext());
					
				}
				
				@Override
				public void onFailure(int statusCode, Throwable error,
						String content) {
					NetworkUtility.dismissProgressDialog();
					if (content==null||sendCanceled==true){
						sendCanceled = false;
						return;
					}
					FeedbackTask.showFailToast(getBaseContext());
				}
				
			});
		} else NetworkUtility.showNoInternetToast(getBaseContext());
	}

}
