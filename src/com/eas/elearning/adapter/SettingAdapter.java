package com.eas.elearning.adapter;

import java.util.ArrayList;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.CheckedTextView;

import com.eas.elearning.R;
import com.eas.elearning.bean.ObjSubject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SettingAdapter extends ArrayAdapter<ObjSubject>{
	ArrayList<ObjSubject> arrSubject;
	Context mContext;
	public boolean check=false;
	public SettingAdapter(Context context,
			int textViewResourceId, ArrayList<ObjSubject> objects) {
		super(context, textViewResourceId, objects);
		this.arrSubject=objects;
		this.mContext=context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=convertView;
		ViewHolder holder;
		LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(v==null){
			holder=new ViewHolder();
			v=inflate.inflate(R.layout.row_subject, null);
			holder.tvSubject=(CheckedTextView) v.findViewById(R.id.tvSubject);
			v.setTag(holder);
		}
		else{
			holder=(ViewHolder) v.getTag();
		}
		holder.tvSubject.setText(""+arrSubject.get(position).getNameSubject());
		if(check){
			holder.tvSubject.setChecked(true);
		}
		else{
			holder.tvSubject.setChecked(false);
			holder.tvSubject.setChecked(load(position));
		}
		
		return v;
	}
	public class ViewHolder{
		CheckedTextView tvSubject;
	}
	
	private boolean load(int index) {
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	    return sharedPreferences.getBoolean("check" +index, false);
	}
}
