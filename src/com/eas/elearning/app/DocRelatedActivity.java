package com.eas.elearning.app;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.ImageButton;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.ProgressBar;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.eas.elearning.R;
import com.eas.elearning.adapter.DocRelatedAdapter;
import com.eas.elearning.bean.Document;
import com.eas.elearning.business.DocumentTask;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.util.DataHolder;
import com.eas.elearning.util.DateTimeFormat;
import com.eas.elearning.util.HTMLUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DocRelatedActivity extends Activity {
	private ListView listDoc;
	private ImageButton btnRetry;
	private TextView tvNoData;
	private ProgressBar prBar;
	private ArrayList<Document> docs;
	private DocRelatedAdapter adapter;
	private View layoutListDoc;
	
	private TextView tvTitle;
	private TextView tvUploader;
	private TextView tvUploadTime;
	private ImageView imgViewThumb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_related_doc);
			
		listDoc = (ListView) findViewById(R.id.listDoc);
		btnRetry = (ImageButton) findViewById(R.id.btnRetry);
		tvNoData = (TextView) findViewById(R.id.tvNoData);
		prBar = (ProgressBar) findViewById(R.id.prBar);
		layoutListDoc = findViewById(R.id.layoutList);
		
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvUploader = (TextView) findViewById(R.id.tvUploader);
		tvUploadTime = (TextView) findViewById(R.id.tvUploadTime);
		imgViewThumb = (ImageView) findViewById(R.id.imgViewThumb);
		
		Document doc = DataHolder.getDoc();
		
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(doc.getImageLinkThumb()), imgViewThumb);
		
		tvTitle.setText(doc.getTitle());
		tvUploader.setText(doc.getFullName());
		tvUploadTime.setText(DateTimeFormat.format(doc.getDate(), DateTimeFormat.SHORT_VALUE));
		
		btnRetry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData();	
			}
		});
		
		listDoc.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View arg1, int pos,
					long id) {
				DataHolder.setDoc((Document) a.getItemAtPosition(pos));
				Intent i; 
				if (DataHolder.getDoc().getDocumentType() == 1){
				i = new Intent(getBaseContext(),
						DocDetailActivity.class);
				} else {
					i = new Intent(getBaseContext(), VideoDetailActivity.class);
				}
				startActivity(i);
			}
		});
		
		View v = getLayoutInflater().inflate(R.layout.header_doc_related);
		listDoc.addHeaderView(v);
		getData();
	}
	
	private void getData(){
		layoutListDoc.setVisibility(View.GONE);
		tvNoData.setVisibility(View.GONE);
		btnRetry.setVisibility(View.GONE);
		prBar.setVisibility(View.VISIBLE);
		if (NetworkUtility.checkNetworkState(getBaseContext())){
			
			DocumentTask.getRelatedDoc(DataHolder.getDoc().getID(), new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, String content) {
					if (content==null){
						tvNoData.setVisibility(View.GONE);
						btnRetry.setVisibility(View.VISIBLE);
						prBar.setVisibility(View.GONE);
						return;
					}
					docs = Parser.getDocument(content);
					if (docs.size()<1){
						tvNoData.setVisibility(View.VISIBLE);
						btnRetry.setVisibility(View.GONE);
						prBar.setVisibility(View.GONE);
						return;
					}
					tvNoData.setVisibility(View.GONE);
					btnRetry.setVisibility(View.GONE);
					prBar.setVisibility(View.GONE);
					layoutListDoc.setVisibility(View.VISIBLE);
					
					adapter = new DocRelatedAdapter(getBaseContext(), docs);
					listDoc.setAdapter(adapter);
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					tvNoData.setVisibility(View.GONE);
					btnRetry.setVisibility(View.VISIBLE);
					prBar.setVisibility(View.GONE);
				}
				
			});
			
		} else {
			NetworkUtility.showNoInternetToast(getBaseContext());
			tvNoData.setVisibility(View.GONE);
			btnRetry.setVisibility(View.VISIBLE);
			prBar.setVisibility(View.GONE);
		}
		
	}

}
