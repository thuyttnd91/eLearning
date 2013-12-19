package com.eas.elearning.app;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

import com.eas.elearning.R;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class FragmentContent extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_content);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getSupportActionBar().setSubtitle("ELearning");
	}
}
