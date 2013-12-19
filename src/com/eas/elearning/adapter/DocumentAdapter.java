package com.eas.elearning.adapter;

import java.util.ArrayList;
import java.util.Collections;

import org.holoeverywhere.LayoutInflater;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eas.elearning.R;
import com.eas.elearning.bean.Document;
import com.eas.elearning.util.HTMLUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DocumentAdapter extends BaseAdapter {

	private ArrayList<Document> rowSub;
	Context ctx;
	LayoutInflater myInflater;
	private ArrayList<String> colors;
	
	public DocumentAdapter(ArrayList<Document> rowSub, Context ctx) {
		super();
		this.rowSub = rowSub;
		Log.e("row","" + rowSub.size());
		this.ctx = ctx;
		colors = new ArrayList<String>();
		colors.add("#ff5e4d");
		colors.add("#527423");
		colors.add("#5a5e6b");
		colors.add("#df6925");
		colors.add("#25bfda");
		colors.add("#9e0e40");
		Collections.shuffle(colors);
	}
	
	public void setDocumentData(ArrayList<Document> data){
		rowSub = data;
		this.notifyDataSetChanged();
	}

    @Override
    public int getCount() {
    	return rowSub.size();
    }

	@Override
	public Object getItem(int position) {
		return rowSub.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowSub.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.e("row","" + getCount());
		ViewHolder holder;
		myInflater = LayoutInflater.from(ctx);
		
		
		View rowView;
		if(convertView == null){
			holder = new ViewHolder();
			rowView = myInflater.inflate(R.layout.document_row, parent, false);
			holder.txtTitle = (TextView)rowView.findViewById(R.id.tv_title_sub);
			holder.txtDes = (TextView)rowView.findViewById(R.id.tv_des_sub);
			holder.txtDownload = (TextView)rowView.findViewById(R.id.tv_download);
			holder.txtView = (TextView)rowView.findViewById(R.id.tv_views);
			holder.txtComment = (TextView)rowView.findViewById(R.id.tv_comment);
			holder.imgThumb = (ImageView) rowView.findViewById(R.id.img_thumb_sub);
			holder.imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatar);
			rowView.setTag(holder);
			
		}
		
		else{
			rowView= (View)convertView;
			holder = (ViewHolder)rowView.getTag();
		}
				
		holder.txtTitle.setTextColor(Color.parseColor(colors.get(position%colors.size())));
//		holder.line.setBackgroundColor(Color.parseColor(colors.get(position%colors.size())));
		
		Document dcm = rowSub.get(position);
		
		if (dcm.getDocumentType() == 2) {
			holder.txtDownload.setVisibility(View.GONE);
			rowView.findViewById(R.id.tv_downloads).setVisibility(View.GONE);
		}
		else {
			holder.txtDownload.setVisibility(View.VISIBLE);
			rowView.findViewById(R.id.tv_downloads).setVisibility(View.VISIBLE);
		}
		
		holder.txtTitle.setText(dcm.getTitle());
		holder.txtDes.setText(dcm.getFullName());
		holder.txtView.setText(""+dcm.getView());
		holder.txtDownload.setText(""+dcm.getDownload());
		holder.txtComment.setText(""+dcm.getComments());
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(dcm.getImageLinkThumb()), holder.imgThumb);
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(dcm.getAccountImgLink()), holder.imgAvatar);
		
		return rowView;
		
//		LayoutInflater inflater = (LayoutInflater) ctx
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//		View listView;
//
//		listView = new View(ctx);
//		
//		ViewHolder holder = new ViewHolder();
//			if (convertView == null) {
//				holder = new ViewHolder();
//				listView = inflater.inflate(R.layout.document_row, null);
//				listView.setTag(holder);
//				
//			} else {
//				listView = (View) convertView;
//				holder = (ViewHolder) listView.getTag();
//			}
//			
//		 
//		return listView;
		
		
	}
	
	static class ViewHolder {

		TextView txtTitle, txtDes, txtView, txtDownload, txtComment;
		ImageView imgThumb, imgAvatar;
	}

}
