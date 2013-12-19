package com.eas.elearning.app;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.holoeverywhere.app.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.eas.elearning.R;
import com.eas.elearning.adapter.DownloadAdapter;
import com.eas.elearning.bean.Document;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;

public class DownloadActivity extends Activity {
	
	private ListView lvDownload;
	private DownloadAdapter adapter;
	private ActionBar mActionBar;
	DBAccount mDb;
	ImageView back;
	ArrayList<Document> arrDocument = new ArrayList<Document>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		mActionBar = this.getSupportActionBar();
		mDb=new DBAccount(this);
		mActionBar.hide();
		lvDownload = (ListView) findViewById(R.id.lvDownload);
		back = (ImageView) findViewById(R.id.imgIcon);
		getDocumentfromDb();
		
	
		
		lvDownload.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position!=0){
				

				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				File file = new File(arrDocument.get(position-1).getDocumentLink());
				intent.setDataAndType(Uri.fromFile(file), "application/text");
				
				try {
                    startActivity(intent);
                } catch(ActivityNotFoundException e) {
                	intent.setDataAndType(Uri.fromFile(file), "application/*");
                    startActivity(intent);
                }
				
				}
				
			}
		});
	}
	public void getDocumentfromDb(){
		MyPreference.getInstance().Initialize(this);
		String strID = MyPreference.getInstance().getString("SETTING_ID");
		if(strID.equals("")==false){
		 ArrayList<Document> list1=mDb.getDocumentDownloaded(Integer.parseInt(strID));
		 ArrayList<Document> list2=mDb.getDocumentDownloaded(-9999);
		 List<Document> all = new ArrayList<Document>();
		 all.addAll(list1);
		 all.addAll(list2);

		 Map<String, Document> map = new HashMap<String, Document>();
		 for (Document p : all) {
		     if (!map.containsKey(""+p.getID())) {
		         map.put(""+p.getID(), p);
		     }
		 }
		 arrDocument = new ArrayList<Document>(map.values());
		Log.e("DOWNLOAD ACTIVITY: ", ""+arrDocument.size());
		} else {
			arrDocument=mDb.getDocumentDownloaded(-9999);
		}
		adapter = new DownloadAdapter(this, arrDocument);
		lvDownload.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.download, menu);
		return true;
		
	}
	
	public void onClickBack(View v){
		setResult(1);
	}
	
	public void hanlerBack(View view){
		onBackPressed();
	}

}
