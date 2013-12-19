package com.eas.elearning.network;

import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.Toast;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {
	private static ProgressDialog prDialog;

	public static final int DEFAULT_TIME_OUT = 5000;
	public static String LOGIN="LOGIN";
	public static String FORGOT_PASSWORD="FORGOT_PASSWORD";
	public static String REGISTER="REGISTER";
	public static String GET_SCHOOLS="GET_SCHOOLS";
	public static String UPDATE_INFO="UPDATE_INFO";
	public static String RESULT_CODE="result_code";
	public static String USER_INFO="user_info";
	public static String RECEIVE_FAVORITE="RECEIVE_FAVORITE";
	public static String SEND_FAVORITE="SEND_FAVORITE";
	public static String GET_ADVANCE_DOCUMENTS="GET_ADVANCE_DOCUMENTS";
	public static String ADD_VIEW_COUNT ="ADD_VIEW_COUNT";
	public static String ADD_DOWNLOAD_COUNT ="ADD_DOWNLOAD_COUNT";
	public static String GET_DETAIL_COMMENT ="GET_DETAIL_COMMENT";
	public static String COMMENT_DETAIL ="COMMENT_DETAIL";
	public static String GET_RELATED_DOCUMENT ="GET_RELATED_DOCUMENT";
	public static String GET_QA_QUESTIONS ="GET_QA_QUESTIONS";
	public static String GET_ANSWERS ="GET_ANSWERS";
	public static String SEND_ANSWERS ="SEND_ANSWERS";
	public static String POST_QUESTION ="POST_QUESTION";
	
	
	//infomation
	public static String INFOMATION="GET_INFORMATION";
	public static String VER_AREA="area_ver";
	public static String VER_SCHOOL="school_ver";
	public static String VER_CLASS="class_ver";
	public static String VER_SUBJECT="subject_ver";
	
	//delete favorite
		public static String DELETE_FARVORITE="DELETE_FARVORITE";
		//param delete
		public static String DocumentIDS="DocumentIDS";
	
	
	//param
	public static String IDACCOUNT="AccountID";
	public static String USERNAME="UserName";
	public static String PASSWORD="Password";
	public static String EMAIL="Email";
	public static String FULLNAME="FullName";
	public static String GENDER="Gender";
	public static String BIRTHDAY="BirthDay";
	public static String CLASS="Class";
	public static String IDSCHOOL="SchoolID";
	
	
	//function
	
	public static String FEEDBACK_URL = "SEND_FEEDBACK";
	public static String GET_COMMENT_DOC_URL = "GET_DOCUMENT_COMMENT";
	public static String COMMENT_DOC_URL = "comment_document";
	public static String DETAIL_DOCUMENT = "DETAIL_DOCUMENT";
	public static final String TAG = "tag";
	public static final String RESULT_CODE_OK = "OK";
	public static final String COMMETNS = "comments";
	public static final String LIST_COMMENT = "ListComment";
	public static final String DETAIL = "detail";

	public static final String PAGE = "Page";
 
	public static final String ORDER = "Order";

	public static final String MAIN_CONTENT = "documents";

	public static final String CONTENT_CORE = "documents";

	public static final String DOCUMENT_TITLE = "DocumentTitle";

	public static final String DOCUMENT_TYPE = "DocumentType";

	public static final String DOCUMENT_LINK = "DocumentLink";

	public static final String DATE_UPLOAD = "DateUpload";

	public static final String ACCOUNT_AVT = "AccountLink";

	public static final String ACCOUNT_USERNAME = "AccountFullName";

	public static final String VIEW_COUNT = "Views";

	public static final String DOWNLOAD_COUNT = "Downloads";

	public static final String SUBJECT_ID = "SubjectID";

	public static final String DOCUMENT_GRADE = "Class";

	public static final String DOCUMENT_DESCRIPTION = "Description";

	public static final String IMG_THUMB = "ImageLinkThumb";

	public static final String COMMENT_COUNT = "CommentCount";

	public static final String STATUS = "Status";

//	public static final String CLASS = "Class";
	public static final String TITLE = "Title";

	public static final String GRADE = "Class";

	public static final String SUBJECT = "Subject";

	public static final String PAGE_COUNT = "total_page";

	public static final String CURRENT_PAGE = "current_page";
	//param
	
	public static String DOCUMENT_ID = "DocumentID";
	public static String DOCUMENT_DETAIL_ID = "DocumentDetailID";
	public static String ACCOUNT_ID = "AccountID";
	public static String CONTENTS = "Contents";
	public static String IMG_LINK = "ImageLink";
	public static String DATE_CREATE = "DateCreate";
	public static String FULL_NAME = "FullName";
	public static String AVATAR_LINK = "AccountImgLink";
	public static String ID = "ID";
	public static String MESSAGE = "message";
	//
	
	
	// helper function
	
	public static void showNoInternetToast(Context c){
		Toast.makeText(c, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
	}
	
	public static void showConnectionErrorToast(Context c){
		Toast.makeText(c, "Không thể kết nối đến server", Toast.LENGTH_SHORT).show();
	}
	
	public static void showProgressDialog(Context c,String title, String msg, boolean cancelable, OnCancelListener cancelListener){
		prDialog = ProgressDialog.show(c, title, msg, true, cancelable, cancelListener);
	}
	
	public static void dismissProgressDialog(){
		prDialog.dismiss();
	}
	
	public static boolean checkNetworkState(Context context){
		ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		  if (i == null)
		    return false;
		  if (!i.isConnected())
		    return false;
		  if (!i.isAvailable())
		    return false;
		return true;
	}
	
}
