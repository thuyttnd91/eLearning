package com.eas.elearning.adapter;


import java.io.File;
import java.util.ArrayList;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eas.elearning.R;
import com.eas.elearning.bean.Document;
import com.eas.elearning.db.DBAccount;

public class DownloadAdapter extends BaseAdapter {
	
	private Context context;
	ViewHolder holder;
	DBAccount mdb;
	private ArrayList<Document> downloaded;
	

	public DownloadAdapter(Context context, ArrayList<Document> downloaded) {
		this.context = context;
		this.downloaded = downloaded;
		Log.e("TEST: ",""+ downloaded.size());
		mdb=new DBAccount(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return downloaded.size()+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View listView;

		listView = new View(context);
		switch (getItemViewType(position)) {
		case 0:
			if (convertView == null) {
				// declare
				listView = inflater.inflate(R.layout.row_download_group_header,
						null);
				listView.findViewById(R.id.tvHeader);
				
				
			} else {
				listView = (View) convertView;
			}
			break;
		default:
			if (convertView == null) {
				holder = new ViewHolder();
				listView = inflater.inflate(R.layout.row_download_content, null);
				holder.tvTitle = (TextView) listView.findViewById(R.id.tvFileName);
				holder.imgDes = (ImageView) listView.findViewById(R.id.imgIcon);
				holder.tvDate = (TextView) listView.findViewById(R.id.tvDescription);
				holder.btnDelete = (LinearLayout) listView.findViewById(R.id.btnDelete);
				listView.setTag(holder);
				
			} else {
				listView = (View) convertView;
				holder = (ViewHolder) listView.getTag();
			}
				File file=new File(downloaded.get(position-1).getDocumentLink());
				holder.tvTitle.setText(file.getName());
				holder.tvDate.setText(downloaded.get(position-1).getDate());
				String ext = "";
				try{
				ext = file.getName().substring(file.getName().indexOf("."));
				} catch(StringIndexOutOfBoundsException e){
					e.printStackTrace();
				}
				if(ext.equals(".docx")||ext.equals(".doc")) holder.imgDes.setImageResource(R.drawable.ic_doc);
				else if(ext.equals(".xls")||ext.equals(".xlsx")) holder.imgDes.setImageResource(R.drawable.ic_excel);
				else if(ext.equals(".ppt")||ext.equals(".pptx")) holder.imgDes.setImageResource(R.drawable.ic_presentation);
				else holder.imgDes.setImageResource(R.drawable.ic_file);
				holder.btnDelete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
						LayoutInflater inflater = (LayoutInflater) context
								.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

						View popupLayout = inflater
								.inflate(R.layout.dialog_remove_download, null);
						TextView tvTitle = (TextView) popupLayout.findViewById(R.id.tvTitle);
						TextView tvMessage = (TextView) popupLayout.findViewById(R.id.tvMessage);
						Button btnOK = (Button) popupLayout.findViewById(R.id.btnOK);
						Button btnCancel = (Button) popupLayout.findViewById(R.id.btnCancel);
						tvTitle.setText("Cảnh báo");
						tvMessage.setText("Bạn chắc chắn muốn xóa tài liệu này ?");
						btnOK.setText("Xóa");
						btnCancel.setText("Hủy");
					
						helpBuilder.setView(popupLayout);
						final AlertDialog helpDialog = helpBuilder.create();
						helpDialog.show();
						
						btnOK.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								mdb.deleteDocumentDownload(downloaded.get(position-1).getID());
								downloaded.remove(position-1);
								DownloadAdapter.this.notifyDataSetChanged();
								helpDialog.dismiss();
							}
						});
						
						btnCancel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								helpDialog.dismiss();
							}
						});
						
//						AlertDialog.Builder restartAlertBuilder = new AlertDialog.Builder(context);
//						restartAlertBuilder.setTitle("Cảnh báo");
//						restartAlertBuilder
//								.setMessage("Bạn chắc chắn muốn xóa tài liệu này ?");
//						restartAlertBuilder.setCancelable(true);
//						restartAlertBuilder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								mdb.deleteDocumentDownload(downloaded.get(position-1).getID());
//								downloaded.remove(position-1);
//								DownloadAdapter.this.notifyDataSetChanged();
//							}
//						});
//						restartAlertBuilder.setNegativeButton("Hủy", null);
						/*restartAlertBuilder.setPositiveButton("Ok", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								startCheckResult();
							}
						});*/

//						AlertDialog restartAlertDialog = restartAlertBuilder.create();
//						restartAlertDialog.show();
						
						
						//mdb.deleteDocumentDownload(downloaded.get(position).getID());
						
					}
				});
			break;
		}
		 
		return listView;
	}
	
	@Override
	public int getViewTypeCount() {
	    return 2;
	}
	
	@Override
	public int getItemViewType(int position) {

		if (position==0)
			return 0;
		else
			return 1;
	}
	
	private class ViewHolder{
		TextView tvTitle, tvDate;
		ImageView imgDes;
		LinearLayout btnDelete;
	}

}
