package com.eas.elearning.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.widget.Toast;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.eas.elearning.R;
import com.eas.elearning.adapter.DocumentAdapter;
import com.eas.elearning.bean.Document;
import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.business.AccountTask;
import com.eas.elearning.business.DocumentTask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.network.ResponseTranslater;
import com.eas.elearning.util.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class LoginAccountPopup extends Activity implements OnClickListener {
	private String TAG = "LoginAccountPopup";
	EditText eUsername, ePassword;
	TextView tvForgot, tvRegister;
	String name, password, nameSchool;
	DBAccount mdb;
	boolean isCheck = false, isCancel = false;
	Util utils;
	ObjAccount obj;
	private ArrayList<Document> onlineDocs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.popup_login);
		eUsername = (EditText) findViewById(R.id.eUsername);
		ePassword = (EditText) findViewById(R.id.ePassword);
		tvForgot = (TextView) findViewById(R.id.tvForgot);
		tvRegister = (TextView) findViewById(R.id.tvRegister);
		isCheck = getIntent().getExtras().getBoolean("CHECK");
		mdb = new DBAccount(this);
		utils = new Util(this);
		eUsername.setCompoundDrawablesWithIntrinsicBounds(null, null,
				getResources().getDrawable(R.drawable.icon_user), null);
		ePassword.setCompoundDrawablesWithIntrinsicBounds(null, null,
				getResources().getDrawable(R.drawable.icon_password), null);
		if (isCheck) {
			name = getIntent().getExtras().getString("NAME");
			password = getIntent().getExtras().getString("PASS");
			nameSchool = getIntent().getExtras().getString("NAME_SCHOOL");

			eUsername.setText(name);
			ePassword.setText(password);
		}
		if (NetworkUtility.checkNetworkState(this)) {
			tvForgot.setOnClickListener(this);
			tvRegister.setOnClickListener(this);
		} else {
			Toast.makeText(getBaseContext(), "Không kết nối mạng",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void onClickLogin(View v) {
		name = eUsername.getText().toString();
		password = ePassword.getText().toString();
		if (NetworkUtility.checkNetworkState(this)) {

			if (!(name.equals("") || password.equals(""))) {
				NetworkUtility.showProgressDialog(this, "",
						"Đang đăng nhập ...", true, new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								isCancel = true;
							}
						});
				AccountTask.Login(NetworkUtility.LOGIN, name, password,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, String response) {
								super.onSuccess(arg0, response);
								if (response == null || isCancel == true) {
									isCancel = false;
									return;
								}
								Log.e(TAG, "response: "+ response);
								 obj = new ObjAccount();
								obj = ResponseTranslater
										.getInfoAccount(response);
								if (obj != null) {
									obj.setNameSchool(nameSchool);
									if (!(mdb.CheckIDAccount(obj.getId()))) {
										mdb.AddAccount(obj);
									}
									MyPreference.getInstance().Initialize(
											getApplicationContext());
									utils.setLogin("" + obj.getId(),
											obj.getUserName());
									getData();
									

								} else {
									NetworkUtility.dismissProgressDialog();
									eUsername.setText("");
									ePassword.setText("");
									AccountTask.showFailToast(getBaseContext(),"Thông tin không đúng");
										
								}
							}

							@Override
							@Deprecated
							public void onFailure(Throwable error,
									String content) {
								// TODO Auto-generated method stub
								super.onFailure(error, content);
								NetworkUtility.dismissProgressDialog();
								if (content == null || isCancel == true) {
									isCancel = false;
									return;
								}
								AccountTask.showFailToast(getBaseContext(),
										"Đăng nhập không thành công");
							}
						});
			} else {
				AccountTask.showFailToast(getBaseContext(), "Không đủ thông tin");
				
			}
		} else {
			NetworkUtility.showNoInternetToast(getBaseContext());
			
		}

	}

	public void onClickCancel(View v) {
		finish();
	}

	public void onClickRegister(View v) {
		Intent intent = new Intent(this, RegisterAccountActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvForgot:
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.popup_forgot_pass);
			dialog.setTitle("Quên Mật Khẩu !");
			final EditText eForgot = (EditText) dialog
					.findViewById(R.id.eForgot);
			Button btnSend = (Button) dialog.findViewById(R.id.btnSend);
			btnSend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (eForgot.getText().toString().equals("")) {
						Toast.makeText(getBaseContext(), "Hãy nhập email !",
								Toast.LENGTH_SHORT).show();
					} else {
						String email = eForgot.getText().toString();
						if (CheckValidEmail(email)) {
							AccountTask.Forget_Password(
									NetworkUtility.FORGOT_PASSWORD, email,
									new AsyncHttpResponseHandler() {
										@Override
										public void onSuccess(int arg0,
												String response) {
											super.onSuccess(arg0, response);
											Log.e(TAG, "response: " + response);
											if (response == null) {
												return;
											}
											if (ResponseTranslater
													.checkSuccess1(response)) {
												Toast.makeText(
														getBaseContext(),
														"Gửi email thành công",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												eForgot.setText("");
												Toast.makeText(
														getBaseContext(),
														"Email không đúng,hãy nhập lại",
														Toast.LENGTH_SHORT)
														.show();
											}
										}

										@Override
										@Deprecated
										public void onFailure(Throwable error,
												String content) {
											// TODO Auto-generated method stub
											super.onFailure(error, content);
											Toast.makeText(
													getBaseContext(),
													"không kết nối được với server",
													Toast.LENGTH_SHORT).show();
										}
									});
							finish();
						} else {
							eForgot.setText("");
							Toast.makeText(getBaseContext(), "Email not valid",
									Toast.LENGTH_SHORT).show();
						}

					}

				}
			});
			dialog.show();
			break;

		default:
			Intent intent = new Intent(LoginAccountPopup.this,
					RegisterAccountActivity.class);
			startActivityForResult(intent, 1);
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				String name = data.getExtras().getString("NAME");
				String pass = data.getExtras().getString("PASSWORD");
				nameSchool = data.getExtras().getString("NAME_SCHOOL");
				eUsername.setText(name);
				ePassword.setText(pass);
			}
		}
	}

	public boolean CheckValidEmail(String data) {
		String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(regexEmail);
		Matcher matcher = pattern.matcher(data);
		if (matcher.find())
			return true;
		else
			return false;
	}
	private void getData() {
		if (NetworkUtility.checkNetworkState(getBaseContext())) {
				DocumentTask.getFarvoriteDoc(obj.getId(),
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int statusCode, String content) {

								if (content == null
										|| getBaseContext() == null) {
									return;
								}
								onlineDocs=Parser.getFarvDoc(content);
								if(onlineDocs.size()>0){
									mdb.deleteDocumentFavorite(obj.getId());
									for(int i=0;i<onlineDocs.size();i++){
										onlineDocs.get(i).setAccountID(obj.getId());
										mdb.AddDocumentFavorite(onlineDocs.get(i));
									}
									
								}
								finish();

							}

							@Override
							public void onFailure(Throwable error,
									String content) {
								NetworkUtility.showConnectionErrorToast(getBaseContext());
							}

						});
		} else {
			NetworkUtility.showNoInternetToast(getBaseContext());
		}

	}

}
