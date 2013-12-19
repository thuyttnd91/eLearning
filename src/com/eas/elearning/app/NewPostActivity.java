package com.eas.elearning.app;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import com.eas.elearning.R;
import com.eas.elearning.business.QATask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.util.HTMLUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NewPostActivity extends Activity {
	
	private EditText edtContent;
	private TextView tvUserName;
	private ImageView imgViewAvatar;
	private DBAccount db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setResult(RESULT_CANCELED);
		
		db = new DBAccount(this);
		int id = Integer.parseInt(MyPreference.getInstance().getString("SETTING_ID"));
		
		setContentView(R.layout.activity_new_post);
		
		edtContent = (EditText) findViewById(R.id.edtContent);
		imgViewAvatar = (ImageView) findViewById(R.id.imgViewAvatar);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		
		
		
		tvUserName.setText(db.getAccount(id).getFullName());
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(db.getAccount(id).getImgAvatar()), imgViewAvatar);
		
	}
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.tvExit:
			finish();
			break;
		case R.id.btnSendQues:
			sendQues();
			break;

		default:
			break;
		}
	}
	
	private void sendQues(){
		
		int id = Integer.parseInt(MyPreference.getInstance().getString("SETTING_ID"));
		String ques = edtContent.getText().toString();
		
		if (ques.length() < 1) {
			Toast.makeText(this, "Bạn chưa nhập câu hỏi", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (NetworkUtility.checkNetworkState(this)){
			NetworkUtility.showProgressDialog(this, null, "Đang gửi câu hỏi", true, new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
				}
			});
			QATask.Post(id, ques, new AsyncHttpResponseHandler(){
			
				@Override
				public void onSuccess(int statusCode, String content) {
					
					NetworkUtility.dismissProgressDialog();
					
					if (content == null){
						Toast.makeText(getBaseContext(), "Không thể gửi câu hỏi", Toast.LENGTH_SHORT).show();
						return;
					}
					
					if (Parser.checkSuccess(content)){
						Toast.makeText(getBaseContext(), "Đã gửi thành công câu hỏi", Toast.LENGTH_SHORT).show();
						setResult(RESULT_OK);
						finish();
						
					} else {
						Toast.makeText(getBaseContext(), "Không thể gửi câu hỏi", Toast.LENGTH_SHORT).show();
						return;
					}
					
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					NetworkUtility.dismissProgressDialog();
					Toast.makeText(getBaseContext(), "Không thể gửi câu hỏi", Toast.LENGTH_SHORT).show();
					return;
				}
				
			});
		} else {
			NetworkUtility.showNoInternetToast(this);
			return;
		}
		
	}

}
