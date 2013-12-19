package com.eas.elearning.app;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.ImageButton;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.ProgressBar;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.eas.elearning.R;
import com.eas.elearning.adapter.CommentAdapter;
import com.eas.elearning.bean.Comment;
import com.eas.elearning.bean.Document;
import com.eas.elearning.business.CommentTask;
import com.eas.elearning.business.DocumentTask;
import com.eas.elearning.business.ViewsTask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.network.ResponseTranslater;
import com.eas.elearning.util.DataHolder;
import com.eas.elearning.util.FbUtil;
import com.eas.elearning.util.HTMLUtil;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class VideoDetailActivity extends FragmentActivity {

	private String TAG="VideoDetailActivity";
	private ListView lvComment;
	private CommentAdapter adapterCmt;
	private ImageButton btnFarvorite;
	private ImageButton btnSend;
	private ImageButton btnRetryCmt;
	private ProgressBar pbarCmt;
	private TextView tvTitle;
	private TextView tvDes;
	private TextView tvViewCount;
	private TextView tvDownloadCount;
	private TextView tvCommentCount;
	private EditText edtComment;
	private boolean isCheck=false;
	DBAccount mdb;
	String strID="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		

		setContentView(R.layout.activity_video_detail);

		lvComment = (ListView) findViewById(R.id.listViewContain);
		btnSend = (ImageButton) findViewById(R.id.btnSend);
		btnFarvorite = (ImageButton) findViewById(R.id.btnFavorite);

		View header = getLayoutInflater().inflate(R.layout.doc_detail_header, null);

		tvTitle = (TextView) header.findViewById(R.id.tvTitle);
		tvDes = (TextView) header.findViewById(R.id.tvDes);
		tvViewCount = (TextView) header.findViewById(R.id.tvViewCount);
		tvDownloadCount = (TextView) header.findViewById(R.id.tvDownloadCount);
		tvCommentCount = (TextView) header.findViewById(R.id.tvCommentCount);
		btnRetryCmt = (ImageButton) header.findViewById(R.id.btnRetryCmt);
		pbarCmt = (ProgressBar) header.findViewById(R.id.prBarCmt);
		edtComment = (EditText) findViewById(R.id.edtComment);
		
		tvDownloadCount.setVisibility(View.GONE);
		header.findViewById(R.id.tvDownloadCountLabel).setVisibility(View.GONE);

		lvComment.addHeaderView(header);
		adapterCmt = new CommentAdapter(this, new ArrayList<Comment>());
		lvComment.setAdapter(adapterCmt);
		
		btnFarvorite.setBackgroundResource(R.drawable.ic_favorite);
		
		
		
		strID = MyPreference.getInstance().getString("SETTING_ID");
		mdb = new DBAccount(this);
		if(!(strID.equals(""))){
			
			isCheck = mdb.CheckExistFavorite(DataHolder.getDoc().getID(),Integer.parseInt(strID));
			Log.e(TAG, "check user "+isCheck);
			if(!isCheck){
				isCheck = mdb.CheckExistFavorite(DataHolder.getDoc().getID(),-9999);
			}
		}
		else{
			
			isCheck = mdb.CheckExistFavorite(DataHolder.getDoc().getID(),-9999);
			Log.e(TAG, "check  "+isCheck);
		}
		
		if (isCheck) {
			btnFarvorite.setImageResource(R.drawable.ic_favorited);
		}
		
		DataHolder.getDoc().setView(DataHolder.getDoc().getView() + 1);
		
		ViewsTask.AddViewCount(DataHolder.getDoc().getID(), new AsyncHttpResponseHandler());
		
		preparePlayer();
		setupUI();
		getComment();
	}

	@SuppressLint("NewApi")
	private void preparePlayer() {
		YouTubePlayerSupportFragment fragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer);
		fragment.initialize(DataHolder.DEVELOPER_KEY, new OnInitializedListener() {
			
			@Override
			public void onInitializationSuccess(Provider provider, YouTubePlayer player,
					boolean wasRestored) {
				if (!wasRestored) player.cueVideo(HTMLUtil.getYoutubeVideoURLID(DataHolder.getDoc().getDocumentLink()));
			}
			
			@Override
			public void onInitializationFailure(Provider provider,
					YouTubeInitializationResult result) {
				
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnRetrySlide:
			break;
		case R.id.btnRetryCmt:
			getComment();
			break;
		
		case R.id.btnFullScr:
			Intent i = new Intent(this, FullScreenSlideActivity.class);
			startActivity(i);
			break;
		case R.id.btnSend:
			sendComment();
			break;
		case R.id.btnShare:
						
			break;
		case R.id.btnFavorite:
			sendFarvorite();
			break;
			
		case R.id.btnComment:
			View view = findViewById(R.id.layoutInput);
			if(view.getVisibility() == View.GONE) view.setVisibility(View.VISIBLE);
			else view.setVisibility(View.GONE);
			break;
			
		case R.id.tvRelated:
			startActivity(new Intent(getBaseContext(), DocRelatedActivity.class));
		default:
			break;
		}
	}
	public void ShareDocument(View v){
		
		Session session=Session.getActiveSession();
		if(session==null || (!session.isOpened())){
			Session.openActiveSession(getActivity(), true, new StatusCallback() {
				
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					if(session!=null){
						if(state.isOpened()){
							Log.e("DOC: ", DataHolder.getDoc().getTitle());
							FbUtil.share(getActivity(),DataHolder.getDoc().getTitle(),DataHolder.getDoc().getDes(), DataHolder.getDoc().getDocumentLink(), null);
						}
					}
					
				}
			});
		}
		else{
			FbUtil.share(getActivity(),HTMLUtil.getFullUrl(DataHolder.getDoc().getTitle()),HTMLUtil.getFullUrl(DataHolder.getDoc().getDes()), DataHolder.getDoc().getDocumentLink(),HTMLUtil.getFullUrl(DataHolder.getDoc().getImageLink()));
		}
	}

	
	private void sendFarvorite() {
		if (NetworkUtility.checkNetworkState(getActivity())) {
			
			if (strID == "") {
				if (isCheck) {
					mdb.DeleteFavorite(DataHolder.getDoc().getID());
					btnFarvorite.setImageResource(R.drawable.ic_favorite);
					isCheck = false;
					return;
				} else {
					isCheck = true;
					Document objDocument = new Document();
					objDocument.setAccountID(-9999);
					objDocument.setID(DataHolder.getDoc().getID());
					objDocument.setTitle(DataHolder.getDoc().getTitle());
					objDocument.setDes(DataHolder.getDoc().getDes());
					objDocument.setDocumentType(DataHolder.getDoc()
							.getDocumentType());
					objDocument.setDocumentLink(DataHolder.getDoc()
							.getDocumentLink());
					objDocument.setDocumentType(DataHolder.getDoc()
							.getDocumentType());
					objDocument.setDate(DataHolder.getDoc().getDate());
					objDocument
							.setImageLink(DataHolder.getDoc().getImageLink());
					objDocument.setImageLinkThumb(DataHolder.getDoc()
							.getImageLinkThumb());
					objDocument
							.setSubjectID(DataHolder.getDoc().getSubjectID());
					objDocument.setGrade(DataHolder.getDoc().getGrade());
					objDocument.setDownload(DataHolder.getDoc().getDownload());
					objDocument.setView(DataHolder.getDoc().getView());
					objDocument.setComments(DataHolder.getDoc().getComments());
					Log.e("TEST", "" + objDocument.getAccountImgLink());
					mdb.AddDocumentFavorite(objDocument);

					btnFarvorite
							.setImageResource(R.drawable.ic_favorited);
					return;
				}
			}
			else{
				final int idUser = Integer.parseInt(strID);
				if(isCheck){
					isCheck=false;
					deleteFavoriteServer(idUser,DataHolder.getDoc().getID());
					
				}
				else{
					isCheck=true;
					DocumentTask.sendFarvoriteDoc(idUser, DataHolder.getDoc().getID(),
							new AsyncHttpResponseHandler() {

								@Override
								public void onSuccess(int statusCode, String content) {
									if (content == null
											|| !Parser.checkSuccess(content)) {
										NetworkUtility
												.showConnectionErrorToast(getActivity());
										return;
									}
									btnFarvorite.setImageResource(R.drawable.ic_favorited);
									Toast.makeText(getActivity(),
											"Đã đánh dấu yêu thích", Toast.LENGTH_SHORT)
											.show();
									
									Document obj = DataHolder.getDoc();
									obj.setAccountID(idUser);
									mdb.AddDocumentFavorite(obj);
									
								}

								@Override
								public void onFailure(Throwable error, String content) {
									NetworkUtility
											.showConnectionErrorToast(getActivity());
								}

							});
				}
				
			}
			

		} else
			NetworkUtility.showNoInternetToast(getActivity());
	}
	public void deleteFavoriteServer(int idAccount,final int SubjectId){
		if(NetworkUtility.checkNetworkState(getActivity())){
			DocumentTask.deleteFavorite(idAccount, SubjectId, new AsyncHttpResponseHandler(){
				@Override
				@Deprecated
				public void onSuccess(int statusCode, String content) {
					if(ResponseTranslater.checkSuccess(content)){
						
						btnFarvorite.setImageResource(R.drawable.ic_favorite);
						isCheck=false;
						mdb.DeleteFavorite(SubjectId);
					}
				}
				@Override
				@Deprecated
				public void onFailure(Throwable error, String content) {
					NetworkUtility.showConnectionErrorToast(getBaseContext());
					Log.e(TAG, "error: "+content);
				}
			});
		}
		else{
			NetworkUtility.showNoInternetToast(getActivity());
		}
	}
	
	private void setupUI(){
		Document doc = DataHolder.getDoc();
		tvTitle.setText(doc.getTitle());
		tvDes.setText(doc.getDes());
		tvViewCount.setText(doc.getView() + "");
		tvCommentCount.setText(doc.getComments() + "");
		tvDownloadCount.setText(doc.getDownload() + "");
	}

	private void getComment() {
		if (NetworkUtility.checkNetworkState(getBaseContext())) {
			pbarCmt.setVisibility(View.VISIBLE);
			btnRetryCmt.setVisibility(View.GONE);
			CommentTask.getDocComment(DataHolder.getDoc().getID(), new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, String content) {
					
					if (content == null) {
						pbarCmt.setVisibility(View.GONE);
						btnRetryCmt.setVisibility(View.VISIBLE);
						return;
					}
					
					Log.e("cmtRes", content);
					pbarCmt.setVisibility(View.GONE);
					btnRetryCmt.setVisibility(View.GONE);
					tvCommentCount.setText(Parser.getDocComment(content).size() + "");
					adapterCmt.setData(Parser.getDocComment(content));
					
				}
				
				@Override
				public void onFailure(int statusCode, Throwable error,
						String content) {
					pbarCmt.setVisibility(View.GONE);
					btnRetryCmt.setVisibility(View.VISIBLE);
				}
				
			});
		} else {
			pbarCmt.setVisibility(View.GONE);
			btnRetryCmt.setVisibility(View.VISIBLE);
			NetworkUtility.showNoInternetToast(getBaseContext());
		}
	}

	
	
	private void sendComment(){
		String strID = MyPreference.getInstance().getString("SETTING_ID");
		if(strID==""){
			Toast.makeText(getActivity(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
			return;
		}
		String msg = edtComment.getText().toString();
		if (msg.length()<1) {
			Toast.makeText(getBaseContext(), "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT).show();
			return;
		}
		if (NetworkUtility.checkNetworkState(getBaseContext())){
			
			btnSend.setEnabled(false);
			int id = Integer.parseInt(strID);
			CommentTask.CommentDoc(id, DataHolder.getDoc().getID(),msg, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, String content) {
					btnSend.setEnabled(true);
					if (content == null){
						Toast.makeText(getBaseContext(), "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
						return;
					}
					
					if (Parser.checkSuccess(content)){
						getComment();
						edtComment.setText("");
						InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
					    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
					} else {
						Toast.makeText(getBaseContext(), "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					Toast.makeText(getBaseContext(), "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
					btnSend.setEnabled(true);
					return;
				}
				
			});
			
		} else {
			Toast.makeText(getBaseContext(), "Vui lòng bật kết nối internet", Toast.LENGTH_SHORT).show();
		}
	}
	
	/*private void playSlide(){
		timer = new Timer();
		timer.scheduleAtFixedRate(new SlideTask(), 0, 1500);
		
	}*/
	
	
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	private FragmentActivity getActivity(){
		return this;
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

	}

}
