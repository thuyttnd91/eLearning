package com.eas.elearning.adapter;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;

import com.eas.elearning.R;
import com.eas.elearning.bean.Document;
import com.eas.elearning.util.DateTimeFormat;
import com.eas.elearning.util.HTMLUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DocRelatedAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<Document> docs;

	public DocRelatedAdapter(Context c, ArrayList<Document> docs) {
		this.docs = docs;
		this.inflater = LayoutInflater.from(c, R.style.Holo_Theme_Light_NoActionBar);
	}

	@Override
	public int getCount() {
		return docs.size();
	}

	@Override
	public Object getItem(int position) {
		return docs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return docs.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			row = inflater.inflate(R.layout.row_doc_related, parent, false);
			holder.imgViewThumb = (ImageView) row.findViewById(R.id.imgViewThumb);
			holder.tvTitle = (TextView) row.findViewById(R.id.tvTitle);
			holder.tvUploader = (TextView) row.findViewById(R.id.tvUploader);
			holder.tvUploadTime = (TextView) row.findViewById(R.id.tvUploadTime);
			row.setTag(holder);
		} else {
			row = convertView;
			holder = (ViewHolder) row.getTag();
		}
		
		Document doc = docs.get(position);
		
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(doc.getImageLinkThumb()), holder.imgViewThumb);
		
		holder.tvTitle.setText(doc.getTitle());
		holder.tvUploader.setText(doc.getFullName());
		holder.tvUploadTime.setText(DateTimeFormat.format(doc.getDateUpload(), DateTimeFormat.SHORT_VALUE));
		
		return row;
	}
	
	class ViewHolder{
		ImageView imgViewThumb;
		TextView tvTitle;
		TextView tvUploader;
		TextView tvUploadTime;
	}

}
