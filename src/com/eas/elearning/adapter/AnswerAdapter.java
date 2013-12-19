package com.eas.elearning.adapter;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;

import com.eas.elearning.R;
import com.eas.elearning.bean.Answer;
import com.eas.elearning.util.DateTimeFormat;
import com.eas.elearning.util.HTMLUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AnswerAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<Answer> answers;

	public AnswerAdapter(Context c, ArrayList<Answer> data) {
		this.inflater = LayoutInflater.from(c, R.style.Holo_Theme_Light);
		this.answers = data;
	}

	@Override
	public int getCount() {
		return answers.size();
	}

	@Override
	public Object getItem(int pos) {
		return answers.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View cvView, ViewGroup parrent) {
		View row;
		ViewHolder holder;
		if (cvView == null){
			
			holder = new ViewHolder();
			row = inflater.inflate(R.layout.row_answer, parrent, false);
			holder.imgViewAvatar = (ImageView) row.findViewById(R.id.imgViewAvatar);
			holder.tvContent = (TextView) row.findViewById(R.id.tvContent);
			holder.tvPostTime = (TextView) row.findViewById(R.id.tvPostTime);
			holder.tvUserName = (TextView) row.findViewById(R.id.tvUserName);
			row.setTag(holder);
			
		} else {
			row = cvView;
			holder = (ViewHolder) row.getTag();
		}
		
		Answer ans = answers.get(pos);
		ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(ans.getAvatarLink()), holder.imgViewAvatar);
		holder.tvContent.setText(ans.getContent());
		holder.tvPostTime.setText(DateTimeFormat.format(ans.getDateCreate(), DateTimeFormat.SHORT_VALUE));
		holder.tvUserName.setText(ans.getUserName());
		
		return row;
	}
	
	class ViewHolder{
		private ImageView imgViewAvatar;
		private TextView tvContent, tvUserName, tvPostTime;
	}

}
