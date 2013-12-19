package com.eas.elearning.app;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.ViewPager;

import android.os.Bundle;

import com.eas.elearning.R;
import com.eas.elearning.adapter.DocSlideAdapter;
import com.eas.elearning.util.DataHolder;

public class FullScreenSlideActivity extends Activity {
	ViewPager pagerSlide;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_full_screen);
		pagerSlide =  (ViewPager) findViewById(R.id.pagerSlide);
		pagerSlide.setAdapter(new DocSlideAdapter(getSupportFragmentManager(), DataHolder.getPages(),2));
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
