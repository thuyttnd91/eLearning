package com.eas.elearning.app;

import com.eas.elearning.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CommentAcitivty extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_activity);
		ListView listView = (ListView) findViewById(R.id.lv_cm);
	}

}
