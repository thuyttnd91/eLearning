package com.eas.elearning.network;

import java.util.ArrayList;

import com.eas.elearning.bean.ObjAccount;
import com.loopj.android.http.RequestParams;

public class ParamBuilder {
	
	public static RequestParams BuildFeedbackParams(String Email, String msg){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.EMAIL, Email + "");
		params.put(NetworkUtility.MESSAGE, msg);
		return params;
	}
	public static RequestParams BuildParamGetInfomation(String ver_area,String ver_school,String ver_class,String ver_subject){
		RequestParams params=new RequestParams();
		params.put(NetworkUtility.VER_AREA, ver_area);
		params.put(NetworkUtility.VER_SCHOOL, ver_school);
		params.put(NetworkUtility.VER_CLASS, ver_class);
		params.put(NetworkUtility.VER_SUBJECT, ver_subject);
		return params;
		
		
	}
	
	public static RequestParams BuildParamDeleteFavorite(int idAccount,int idDocument){
		RequestParams params=new RequestParams();
		params.put(NetworkUtility.IDACCOUNT,""+idAccount);
		params.put(NetworkUtility.DocumentIDS, ""+idDocument);
		return params;
		
		
	}
	
	
	public static RequestParams BuildParamsRegister(ObjAccount obj){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.USERNAME,obj.getUserName());
		params.put(NetworkUtility.PASSWORD,obj.getPassWord());
		params.put(NetworkUtility.EMAIL,obj.getEmail());
		params.put(NetworkUtility.FULLNAME,obj.getFullName());
		params.put(NetworkUtility.GENDER,""+obj.getGender());
		params.put(NetworkUtility.BIRTHDAY,obj.getBirthday());
		params.put(NetworkUtility.CLASS,""+obj.getIdClass());
		params.put(NetworkUtility.IDSCHOOL,""+obj.getIdSchool());
		return params;
	}
	public static RequestParams BuildParamsUpdate(ObjAccount obj){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.IDACCOUNT,""+obj.getId());
		params.put(NetworkUtility.FULLNAME,obj.getFullName());
		params.put(NetworkUtility.GENDER,""+obj.getGender());
		params.put(NetworkUtility.BIRTHDAY,obj.getBirthday());
		params.put(NetworkUtility.CLASS,""+obj.getIdClass());
		params.put(NetworkUtility.IDSCHOOL,""+obj.getIdSchool());
		return params;
	}
	public static RequestParams BuildParamLogin(String username,String password){
		RequestParams param = new RequestParams();
		//param.put("tag","LOGIN");
		param.put(NetworkUtility.USERNAME, username);
		param.put(NetworkUtility.PASSWORD, password);
		return param;
	}
	public static RequestParams BuildParamForgetPassword(String email){
		RequestParams param = new RequestParams();
		param.put(NetworkUtility.EMAIL, email);
		return param;
	}
	
	public static RequestParams BuildCommentParams(int AccountID, int DocumentID, String msg){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.ACCOUNT_ID, AccountID + "");
		params.put(NetworkUtility.DOCUMENT_ID, DocumentID + "");
		params.put(NetworkUtility.MESSAGE, msg);
		return params;
	}
	
	public static RequestParams BuildCommentDetailParams(int AccountID, int DocumentDetailID, String msg){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.ACCOUNT_ID, AccountID + "");
		params.put(NetworkUtility.DOCUMENT_DETAIL_ID, DocumentDetailID + "");
		params.put(NetworkUtility.MESSAGE, msg);
		return params;
	}
	
	public static RequestParams BuildGetCommentParams(int DocumentID){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.DOCUMENT_ID, DocumentID + "");
		return params;
	}
	
	public static RequestParams BuildGetDetailCommentParams(int DocumentDetailID){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.DOCUMENT_DETAIL_ID, DocumentDetailID + "");
		return params;
	}
	
	public static RequestParams BuildViewCountParams(int DocumentID){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.DOCUMENT_ID, DocumentID + "");
		return params;
	}
	
	public static RequestParams BuildGetDocRelatedParams(int DocumentID){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.DOCUMENT_ID, DocumentID + "");
		return params;
	}
	
	public static RequestParams BuildDownloadCountParams(int DocumentID){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.DOCUMENT_ID, DocumentID + "");
		return params;
	}
	
	public static RequestParams BuildGetDocDetailParams(int ID){
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.ID, ID + "");
		return params;
	}

	public static RequestParams buildNewDocumentParam(int page ) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.PAGE, ""+page);
		params.put(NetworkUtility.ORDER, "new");
		return params;
	}
	
	public static RequestParams buildDocumentAdvanceParam(int page, String Class, ArrayList<Integer> Subject, String order, String Title ) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.PAGE, ""+page);
		if(Class !=null)
			params.put(NetworkUtility.CLASS, Class);
		String subject = "";
		for (int i = 0; i < Subject.size(); i++) {
			
			if(i!=0){
				subject = subject+","+Subject.get(i);
			} else if(i==0) {
				subject = subject+Subject.get(i);
			} 
			
		}
		if(subject.equals("")) subject = null;
		
//		Log.i("subject", subject);
		if(subject!=null)
			params.put(NetworkUtility.SUBJECT_ID, subject);
		params.put(NetworkUtility.ORDER, order);
		if(Title != null)
			params.put("Title", Title);
		
		 
		return params;
	}

	public static RequestParams buildDocumentAdvanceParam(int page, int Class, int Subject, String Title ) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.PAGE, ""+page);
		params.put(NetworkUtility.CLASS, ""+Class);
		params.put(NetworkUtility.SUBJECT_ID, ""+Subject);
		params.put(NetworkUtility.DOCUMENT_TITLE, Title);
		
		 
		return params;
	}

	public static RequestParams buildSearchDocumentParam(String title, int page) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.TITLE, title);
		params.put(NetworkUtility.GRADE, "12");
		params.put(NetworkUtility.SUBJECT, "23");
		params.put(NetworkUtility.PAGE, ""+page);
		return params;
	}
	
	public static RequestParams buildSendFarvDocParam(int AccountID, int DocumentID) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.ACCOUNT_ID, AccountID + "");
		params.put(NetworkUtility.DOCUMENT_ID, DocumentID + "");
		return params;
	}
	
	public static RequestParams buildGetDocForSubjParam(int subjID, int page) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.SUBJECT, subjID + "");
		params.put(NetworkUtility.PAGE, page + "");
		return params;
	}
	
	public static RequestParams buildGetPostParam(int page) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.PAGE, page + "");
		return params;
	}
	
	public static RequestParams buildPostParam(int AccountID, String content) {
		RequestParams params = new RequestParams();
		params.put(NetworkUtility.ACCOUNT_ID, AccountID + "");
		params.put("Question", content);
		return params;
	}
	
	public static RequestParams buildGetAnswerParam(int PostID) {
		RequestParams params = new RequestParams();
		params.put("QuestionID", "" + PostID);
		return params;
	}
	
	public static RequestParams buildSendAnswerParam(int PostID, int AccountID, String content) {
		RequestParams params = new RequestParams();
		params.put("QuestionID", "" + PostID);
		params.put(NetworkUtility.ACCOUNT_ID, AccountID + "");
		params.put("AnswerContent", content);
		return params;
	}
	
}
