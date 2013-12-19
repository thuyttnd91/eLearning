package com.eas.elearning.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Session.StatusCallback;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;

public class FbUtil {
	public static void ShareFacbook(final Activity activity,final String title,final String description,final String url,final String urlImage){
		Log.d("IMG: ", ""+urlImage);
		if(!checkSession()){
			Session.openActiveSession(activity, true, new StatusCallback() {
				
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					if(session!=null){
						if(state.isOpened()){
							Bundle params = new Bundle();
							params.putString("name", title);
							params.putString("description", description);
							params.putString("link", url);
							//params.putString("picture", "http://xemanh.net/images/anhdepvalentine/anh-dep-tinh-yeu-04.jpg");
							params.putString("picture", "http://10.0.1.100:81/travelguide/public/uploads/place/1376023056_1.jpg");
							WebDialog feedDialog = new WebDialog.FeedDialogBuilder(activity,
									session, params).build();
							feedDialog.show();
							//SharePlace(activity, title, description, url, urlImage);
						}
					}
					
				}
			});
		}
		else{
			SharePlace(activity, title, description, url, "http://10.0.1.100:81/travelguide/public/uploads/place/1376023056_1.jpg");
		}
	}
	public static void SharePlace(Activity ac,String title,String des,String url,String urlImage){
		if (FacebookDialog.canPresentShareDialog(ac,
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			Log.e("Utils Facebook:", "dialog1");
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					ac).setName(title).setDescription(des)
					.setPicture(urlImage).setLink(url).build();
			shareDialog.present();
		} else {
			Bundle params = new Bundle();
			params.putString("name", title);
			params.putString("description", des);
			params.putString("link", url);
			params.putString("picture", urlImage);
			WebDialog feedDialog = new WebDialog.FeedDialogBuilder(ac,
					Session.getActiveSession(), params).build();
			feedDialog.show();
		}
	}
	public static boolean checkSession() {
		Session session = Session.getActiveSession();
		if (session == null || (!session.isOpened()))
			return false;
		return true;
	}
	public static void share(Activity activity, String title, String desc,
			String url, String imgUrl) {
		if (FacebookDialog.canPresentShareDialog(activity,
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					activity).setName(title).setDescription(desc)
					.setPicture(imgUrl).setLink(url).build();
			shareDialog.present();
		} else {
			Bundle params = new Bundle();
			params.putString("name", title);
			params.putString("description", desc);
			params.putString("link", url);
			params.putString("picture", imgUrl);
			WebDialog feedDialog = new WebDialog.FeedDialogBuilder(activity,
					Session.getActiveSession(), params).build();
			feedDialog.show();
		}
	}
}
