package com.eas.elearning.app;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Toast;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eas.elearning.R;
import com.eas.elearning.adapter.CommentAdapter;
import com.eas.elearning.business.CommentTask;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.Parser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

public class CommentDialog {

	private static TextView tvRetry;
	private static ProgressBar pBar;
	private static ProgressBar pBarSend;
	private static View popupLayout;
	private static ListView jobList;
	private static Activity context;
	private static EditText edtComment;
	private static RelativeLayout btnSend;
	private static int id;
	private static onCommentListener listener;

	public interface onCommentListener {
		public void onSuccess();
	}

	public static void show(final Activity context, final int id, onCommentListener listener) {
		CommentDialog.context = context;
		CommentDialog.id = id;
		CommentDialog.listener = listener;
		final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		popupLayout = inflater
				.inflate(R.layout.dialog_comment, null);
		pBar = (ProgressBar) popupLayout.findViewById(R.id.progressBar);
		pBar.setVisibility(View.GONE);
		tvRetry = (TextView) popupLayout.findViewById(R.id.tvRetry);
		btnSend = (RelativeLayout) popupLayout
				.findViewById(R.id.layout_send);
		jobList = (ListView) popupLayout.findViewById(R.id.lv_Comments);
		edtComment = (EditText) popupLayout
				.findViewById(R.id.edtComment);
		pBarSend = (ProgressBar) popupLayout
				.findViewById(R.id.progressBarSend);

		helpBuilder.setView(popupLayout);
		final AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();

		jobList.setOnScrollListener(new PauseOnScrollListener(ImageLoader
				.getInstance(), true, true));

		getComments();

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String strID = MyPreference.getInstance().getString("SETTING_ID");
				if(strID==""){
					Toast.makeText(context, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
					return;
				}
				if (edtComment.length() < 1) {
					Toast.makeText(context, "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				sendCmt();
			}

		});
		tvRetry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getComments();
			}
		});

	}
	
	private static void getComments(){
		/*ArrayList<Comment> fakedata = new  ArrayList<Comment>();
		for (int i = 0; i < 10; i++) {
			fakedata.add(new Comment(1, "abc", "", "12/11/2013", "HoaiLe", "http://img3.tamtay.vn/files/photo2/2013/1/3/15/42642/50e5410d_1b6d1781_avatar-nghien-dau-2.jpg"));
		}
		
		CommentAdapter adapter = new CommentAdapter(context, fakedata);
		jobList.setAdapter(adapter);*/
		
		tvRetry.setVisibility(View.GONE);
		pBar.setVisibility(View.VISIBLE);
		CommentTask.getDetailComment(id, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				if (content == null){
					tvRetry.setVisibility(View.VISIBLE);
					pBar.setVisibility(View.GONE);
					return;
				}
				
				if (Parser.checkSuccess(content)){
					tvRetry.setVisibility(View.GONE);
					pBar.setVisibility(View.GONE);
					jobList.setAdapter(new CommentAdapter(context, Parser.getDocDetailComment(content)));
				} else {
					tvRetry.setVisibility(View.VISIBLE);
					pBar.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				tvRetry.setVisibility(View.VISIBLE);
				pBar.setVisibility(View.GONE);
			}
			
		});
				
	}
	
	private static void sendCmt(){
		
		btnSend.setVisibility(View.INVISIBLE);
		pBarSend.setVisibility(View.VISIBLE);
		
		String strID = MyPreference.getInstance().getString("SETTING_ID");
		int accId = Integer.parseInt(strID);
		CommentTask.CommentDocDetail(accId, id, edtComment.getText().toString(), new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				if (content == null){
					btnSend.setVisibility(View.VISIBLE);
					pBarSend.setVisibility(View.GONE);
					Toast.makeText(context, "Không thể đăng bình luận", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(Parser.checkSuccess(content)){
					
					if (listener != null) listener.onSuccess();
					btnSend.setVisibility(View.VISIBLE);
					pBarSend.setVisibility(View.GONE);
					edtComment.setText("");
					InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(Activity.INPUT_METHOD_SERVICE);
				    inputMethodManager.hideSoftInputFromWindow(popupLayout.getWindowToken(), 0);
				    getComments();
					
				} else{
					btnSend.setVisibility(View.VISIBLE);
					pBarSend.setVisibility(View.GONE);
					Toast.makeText(context, "Không thể đăng bình luận", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				btnSend.setVisibility(View.VISIBLE);
				pBarSend.setVisibility(View.GONE);
				Toast.makeText(context, "Không thể đăng bình luận", Toast.LENGTH_SHORT).show();
			}
			
		});
	}

}
