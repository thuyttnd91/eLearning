package com.eas.elearning.adapter;

import java.util.ArrayList;

import com.eas.elearning.app.DocPageFragment;
import com.eas.elearning.app.FullScreenFragment;
import com.eas.elearning.bean.DocPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class DocSlideAdapter extends FragmentStatePagerAdapter{
	private ArrayList<DocPage> data;
	private int type;

	public DocSlideAdapter(FragmentManager fm, ArrayList<DocPage> data, int type) {
		super(fm);
		this.data = data;
		this.type = type;
	}

	@Override
	public Fragment getItem(int pos) {
		if(type!=2) {
		DocPageFragment page = new DocPageFragment();
		page.setData(data.get(pos));
			return page;
		} else {
			FullScreenFragment page = new FullScreenFragment();
			page.setData(data.get(pos), type);
			return page;
		}
	}

	@Override
	public int getCount() {
		return data.size();
	}
	
	public void setData(ArrayList<DocPage> data){
		this.data = data;
		this.notifyDataSetChanged();
	}

	

}
