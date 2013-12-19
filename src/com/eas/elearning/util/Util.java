package com.eas.elearning.util;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;

import com.eas.elearning.db.MyPreference;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class Util {
	private static  Context mContext;
	public Util(Context ctx){
		this.mContext=ctx;
	}
	public static void hideKeyBoard(Activity c){
		InputMethodManager inputMethodManager = (InputMethodManager)  c.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(c.getCurrentFocus().getWindowToken(), 0);
	}
	/*private void SavePreferences(String key, String value) {
		//context.getSharedPreferences("CASPreferences", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }*/
	public  void setLogin(String id,String name) {
		MyPreference.getInstance().Initialize(mContext);
		MyPreference.getInstance().writeString("SETTING_ID",id);
		MyPreference.getInstance().writeString("SETTING_NAME",name);
	}
	public  void setGender(String gender) {
		MyPreference.getInstance().Initialize(mContext);
		MyPreference.getInstance().writeString("SETTING_GENDER",gender);
	}
	public  void setFirstTime(String first) {
		MyPreference.getInstance().Initialize(mContext);
		MyPreference.getInstance().writeString("FIRST_TIME",first);
	}
	public void setVersion(String ver_area,String ver_school,String ver_class,String ver_subject){
		MyPreference.getInstance().Initialize(mContext);
		MyPreference.getInstance().writeString("VER_AREA",""+ver_area);
		MyPreference.getInstance().writeString("VER_SCHOOL",""+ver_school);
		MyPreference.getInstance().writeString("VER_CLASS",""+ver_class);
		MyPreference.getInstance().writeString("VER_SUBJECT",""+ver_subject);
		
	}
	public void setIDClass(String id){
		MyPreference.getInstance().Initialize(mContext);
		MyPreference.getInstance().writeString("ID_CLASS",""+id);
	}
	public void setCheckAll(boolean key){
		MyPreference.getInstance().Initialize(mContext);
		MyPreference.getInstance().CheckValid("CHECK_ALL",key);
	}
}
