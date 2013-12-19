package com.eas.elearning.app;

import org.holoeverywhere.app.Activity;

import com.eas.elearning.R;

import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
	}
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.tvExit:
			finish();
			break;
		case R.id.btnOk:
			finish();
			break;
		case R.id.tvViewDes:
			
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
