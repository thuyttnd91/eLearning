package com.eas.elearning.adapter;

import java.util.ArrayList;

import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.eas.elearning.R;
import com.eas.elearning.bean.Comment;
import com.eas.elearning.util.DateTimeFormat;
import com.eas.elearning.util.HTMLUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommentAdapter extends BaseAdapter {

	Context context;
	ArrayList<Comment> data;

	public CommentAdapter(Context context, ArrayList<Comment> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.comment_row, null);
			holder.imgAvata = (ImageView) convertView
					.findViewById(R.id.img_avata);
			holder.imgContent = (ImageView) convertView
					.findViewById(R.id.img_cm);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tvMsg = (TextView) convertView
					.findViewById(R.id.tvMsg);
			convertView.setTag(holder);
		}

		else {
			convertView = (View) convertView;
			holder = (ViewHolder) convertView.getTag();
		}
		
		Comment cmt = data.get(position);
		
		holder.tvName.setText(cmt.getFullname());
		holder.tvDate.setText(DateTimeFormat.format(cmt.getDateCreate(), DateTimeFormat.SHORT_VALUE));
		holder.tvMsg.setText(cmt.getContents());
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.bitmapConfig(Config.RGB_565)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.resetViewBeforeLoading(true)
		.showStubImage(R.drawable.avatar_placeholder2)
		.build();
		
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(cmt.getAccLink()), holder.imgAvata, options);
		
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(cmt.getImageLink()), holder.imgContent);
		
		return convertView;
	}
	
	public void setData(ArrayList<Comment> data){
		this.data = data;
		this.notifyDataSetChanged();
	}

	public static class ViewHolder {
		ImageView imgAvata, imgContent;
		TextView tvName, tvDate, tvMsg;
	}

}
