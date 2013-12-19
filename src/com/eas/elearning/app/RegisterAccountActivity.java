package com.eas.elearning.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.DatePicker;
import org.holoeverywhere.widget.DatePicker.OnDateChangedListener;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.Toast;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.eas.elearning.R;
import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.bean.ObjArea;
import com.eas.elearning.bean.ObjSchool;
import com.eas.elearning.business.AccountTask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ResponseTranslater;
import com.eas.elearning.util.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class RegisterAccountActivity extends Activity {
	private String TAG = "RegisterAccountActivity";
	public ObjAccount objAccount;
	EditText eDay, eMonth, eYear, eUsername, ePassword, eEmail, eFullname;
	String name, pass, email, fullname, strDay, strMonth, strYear, nameSchool,
			nameClass;
	int day, month, year1 = 0, year2 = 0, idClass = 0;
	Spinner spSchool, spClass, spArea;
	Switch swGender;
	DBAccount mdb;
	int index = 0;
	Util utils;
	int gender = 0;
	boolean isCancel = false;
	ArrayList<Integer> listIDSchool, listArea;
	ArrayList<String> listnameSchool, listNameArea;
	ArrayAdapter<String> adapter;
	// SchoolAdapter adapter;
	String date = "";
	ArrayList<String> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_register);
		init();
		eDay.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				if (hasFocus) {
					if (eDay.getText().toString().length() != 0) {
						int d = Integer.parseInt(eDay.getText().toString());
						Log.e(TAG, "check: " + checkDigit1(d));
						int iDay = checkDigit1(Integer.parseInt(eDay.getText()
								.toString()));
						eDay.setText("" + iDay);
					}

				}

			}
		});

		objAccount = new ObjAccount();
		swGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					gender = 1;
				} else {
					gender = 0;
				}

			}
		});
	}

	public void init() {
		eUsername = (EditText) findViewById(R.id.eUsername);
		ePassword = (EditText) findViewById(R.id.ePass);
		eEmail = (EditText) findViewById(R.id.eEmail);
		eFullname = (EditText) findViewById(R.id.eFullname);
		eDay = (EditText) findViewById(R.id.eDay);
		eMonth = (EditText) findViewById(R.id.eMonth);
		eYear = (EditText) findViewById(R.id.eYear);
		spSchool = (Spinner) findViewById(R.id.spSchool);
		spClass = (Spinner) findViewById(R.id.spClass);
		spArea = (Spinner) findViewById(R.id.spArea);
		swGender = (Switch) findViewById(R.id.swGender);
		mdb = new DBAccount(this);
		listIDSchool = new ArrayList<Integer>();
		listnameSchool = new ArrayList<String>();
		getAreaFromDB();
		getSchoolFromDB();
		getClassFromDB();

		spArea.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				Log.e(TAG, "position ");
				listIDSchool.clear();
				listnameSchool.clear();
				spSchool.setAdapter(null);
				// spSchool.refreshDrawableState();
				String nameArea = spArea.getSelectedItem().toString();
				Log.e(TAG, "name area: " + nameArea);
				int idAreaSelect = mdb.getIdArea(nameArea);
				Log.e(TAG, "id area: " + idAreaSelect);
				ArrayList<ObjSchool> listSchool = mdb.getIDSchool(idAreaSelect);
				Log.e(TAG, "list school: " + listSchool.size());

				for (int i = 0; i < listSchool.size(); i++) {
					listIDSchool.add(listSchool.get(i).getIdSchool());
					listnameSchool.add(listSchool.get(i).getNameSchool());
				}
				Log.e(TAG, "list name School: " + listnameSchool);
				adapter = new ArrayAdapter<String>(
						RegisterAccountActivity.this, R.layout.row_text_school,
						listnameSchool);
				adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
				// adapter.setDropdownContent(listnameSchool);
				// spSchool.setAdapter(adapter);
				// adapter = new SchoolAdapter(getApplicationContext(),
				// listnameSchool);
				spSchool.setAdapter(adapter);
				spSchool.setSelection(0, true);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
		spSchool.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				index = listIDSchool.get(position);
				nameSchool = listnameSchool.get(position);
				Log.e(TAG, "index: " + index);
				Log.e(TAG, "name: " + nameSchool);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		spClass.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		utils = new Util(this);
	}

	public void onClickBirthday(View v) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_datetime);
		DatePicker dpBirthday = (DatePicker) dialog
				.findViewById(R.id.dpBirthday);
		Button btnOK = (Button) dialog.findViewById(R.id.btn_ok);
		Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
		dialog.setTitle("E-learning");
		dialog.show();
		day = dpBirthday.getDayOfMonth();
		month = dpBirthday.getMonth() + 1;
		year1 = dpBirthday.getYear();
		year2 = year1;
		dpBirthday.setOnDateChangedListener(new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int mYear,
					int monthOfYear, int dayOfMonth) {
				day = dayOfMonth;
				month = monthOfYear + 1;
				year2 = mYear;

			}
		});
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (year2 < 2000 || year2 > year1) {
					Toast.makeText(getBaseContext(), "Năm sinh không đúng !",
							Toast.LENGTH_SHORT).show();
				} else {
					year1 = year2;
					eDay.setText(checkDigit("" + day));
					eMonth.setText(checkDigit("" + month));
					eYear.setText("" + year1);
					dialog.dismiss();
				}

			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	public void getClassFromDB() {
		ArrayList<String> arrClass = mdb.getIDClass();
		Collections.sort(arrClass);
		ArrayAdapter<String> adapterClass = new ArrayAdapter<String>(
				getBaseContext(), R.layout.row_text_school, arrClass);
		adapterClass
				.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spClass.setAdapter(adapterClass);

	}

	public void getAreaFromDB() {
		ArrayList<ObjArea> arrArea = mdb.getArea();
		listArea = new ArrayList<Integer>();
		listNameArea = new ArrayList<String>();
		for (int i = 0; i < arrArea.size(); i++) {
			listArea.add(arrArea.get(i).getIdArea());
			listNameArea.add(arrArea.get(i).getNameArea());
		}
		ArrayAdapter<String> adapterArea = new ArrayAdapter<String>(
				getBaseContext(), R.layout.row_text_school, listNameArea);
		adapterArea
				.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spArea.setAdapter(adapterArea);
	}

	public void getSchoolFromDB() {
		// spSchool.setAdapter(null);
		String nameArea = spArea.getSelectedItem().toString();
		Log.e(TAG, "name area: " + nameArea);

		int idAreaSelect = mdb.getIdArea(nameArea);
		Log.e(TAG, "id area: " + idAreaSelect);
		ArrayList<ObjSchool> listSchool = mdb.getIDSchool(idAreaSelect);
		Log.e(TAG, "list school: " + listSchool.size());

		for (int i = 0; i < listSchool.size(); i++) {
			listIDSchool.add(listSchool.get(i).getIdSchool());
			listnameSchool.add(listSchool.get(i).getNameSchool());
		}
		Log.e(TAG, "list name School: " + listnameSchool);
		adapter = new ArrayAdapter<String>(RegisterAccountActivity.this,
				R.layout.row_text_school, listnameSchool);
		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spSchool.setAdapter(adapter);

	}

	public void onClickAccept(View v) {
		if (NetworkUtility.checkNetworkState(this)) {

			name = eUsername.getText().toString();
			pass = ePassword.getText().toString();
			email = eEmail.getText().toString();
			fullname = eFullname.getText().toString();
			strDay = eDay.getText().toString();
			strMonth = eMonth.getText().toString();
			strYear = eYear.getText().toString();
			String strDay = eDay.getText().toString();
			String strMonth = eMonth.getText().toString();
			String strYear = eYear.getText().toString();
			date = strYear + "-" + strMonth + "-" + strDay;
			objAccount.setUserName(name);
			objAccount.setPassWord(pass);
			objAccount.setEmail(email);
			objAccount.setFullName(fullname);
			objAccount.setGender(gender);
			objAccount.setBirthday(date);

			objAccount.setIdSchool(index);
			nameClass = spClass.getSelectedItem().toString();
			Log.e(TAG, "name class: " + nameClass);

			idClass = mdb.getIdClassSetting(nameClass);
			Log.e(TAG, "id class" + idClass);
			objAccount.setIdClass(idClass);
			if (CheckValid(0, name) && CheckValid(0, pass)
					&& CheckValid(0, fullname) && CheckValid(0, strDay)) {
				if (CheckValid(2, name)) {
					if (CheckValid(3, pass)) {
						if (CheckValid(1, email)) {
							RegisterAccount(objAccount);
						} else {
							eEmail.setText("");
							Toast.makeText(getBaseContext(),
									"Email không đúng!", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}

			} else {
				Toast.makeText(getBaseContext(), "Không điền đủ thông tin",
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(getBaseContext(), "Không có kết nối mạng",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void RegisterAccount(ObjAccount obj) {
		NetworkUtility.showProgressDialog(this, "", "Đang đăng ký ...", true,
				new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						isCancel = true;
					}
				});

		AccountTask.Register(NetworkUtility.REGISTER, obj,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onSuccess(int statusCode, String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, content);
						Log.e(TAG, "" + content);
						if (content == null || isCancel == true) {
							isCancel = false;
							return;
						}
						if (ResponseTranslater.checkSuccess(content)) {
							NetworkUtility.dismissProgressDialog();
							Intent i = new Intent();

							RegisterAccountActivity.this
									.setResult(RESULT_OK, i);
							i.putExtra("NAME", name);
							i.putExtra("PASSWORD", pass);
							i.putExtra("NAME_SCHOOL", nameSchool);
							i.putExtra("CHECK", true);
							finish();
						} else {
							NetworkUtility.dismissProgressDialog();
							AccountTask.showFailToast(getBaseContext(),
									"Trùng thông tin");

						}
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						NetworkUtility.dismissProgressDialog();
						NetworkUtility
								.showConnectionErrorToast(getBaseContext());
					}
				});
	}

	public String checkDigit(String number) {
		return (Integer.parseInt(number)) <= 9 ? "0" + number : number;
	}

	public int checkDigit1(int number) {
		return number >= 12 ? 12 : number;
	}

	public void onClickBack(View v) {
		setResult(1);
		finish();
	}

	public boolean CheckValid(int index, String data) {
		switch (index) {
		case 1:
			String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(regexEmail);
			Matcher matcher = pattern.matcher(data);
			if (matcher.find())
				return true;
			else
				return false;
		case 2:
			if (data.length() >= 6) {
				return true;
			} else {
				// eUsername.setText("");
				Toast.makeText(getBaseContext(),
						"Tên đăng nhập phải lớn hơn hoặc bằng 6 ký tự !",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		case 3:
			if (data.length() >= 6) {
				return true;
			} else {
				// eUsername.setText("");
				Toast.makeText(getBaseContext(),
						"Mật khẩu phải lớn hơn hoặc bằng 6 ký tự !",
						Toast.LENGTH_SHORT).show();
				return false;
			}

		default:
			if (data.equals(""))
				return false;
			else
				return true;
		}
	}

}
