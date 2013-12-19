package com.eas.elearning.db;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;

import android.content.Context;



public class MyPreference {
	
	private static MyPreference instance;
	private Context mContext;
	private SharedPreferences mMyPreferences;
	
	public String customVar;

	public static MyPreference getInstance() {
		if (instance == null) {
			// Create the instance
			instance = new MyPreference();
		}
		// Return the instance
		return instance;
	}
	
	private MyPreference() {
		// Constructor hidden because this is a singleton
	}
	public void Initialize(Context ctxt){
	       mContext = ctxt;
	       mMyPreferences=PreferenceManager.getDefaultSharedPreferences(mContext);
	   }
	public void writeString(String key, String value){
	     Editor e = mMyPreferences.edit();
	     e.putString(key, value);
	     e.commit();
	}
	public String getString(String key){
		String str = mMyPreferences.getString(key, "");
		return str;
	}
	public void CheckValid(String key,boolean value){
		Editor e=mMyPreferences.edit();
		e.putBoolean(key, value);
		e.commit();
	}
	public boolean getValid(String key){
		boolean check = mMyPreferences.getBoolean(key,true);
		return check;
	}
}
