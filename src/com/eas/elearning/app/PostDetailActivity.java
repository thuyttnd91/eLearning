package com.eas.elearning.app;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.ImageButton;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.ProgressBar;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.eas.elearning.R;
import com.eas.elearning.adapter.AnswerAdapter;
import com.eas.elearning.bean.Answer;
import com.eas.elearning.bean.Post;
import com.eas.elearning.business.QATask;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.util.DataHolder;
import com.eas.elearning.util.DateTimeFormat;
import com.eas.elearning.util.HTMLUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PostDetailActivity extends Activity {
	private ArrayList<Answer> answers;
	private ListView lvAns;
	private ImageButton btnRetry;
	private ProgressBar prBar, prBarSend;
	private TextView tvNoData;
	private View layoutList;
	private AnswerAdapter adapter;
	private ImageView imgViewAvatar;
	private TextView tvContent, tvUserName, tvPostTime;
	private ImageButton btnSend;
	private EditText edtComment;
	private View layoutSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_post_detail);
		lvAns = (ListView) findViewById(R.id.listAns);
		tvNoData = (TextView) findViewById(R.id.tvNoData);
		btnRetry = (ImageButton) findViewById(R.id.btnRetry);
		prBar = (ProgressBar) findViewById(R.id.prBar);
		layoutList = findViewById(R.id.layoutList);
		imgViewAvatar = (ImageView) findViewById(R.id.imgViewAvatar);
		tvContent = (TextView) findViewById(R.id.tvContent);
		tvPostTime = (TextView) findViewById(R.id.tvPostTime);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		btnSend = (ImageButton) findViewById(R.id.btnSend);
		edtComment = (EditText) findViewById(R.id.edtComment);
		prBarSend = (ProgressBar) findViewById(R.id.progressBarSend);
		layoutSend = findViewById(R.id.layout_send);
		
		Post post = DataHolder.getPost();
		
		tvContent.setText(post.getContent());
		tvUserName.setText(post.getUserName());
		tvPostTime.setText(DateTimeFormat.format(post.getDateCreate(), DateTimeFormat.SHORT_VALUE));
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(post.getAvatarLink()), imgViewAvatar);
		
		getAnswer();
		
	}
	
	private void getAnswer(){
		tvNoData.setVisibility(View.GONE);
		btnRetry.setVisibility(View.GONE);
		prBar.setVisibility(View.VISIBLE);
		layoutList.setVisibility(View.GONE);
		QATask.getAnswer(DataHolder.getPost().getId(), new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				
				if (content == null){
					btnRetry.setVisibility(View.VISIBLE);
					prBar.setVisibility(View.GONE);
					return;
				}
				answers = Parser.getAnswers(content);
				if (answers.size()<1){
					tvNoData.setVisibility(View.VISIBLE);
					btnRetry.setVisibility(View.GONE);
					prBar.setVisibility(View.GONE);
					return;
				}
				
				adapter = new AnswerAdapter(getBaseContext(), answers);
				lvAns.setAdapter(adapter);
				
				layoutList.setVisibility(View.VISIBLE);
				prBar.setVisibility(View.GONE);
				
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				btnRetry.setVisibility(View.VISIBLE);
				prBar.setVisibility(View.GONE);
			}
			
		});
	}
	
	public void sendAns(View v){
		String strID = MyPreference.getInstance().getString("SETTING_ID");
		if (strID == "") {
			Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String ans = edtComment.getText().toString();
		if (ans.length() < 1) {
			Toast.makeText(this, "Bạn chưa nhập câu trả lời", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (NetworkUtility.checkNetworkState(this)){
			btnSend.setVisibility(View.GONE);
			prBarSend.setVisibility(View.VISIBLE);
			layoutSend.setClickable(false);
			
			QATask.sendAnswer(DataHolder.getPost().getId(), Integer.parseInt(strID), ans, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, String content) {
					
					if (content == null) {
						btnSend.setVisibility(View.VISIBLE);
						prBarSend.setVisibility(View.GONE);
						layoutSend.setClickable(true);
						Toast.makeText(getBaseContext(), "Không thể gửi trả lời", Toast.LENGTH_SHORT).show();
						return;
					}
					
					if (Parser.checkSuccess(content)){
						Toast.makeText(getBaseContext(), "Đã gửi câu trả lời", Toast.LENGTH_SHORT).show();
						edtComment.setText("");
						InputMethodManager inputMethodManager = (InputMethodManager)  getBaseContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
					    inputMethodManager.hideSoftInputFromWindow(PostDetailActivity.this.getCurrentFocus().getWindowToken(), 0);
						btnSend.setVisibility(View.VISIBLE);
						prBarSend.setVisibility(View.GONE);
						layoutSend.setClickable(true);
						getAnswer();
					} else {
						btnSend.setVisibility(View.VISIBLE);
						prBarSend.setVisibility(View.GONE);
						layoutSend.setClickable(true);
						Toast.makeText(getBaseContext(), "Không thể gửi trả lời", Toast.LENGTH_SHORT).show();
					}
					
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					btnSend.setVisibility(View.VISIBLE);
					prBarSend.setVisibility(View.GONE);
					layoutSend.setClickable(true);
					Toast.makeText(getBaseContext(), "Không thể gửi trả lời", Toast.LENGTH_SHORT).show();
				}
				
			});
		} else {
			NetworkUtility.showNoInternetToast(this);
			return;
		}
	}

}
