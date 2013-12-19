package com.eas.elearning.app;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.ProgressBar;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.eas.elearning.R;
import com.eas.elearning.adapter.PostAdapter;
import com.eas.elearning.bean.Post;
import com.eas.elearning.business.QATask;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.util.DataHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class PostListFragment extends Fragment {
	
	private ArrayList<Post> posts;
	private ListView lvPost;
	private Button btnRetry;
	private ProgressBar prBar;
	private TextView tvNoData;
	private PostAdapter adapter;
	
	public static final int NEW_POST_REQUEST = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		setHasOptionsMenu(true);
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActivity().setTitle("Hỏi đáp");
		
		View root = inflater.inflate(R.layout.activity_post_list, container, false);
		
		lvPost = (ListView) root.findViewById(R.id.lvPost);
		tvNoData = (TextView) root.findViewById(R.id.tvNoData);
		btnRetry = (Button) root.findViewById(R.id.btnRetry);
		prBar = (ProgressBar) root.findViewById(R.id.prBar);
		
		btnRetry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData();
			}
		});
		
		lvPost.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int pos,
					long id) {
				DataHolder.setPost(posts.get(pos));
				startActivity(new Intent(getSupportActivity(), PostDetailActivity.class));
			}
		});
		
		getData();
		
		return root;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		inflater.inflate(R.menu.post_list, menu);
		
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.acNewPost:
			String id = MyPreference.getInstance().getString("SETTING_ID");
			if (id == "") Toast.makeText(getSupportActivity(), "Vui lòng đăng nhập để tạo câu hỏi", Toast.LENGTH_SHORT).show();
			else startActivityForResult(new Intent(getSupportActivity(), NewPostActivity.class), NEW_POST_REQUEST);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == NEW_POST_REQUEST)
		if (resultCode == Activity.RESULT_OK){
			getData();
		}
	}
	
	public void getData(){
		tvNoData.setVisibility(View.GONE);
		btnRetry.setVisibility(View.GONE);
		prBar.setVisibility(View.VISIBLE);
		if (NetworkUtility.checkNetworkState(getSupportActivity())){
			QATask.getPost(1, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, String content) {
					if (content == null){
						btnRetry.setVisibility(View.VISIBLE);
						prBar.setVisibility(View.GONE);
						return;
					}
					posts = Parser.getPosts(content);
					if (posts.size()<1){
						tvNoData.setVisibility(View.VISIBLE);
						btnRetry.setVisibility(View.GONE);
						prBar.setVisibility(View.GONE);
						return;
					}
					
					adapter = new PostAdapter(getSupportActivity(), posts);
					lvPost.setAdapter(adapter);
					
					prBar.setVisibility(View.GONE);
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					btnRetry.setVisibility(View.VISIBLE);
					prBar.setVisibility(View.GONE);
				}
				
			});
		} else {
			btnRetry.setVisibility(View.VISIBLE);
			prBar.setVisibility(View.GONE);
			NetworkUtility.showNoInternetToast(getSupportActivity());
		}
	}

}
