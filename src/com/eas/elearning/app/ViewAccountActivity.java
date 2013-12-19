package com.eas.elearning.app;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;

import com.eas.elearning.R;
import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.util.DateTimeFormat;
import com.eas.elearning.util.HTMLUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ViewAccountActivity extends Activity {
	private DBAccount mdb;
	ObjAccount obj;
	int id,gender;
	TextView tvname_account,tvname_class,tvname_school,tvfullname,tvbirthday,tvGender,tvEmail;
	ImageView imgAvatar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_account);
		
		init();
	}
	public void init(){
		mdb=new DBAccount(this);
		id=getIntent().getExtras().getInt("ID");
		obj=mdb.getAccount(id);
		if(obj!=null){
			tvname_account=(TextView) findViewById(R.id.tvname_account);
			tvname_class=(TextView) findViewById(R.id.tvname_class);
			tvname_school=(TextView) findViewById(R.id.tvname_school);
			tvfullname=(TextView) findViewById(R.id.tvfullname);
			tvbirthday=(TextView) findViewById(R.id.tvbirthday);
			tvGender=(TextView) findViewById(R.id.tvGender);
			tvEmail=(TextView) findViewById(R.id.tvEmail);
			imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
			
			DisplayImageOptions options = new DisplayImageOptions.Builder()
			.bitmapConfig(Config.RGB_565)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.resetViewBeforeLoading(true)
			.showStubImage(R.drawable.avatar_placeholder2)
			.build();
			
			ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(obj.getImgAvatar()), imgAvatar, options);
			
			tvname_account.setText(obj.getUserName());
			int id_class = obj.getIdClass();
			String name_class=mdb.getNameClassByID(id_class);
			tvname_class.setText(""+name_class);
			String nameSchool=mdb.getNameSchool(obj.getIdSchool());
			//ObjAccount obj2=mdb.Account(obj.getIdSchool());
			tvname_school.setText(nameSchool);
			tvfullname.setText(obj.getFullName());
			Log.e("VIEW ACCOUNT: ", ""+obj.getBirthday());
			String date=obj.getBirthday();
			String[] arrStr = date.split("-");
			String[] day=arrStr[2].split("T");
			Log.e("View Account: ", day[0]+ arrStr[1]+arrStr[2]);
			String birth=day[0]+"-"+arrStr[1]+"-"+arrStr[0];
			tvbirthday.setText(""+birth);
			//tvbirthday.setText(""+DateTimeFormat.format(obj.getBirthday(), DateTimeFormat.SHORT_VALUE));
			gender=obj.getGender();
			if(gender==1){
				tvGender.setText("Nam");
			}
			else{
				tvGender.setText("Nữ");
			}
			
			tvEmail.setText(obj.getEmail());
		}
		
		
	} 
	public void onClickBack(View v){
		setResult(1);
		finish();
	}
}
