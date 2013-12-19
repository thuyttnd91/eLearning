package com.eas.elearning.app;

import java.util.ArrayList;
import java.util.Collections;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.DatePicker;
import org.holoeverywhere.widget.DatePicker.OnDateChangedListener;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eas.elearning.R;
import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.bean.ObjArea;
import com.eas.elearning.bean.ObjSchool;
import com.eas.elearning.business.AccountTask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ResponseTranslater;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class UpdateInfoActivity extends Activity  {
	private String TAG = "UpdateInfoActivity";
	public ObjAccount obj=new ObjAccount();
	TextView tvTitle;
	EditText eFullName, eDay, eMonth, eYear;
	String fullname, birthday, nameSchool,nameClass;
	int gender, index, day, month, year1,year2, index2,index3, idArea;
	DBAccount mdb;
	ArrayList<ObjSchool> arrSchool;
	ArrayList<Integer> listIDSchool = new ArrayList<Integer>();
	ArrayList<String> listNameSchool = new ArrayList<String>();
	LinearLayout layoutInfo;
	Spinner spSchool, spClass,spArea;
	Switch swGender;
	int id;
	boolean isCancel=false;
	ArrayList<ObjArea> arrArea;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
		spArea.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e(TAG, "position: "+position);
					listIDSchool.clear();
					listNameSchool.clear();
					idArea =arrArea.get(position).getIdArea();
					getSchoolFromDb(idArea);
					
					if (position!=index3) {
						spSchool.setSelection(0, true);
						index3=-1;
					}
					
				
				// TODO Auto-generated method stub
				
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
					nameSchool = listNameSchool.get(position);
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void init() {
		layoutInfo = (LinearLayout) findViewById(R.id.layoutInfo);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		eFullName = (EditText) findViewById(R.id.eFullname);
		eDay = (EditText) findViewById(R.id.eDay);
		eMonth = (EditText) findViewById(R.id.eMonth);
		eYear = (EditText) findViewById(R.id.eYear);
		spSchool = (Spinner) findViewById(R.id.spSchool);
		spClass = (Spinner) findViewById(R.id.spClass);
		swGender=(Switch) findViewById(R.id.swGender);
		spArea=(Spinner) findViewById(R.id.spArea);
		id = getIntent().getExtras().getInt("ID");
		mdb = new DBAccount(this);
		obj = mdb.getAccount(id);
		layoutInfo.setVisibility(View.GONE);
		if (obj != null) {
			
			getAreaFromDB();
			
			getClassFromDB();
			tvTitle.setText(getResources().getString(R.string.update_account));
			eFullName.setText(obj.getUserName());
			eFullName.setText(obj.getFullName());
			String strBirthday = obj.getBirthday();
			if (strBirthday.length() > 0) {

				String[] arrStr = strBirthday.split("-");
				int size = arrStr.length;
				eDay.setText("" + arrStr[size - 1]);
				eMonth.setText("" + arrStr[size - 2]);
				eYear.setText("" + arrStr[size - 3]);

			}
			Log.e(TAG, "id: " + obj.getGender());
			if(obj.getGender()==1){
				swGender.setSelected(true);
				swGender.setChecked(true);
			}
			else{
				swGender.setSelected(false);
				swGender.setChecked(false);
			}
			
			

		}
		
		
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
	public void getAreaFromDB() {
		 arrArea = mdb.getArea();
		ArrayList<Integer> listArea = new ArrayList<Integer>();
		ArrayList<String> listNameArea = new ArrayList<String>();
		for (int i = 0; i < arrArea.size(); i++) {
			listArea.add(arrArea.get(i).getIdArea());
			listNameArea.add(arrArea.get(i).getNameArea());
		}
		ArrayAdapter<String> adapterArea = new ArrayAdapter<String>(
				getBaseContext(), R.layout.row_text_school, listNameArea);
		adapterArea
				.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spArea.setAdapter(adapterArea);
		 idArea=mdb.getAreabySchool(obj.getIdSchool());
		String name_area=mdb.getNameArea(idArea);
		index3 = listNameArea.indexOf(name_area);
		Log.e(TAG, "index area: "+index3);
		if (index3 != -1)
			spArea.setSelection(index3);
		getSchoolFromDb(idArea);
		
	}
	public void getSchoolFromDb(int id_area){
		arrSchool = mdb.getIDSchool(id_area);
		
		// listClass=getResources().getStringArray(R.string.);
		if (arrSchool.size() > 0) {
			for (int i = 0; i < arrSchool.size(); i++) {
				listIDSchool.add(arrSchool.get(i).getIdSchool());
				listNameSchool.add(arrSchool.get(i).getNameSchool());
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				UpdateInfoActivity.this, R.layout.row_text_school,
				listNameSchool);
		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spSchool.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
		nameSchool = mdb.getNameSchoolbyArea(obj.getIdSchool(),idArea);
		int  indexSchool = listNameSchool.indexOf(nameSchool);
		Log.e(TAG, "name school: "+nameSchool);
		Log.e(TAG, "index: "+indexSchool);
		// int index2=testArray.
		if (indexSchool != -1){
			spSchool.setSelection(indexSchool);
		}
			
		
	}
	public void getClassFromDB(){
		ArrayList<String> arrClass=mdb.getIDClass();
		Collections.sort(arrClass);
		ArrayAdapter<String> adapterClass=new ArrayAdapter<String>(getBaseContext(), R.layout.row_text_school, arrClass);
		adapterClass.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spClass.setAdapter(adapterClass);
		int id_class = obj.getIdClass();
		String name_class=mdb.getNameClassByID(id_class);
		Log.e(TAG, "id class: "+id_class);
		Log.e(TAG, "name class: "+name_class);
		index2 = arrClass.indexOf(name_class);
		if (index2 != -1)
			spClass.setSelection(index2);
		
	}
	public void onClickAccept(View v) {
		if(NetworkUtility.checkNetworkState(this)){
		fullname = eFullName.getText().toString();
		birthday = eYear.getText().toString() + "-"
				+ eMonth.getText().toString() + "-" + eDay.getText().toString();
		Log.e(TAG, "birthday: " + birthday);
		obj.setFullName(fullname);
		obj.setBirthday(birthday);
		obj.setGender(gender);
		nameClass=spClass.getSelectedItem().toString();
		index2=mdb.getIdClassSetting(nameClass);
		obj.setIdClass(index2);
		obj.setIdSchool(index);
		obj.setNameSchool(nameSchool);
			NetworkUtility.showProgressDialog(this, "",
					"Đang cập nhật thông tin...", true, new OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
							isCancel = true;
						}
					});
		AccountTask.Update_Account(NetworkUtility.UPDATE_INFO, obj,
				new AsyncHttpResponseHandler() {
					@Override
					@Deprecated
					public void onSuccess(int statusCode, String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, content);
						if (content == null || isCancel==true) {
							isCancel=false;
							return;
						}
						if (ResponseTranslater.checkSuccess(content)) {

							mdb.UpdateAccount(obj);
							AccountTask.showSuccessToast(getBaseContext(), "Cập nhật thông tin thành công");
							finish();
						}
						else{
							NetworkUtility.dismissProgressDialog();
							AccountTask.showFailToast(getBaseContext(), "Cập nhật thông tin không thành công");
						}
					}
					
					
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						NetworkUtility.dismissProgressDialog();
						if (content==null||isCancel==true){
							isCancel = false;
							return;
						}
						AccountTask.showFailToast(getBaseContext(),"Cập nhật thông tin không thành công");
					}
					
				
				});
		}
		else{
			NetworkUtility.showConnectionErrorToast(getBaseContext());
		}
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
		month = dpBirthday.getMonth()+1;
		year1 = dpBirthday.getYear();
		year2=year1;
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
				if( year2 <2000 || year2>year1){
					Toast.makeText(getBaseContext(), "Năm sinh không đúng !", Toast.LENGTH_SHORT).show();
				}
				else{
						year1=year2;
					// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}

	

	

	
	public String checkDigit(String number) {
		return (Integer.parseInt(number)) <= 9 ? "0" + number : number;
	}
	public void onClickBack(View v){
		setResult(1);
		finish();
	}
}
