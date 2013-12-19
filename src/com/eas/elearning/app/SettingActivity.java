package com.eas.elearning.app;

import java.util.ArrayList;
import java.util.Collections;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.CheckBox;
import org.holoeverywhere.widget.CheckedTextView;
import org.holoeverywhere.widget.Spinner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eas.elearning.R;
import com.eas.elearning.adapter.SettingAdapter;
import com.eas.elearning.bean.ObjSubject;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;

public class SettingActivity extends Activity implements OnItemClickListener, OnClickListener, OnItemSelectedListener {
	ListView lvSubject;
	private String TAG = "SettingActivity";
	Spinner spClass;
	CheckBox cbSubject;
	int idClass;
	DBAccount mdb;
	ArrayList<Integer> listNumber = new ArrayList<Integer>();
	ArrayList<String> arrClass=new ArrayList<String>();
	ArrayList<ObjSubject> arrSubject=new ArrayList<ObjSubject>();
	String strClass="";
	SettingAdapter adapter;
	boolean isCheckAll=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		lvSubject = (ListView) findViewById(R.id.lvSubject);
		cbSubject=(CheckBox) findViewById(R.id.cbSubject);
		spClass = (Spinner) findViewById(R.id.spClass);
		
		mdb=new DBAccount(this);
		 arrClass=mdb.getIDClass();
		 Collections.sort(arrClass);
		ArrayAdapter<String> adapterClass = new ArrayAdapter<String>(
				SettingActivity.this,R.layout.row_text_school,
				arrClass);
		adapterClass.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spClass.setAdapter(adapterClass);
		strClass=MyPreference.getInstance().getString("ID_CLASS");
		if(strClass.equals("")){
			strClass=spClass.getSelectedItem().toString();
			MyPreference.getInstance().writeString("ID_CLASS", strClass);
		}
		isCheckAll=MyPreference.getInstance().getValid("CHECK_ALL");
		
		
		if(!(strClass.equals(""))){
			Log.e(TAG, "name class: "+strClass);
			int index = arrClass.indexOf(strClass);
			if (index != -1)
				spClass.setSelection(index);
		}
		
		arrSubject=mdb.getSubject();

		 adapter = new SettingAdapter(this, R.layout.row_subject,
				arrSubject);
		 Log.e(TAG, "check valid: "+isCheckAll);
		 if(isCheckAll){
			 adapter.check=true;
				adapter.notifyDataSetChanged();
				cbSubject.setChecked(true);
			}
		 else{
			 adapter.check=false;
				adapter.notifyDataSetChanged();
				cbSubject.setChecked(false);
		 }

		lvSubject.setAdapter(adapter);
		lvSubject.setOnItemClickListener(this);
		cbSubject.setOnClickListener(this);
		spClass.setOnItemSelectedListener(this);
		
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		adapter.check = false;
		Log.e(TAG, "position: " + position);
		CheckedTextView tvSubject = (CheckedTextView) v
				.findViewById(R.id.tvSubject);
		
		if (!(tvSubject.isChecked())) {
			tvSubject.setChecked(true);
			Log.e(TAG, "1" + position);
			if(!mdb.CheckIDAccount(arrSubject.get(position).getIdSubject())){
				mdb.addSetting(arrSubject.get(position).getIdSubject());
			}
			
			
		} else {
			tvSubject.setChecked(false);
			mdb.deleteIDSubject(arrSubject.get(position).getIdSubject());
			Log.e(TAG, "2" + position);
		}
		MyPreference.getInstance().CheckValid("CHECK_ALL", false);
		save(position, tvSubject.isChecked());

	}
	

	private void save(int index, boolean isChecked) {
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putBoolean("check" + index, isChecked);
	    editor.commit();
	}

	

	@Override
	public void onClick(View v) {
		strClass=MyPreference.getInstance().getString("ID_CLASS");
		if(isCheckAll){
			isCheckAll=false;
			adapter.check=false;
			adapter.notifyDataSetChanged();
			MyPreference.getInstance().CheckValid("CHECK_ALL", false);
			mdb.deleteAllSetting();
			for(int i=0;i<arrSubject.size();i++){
				save(i, false);
			}
		}
		else{
			isCheckAll=true;
			adapter.check=true;
			adapter.notifyDataSetChanged();
			MyPreference.getInstance().CheckValid("CHECK_ALL", true);
			mdb.deleteAllSetting();
			for(int i=0;i<arrSubject.size();i++){
				save(i, true);
				mdb.addSetting(arrSubject.get(i).getIdSubject());
			}
			
			
		}
		
		
	}
	
	public void onClickBack(View v){
		Intent returnIntent = new Intent();
		 setResult(RESULT_OK, returnIntent);  
		finish();
	}
	
public void onClickSave(View v){
	ArrayList<Integer> list=mdb.getIDSubjectSetting();
	strClass=MyPreference.getInstance().getString("ID_CLASS");
	Log.e(TAG, "count1: "+list);
	Log.e(TAG, "count2: "+strClass);
	int i=mdb.getIdClassSetting(strClass);
	Log.e(TAG, "count3: "+i);
	
	
	
}

@Override
public void onItemSelected(org.holoeverywhere.widget.AdapterView<?> parent,
		View view, int position, long id) {
	strClass=spClass.getSelectedItem().toString();
	MyPreference.getInstance().writeString("ID_CLASS", strClass);
	
}

@Override
public void onNothingSelected(org.holoeverywhere.widget.AdapterView<?> parent) {
	
}

@Override
@SuppressLint("NewApi")
public void onBackPressed() {
	Intent returnIntent = new Intent();
	 setResult(RESULT_OK, returnIntent);     
	 finish();
	super.onBackPressed();
}
}
