package com.eas.elearning.app;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eas.elearning.R;
import com.eas.elearning.bean.DocPage;
import com.eas.elearning.util.HTMLUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class DocPageFragment extends Fragment {
	private DocPage page;
	private ImageView imgView;
	public DocPageFragment() {
		
	}
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = (View) inflater.inflate(R.layout.doc_page, container, false);
		imgView = (ImageView) root.findViewById(R.id.imgPage);
		

		if (page != null) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
			.bitmapConfig(Config.RGB_565)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.displayer(new FadeInBitmapDisplayer(150))
			.resetViewBeforeLoading(true)
			.showStubImage(R.drawable.slide_placeholder_small)
			.build();
			ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(page.getImgLink()), imgView, options);
		}
		return root;
	}
	
	public void setData(DocPage page){
		this.page = page;
	}

}
