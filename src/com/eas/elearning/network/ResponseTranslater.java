package com.eas.elearning.network;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.bean.ObjArea;
import com.eas.elearning.bean.ObjClass;
import com.eas.elearning.bean.ObjSchool;
import com.eas.elearning.bean.ObjSubject;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.util.Util;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

public class ResponseTranslater {
	private static String TAG="ResponseTranslater";
	int accountID;
	String username;
	String fullname;
	String gender;
	String birthday;
	String Image;
	String nameclass;
	int schoolID;
	int accountType;
	static DBAccount mdb;
	static Util utils;
	public static ObjAccount getInfoAccount(String jsonAccount) {
		ObjAccount objAccount = null;
		try {
			JSONObject jsonObjectAccount = new JSONObject(jsonAccount);
			if (jsonObjectAccount.getString(NetworkUtility.RESULT_CODE).equals("OK")){
				
				JSONObject json = jsonObjectAccount
						.getJSONObject(NetworkUtility.USER_INFO);
				if(json!=null){
					objAccount=new ObjAccount();
					objAccount.setId(json.getInt("ID"));
					objAccount.setUserName(json.getString("UserName"));
					objAccount.setEmail(json.getString("Email"));
					objAccount.setFullName(json.getString("FullName"));
					objAccount.setGender(json.getInt("Gender"));
					objAccount.setBirthday(json.getString("BirthDay"));
					objAccount.setIdClass(json.getInt("Class"));
					objAccount.setType(json.getInt("AccountType"));
					objAccount.setIdSchool(json.getInt("SchoolID"));
					objAccount.setImgAvatar(json.getString("Image"));
				}
				
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return objAccount;
	}
	public static boolean checkSuccess(String jsonResponse) {
		JSONObject jsonObject;
		boolean isSuccess = false;
		try {
			jsonObject = new JSONObject(jsonResponse);
			if (jsonObject.getString(NetworkUtility.RESULT_CODE).equals("OK"))
				isSuccess = true;
			else{
				isSuccess = false;
			}
		} catch (JSONException e) {
		}
		return isSuccess;
	}
	public static boolean checkSuccess1(String jsonResponse) {
		JSONObject jsonObject;
		boolean isSuccess = true;
		try {
			jsonObject = new JSONObject(jsonResponse);
			if (jsonObject.getString(NetworkUtility.RESULT_CODE).equals("ERROR"))
				isSuccess = false;
			else{
				isSuccess = true;
			}
		} catch (JSONException e) {
		}
		return isSuccess;
	}

	public static ArrayList<ObjSchool> getSchool(String responseSchool){
		ArrayList<ObjSchool> arrSchool=new ArrayList<ObjSchool>();
		try {
			JSONObject jsonSchool=new JSONObject(responseSchool);
			if(jsonSchool.getString(NetworkUtility.RESULT_CODE).equals("OK")){
				JSONArray jsonArray=jsonSchool.getJSONObject("schools").getJSONArray("ListSchool");
				if(jsonArray.length()>0){
					for(int i=0;i<jsonArray.length();i++){
						JSONObject j=jsonArray.getJSONObject(i);
						ObjSchool obj=new ObjSchool();
						obj.setIdSchool(j.getInt("ID"));
						obj.setNameSchool(j.getString("SchoolName"));
						obj.setIdArea(j.getInt("AreaID"));
						obj.setDescription(j.getString("Description"));
						arrSchool.add(obj);
					}
				}
			}
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return arrSchool;
		
	}
	public static void SaveInfomation(String response,Activity ac){
		mdb=new DBAccount(ac);
		utils=new Util(ac);
		try {
			JSONObject jsonMain=new JSONObject(response);
			//version
			String ver_area=jsonMain.getString("area_ver");
			String ver_school=jsonMain.getString("school_ver");
			String ver_class=jsonMain.getString("class_ver");
			String ver_subject=jsonMain.getString("subject_ver");
			utils.setVersion(ver_area, ver_school, ver_class, ver_subject);
			
			//area
			JSONObject jsonArea=jsonMain.getJSONObject("areas");
			JSONArray arrayArea=jsonArea.getJSONArray("_Areas");
			if(arrayArea.length()>0){
				mdb.deleteAllArea();
				for(int i=0;i<arrayArea.length();i++){
					JSONObject j=arrayArea.getJSONObject(i);
					ObjArea obj=new ObjArea();
					obj.setIdArea(j.getInt("ID"));
					obj.setNameArea(j.getString("AreaName"));
					mdb.addArea(obj);
				}
			}
			//school
			JSONObject jsonSchool=jsonMain.getJSONObject("schools");
			JSONArray arraySchool=jsonSchool.getJSONArray("_School");
			if(arraySchool.length()>0){
				mdb.deleteAllSchool();
				for(int i=0;i<arraySchool.length();i++){
					JSONObject k=arraySchool.getJSONObject(i);
					ObjSchool objSchool=new ObjSchool();
					objSchool.setIdSchool(k.getInt("ID"));
					objSchool.setNameSchool(k.getString("SchoolName"));
					objSchool.setIdArea(k.getInt("AreaID"));
					mdb.AddSchool(objSchool);
				}
			}
			//class
			JSONObject jsonClass=jsonMain.getJSONObject("classes");
			JSONArray arrayClass=jsonClass.getJSONArray("_Class");
			if(arrayClass.length()>0){
				mdb.deleteAllClass();
				for(int i=0;i<arrayClass.length();i++){
					JSONObject h=arrayClass.getJSONObject(i);
					ObjClass objClass=new ObjClass();
					objClass.setIdClass(h.getInt("ID"));
					objClass.setNameClass(h.getString("Class"));
					mdb.addClass(objClass);
				}
			}
			//subject
			JSONObject jsonSubject=jsonMain.getJSONObject("subjects");
			JSONArray arrSubject=jsonSubject.getJSONArray("_Subject");
			if(arrSubject.length()>0){
				mdb.deleteAllSubject();
				for(int i=0;i<arrSubject.length();i++){
					JSONObject y=arrSubject.getJSONObject(i);
					ObjSubject objSubject=new ObjSubject();
					objSubject.setIdSubject(y.getInt("ID"));
					objSubject.setNameSubject(y.getString("SubjectName"));
					mdb.addSubject(objSubject);
				}
			}
			ArrayList<ObjSubject> list=mdb.getSubject();
			Log.e(TAG, ""+ list.get(0).getNameSubject());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
