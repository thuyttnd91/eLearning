package com.eas.elearning;

import org.holoeverywhere.app.Application;

import android.graphics.Bitmap.Config;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class eLearning extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.bitmapConfig(Config.RGB_565)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.resetViewBeforeLoading(true)
		.showStubImage(R.drawable.place_holder)
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(options)
		.build();
		ImageLoader.getInstance().init(config);
		
	}
	
}
