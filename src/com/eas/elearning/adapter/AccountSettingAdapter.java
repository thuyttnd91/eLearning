package com.eas.elearning.adapter;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;

import com.eas.elearning.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

public class AccountSettingAdapter implements SpinnerAdapter{
	Context context;
	ArrayList<String> items;
	LayoutInflater inflater;
	String mEmail;

	public AccountSettingAdapter(Context context, ArrayList<String> items,String email) {
		this.context = context;
		this.items = items;
		inflater = LayoutInflater.from(context);
		mEmail=email;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		TitleViewHolder holder = new TitleViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.spinner_accountsetting_title, null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvEmail);
			convertView.setTag(holder);
		} else
			holder = (TitleViewHolder) convertView.getTag();
			
			holder.tvTitle.setText(mEmail);
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup arg2) {
		DropdownViewHolder dropdownHolder = new DropdownViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.spinner_accountsetting_item, null);
			dropdownHolder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);
			convertView.setTag(dropdownHolder);
		} else
			dropdownHolder = (DropdownViewHolder) convertView.getTag();
			dropdownHolder.tvItem.setText(items.get(position));
		
		return convertView;
	}

	class TitleViewHolder {

		TextView tvTitle;
	}

	/**
	 * The Class DropdownViewHolder.
	 * 
	 * @author anh
	 */
	class DropdownViewHolder {

		TextView tvItem;

	}
}
