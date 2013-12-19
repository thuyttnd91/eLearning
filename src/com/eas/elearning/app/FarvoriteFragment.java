package com.eas.elearning.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ImageButton;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.ProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.eas.elearning.R;
import com.eas.elearning.adapter.FavoriteAdapter;
import com.eas.elearning.bean.Document;
import com.eas.elearning.business.DocumentTask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.util.DataHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FarvoriteFragment extends Fragment {
	private ListView lvFar;
	private ArrayList<Document> docs;
	private ArrayList<Document> localDocs;
	private ArrayList<Document> onlineDocs;
	ProgressBar pro_data;
	DBAccount mDb;
	Button btnRetry;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.list_document_activity, container, false);
		
		mDb = new DBAccount(getSupportActivity());
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActivity().setTitle("Yêu thích");
		
		lvFar = (ListView) root.findViewById(R.id.lv_subjects);
		pro_data=(ProgressBar) root.findViewById(R.id.pro_data);
		btnRetry = (Button) root.findViewById(R.id.btnRetryData);
		ImageButton imgFilter_hor = (ImageButton) root.findViewById(R.id.imgFilter_ho);
		ImageButton imgFilter_ver = (ImageButton) root.findViewById(R.id.imgFilter_ve);
		
		imgFilter_hor.setVisibility(View.GONE);
		imgFilter_ver.setVisibility(View.GONE);
		
		btnRetry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData();
				pro_data.setVisibility(View.VISIBLE);
				btnRetry.setVisibility(View.GONE);
			}
		});
		lvFar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int pos, long id) {
				DataHolder.setDoc((Document) a.getItemAtPosition(pos));
				Intent i; 
				if (DataHolder.getDoc().getDocumentType() == 1){
				i = new Intent(getSupportActivity(),
						DocDetailActivity.class);
				} else {
					i = new Intent(getSupportActivity(), VideoDetailActivity.class);
				}
				startActivity(i);
			}
		});
		
		if (docs==null) getData();
		else lvFar.setAdapter(new FavoriteAdapter(docs, getSupportActivity()));
		
		return root;
	}
	
	
	private void getData() {
		
			final String strID = MyPreference.getInstance().getString("SETTING_ID");
			if (strID.equals("") == false) {
				if (NetworkUtility.checkNetworkState(getSupportActivity())) {
				int id = Integer.parseInt(strID);
				Log.d("result", ""+id);
				DocumentTask.getFarvoriteDoc(id,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int statusCode, String content) {

								if (content == null
										|| getSupportActivity() == null) {
									return;
								}
								
								Log.d("result", content);
								onlineDocs = Parser.getFarvDoc(content);
//								if(localDocs!=null){
									 ArrayList<Document> list1= onlineDocs;
									 ArrayList<Document> list2=mDb.getDocumentFavorite(-9999);
									 List<Document> all = new ArrayList<Document>();
									 all.addAll(list1);
									 all.addAll(list2);

									 Map<String, Document> map = new HashMap<String, Document>();
									 for (Document p : all) {
									     if (!map.containsKey(""+p.getID())) {
									         map.put(""+p.getID(), p);
									     }
									 }
									 docs = new ArrayList<Document>(map.values());
//								}
								lvFar.setAdapter(new FavoriteAdapter(docs,
										getSupportActivity()));
								pro_data.setVisibility(View.GONE);

							}

							@Override
							public void onFailure(Throwable error,
									String content) {
								pro_data.setVisibility(View.VISIBLE);
							}

						});
			} else {
				btnRetry.setVisibility(View.VISIBLE);
				pro_data.setVisibility(View.GONE);
			}
		} else {
			btnRetry.setVisibility(View.GONE);
			pro_data.setVisibility(View.GONE);
			localDocs = mDb.getDocumentFavorite(-9999);
			Log.d("Favorite",""+localDocs.size());
			lvFar.setAdapter(new FavoriteAdapter(localDocs,
					getSupportActivity()));
		
		}

	}
	
	@Override
	public void onResume() {
		super.onResume();
		getData();
	}
	
	
}
 