package com.eas.elearning.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eas.elearning.app.DocPageFragment;
import com.eas.elearning.app.FullScreenFragment;
import com.eas.elearning.bean.DocPage;
import com.eas.elearning.customview.GalleryViewPager;
import com.eas.elearning.customview.ZoomableImageView;


public class FullScreenSlideAdapter extends FragmentStatePagerAdapter{
	private ArrayList<DocPage> data;
	private int type;
	protected int mCurrentPosition = -1;
	protected OnItemChangeListener mOnItemChangeListener;

	public FullScreenSlideAdapter(FragmentManager fm, ArrayList<DocPage> data, int type) {
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
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
		if (mCurrentPosition == position)
			return;
		GalleryViewPager galleryContainer = ((GalleryViewPager) container);
		if (galleryContainer.mCurrentView != null)
			galleryContainer.mCurrentView.resetScale();

		mCurrentPosition = position;
		if (mOnItemChangeListener != null)
			mOnItemChangeListener.onItemChange(mCurrentPosition);
		galleryContainer.mCurrentView = ((FullScreenFragment) object).getZoomableImageView();
	}
	
	@Override
	public Object instantiateItem(ViewGroup arg0, int position) {
		if(type!=2) {
			DocPageFragment page = new DocPageFragment();
			page.setData(data.get(position));
				return page;
			} else {
				FullScreenFragment page = new FullScreenFragment();
				page.setData(data.get(position), type);
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
	
	public int getCurrentPosition() {
		return mCurrentPosition;
	}

	public void setOnItemChangeListener(OnItemChangeListener listener) {
		mOnItemChangeListener = listener;
	}

	public static interface OnItemChangeListener {
		public void onItemChange(int currentPosition);
	}

	

}
