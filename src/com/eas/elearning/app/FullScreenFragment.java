package com.eas.elearning.app;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ImageButton;
import org.holoeverywhere.widget.TextView;

import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.eas.elearning.R;
import com.eas.elearning.app.CommentDialog.onCommentListener;
import com.eas.elearning.bean.DocPage;
import com.eas.elearning.business.BullShitDownloader;
import com.eas.elearning.business.ViewsTask;
import com.eas.elearning.customview.ZoomableImageView;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.ParamBuilder;
import com.eas.elearning.network.Parser;
import com.eas.elearning.network.RestConnector;
import com.eas.elearning.util.DataHolder;
import com.eas.elearning.util.HTMLUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class FullScreenFragment extends Fragment {
	private DocPage page;
	private String TAG="DocPageFragment";
	private ZoomableImageView imgView;
	private ImageButton btnComment;
	private TextView tvCmtCount;
	private ImageButton btnDownload;
	private ImageButton btnNormalScreen;
	private TextView tvPageIndex;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = (View) inflater.inflate(R.layout.doc_detail_fullscreen_activity, container, false);
		imgView =  (ZoomableImageView) root.findViewById(R.id.imgPage);
		btnComment = (ImageButton) root.findViewById(R.id.btnComment);
		tvCmtCount = (TextView) root.findViewById(R.id.tvComment);
		btnNormalScreen = (ImageButton) root.findViewById(R.id.btnFullScr);
		tvPageIndex = (TextView) root.findViewById(R.id.tvPageIndex);
		btnDownload = (ImageButton) root.findViewById(R.id.btnDownload);
		btnDownload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DataHolder.getDoc().setDownload(DataHolder.getDoc().getDownload() + 1);
				String link = HTMLUtil.getFullUrl(DataHolder.getDoc().getDocumentLink());
				Log.e("download", link);
				BullShitDownloader bullShitDownloader = new BullShitDownloader(getActivity(),link, DataHolder.getDoc().getTitle(),DataHolder.getDoc().getID());
				ViewsTask.AddDownloadCount(DataHolder.getDoc().getID(), new AsyncHttpResponseHandler());
			}
		});
		
		
		tvCmtCount.setText("");
		setCmtCount();
		
		btnComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommentDialog.show(getSupportActivity(), page.getID(), new onCommentListener() {
					
					@Override
					public void onSuccess() {
						
						tvCmtCount.setText((Integer.parseInt(tvCmtCount.getText().toString()) + 1) + "");
						
					}
				});
			}
		});
		
		btnNormalScreen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getSupportActivity().finish();
			}
		});

		/*if (page != null) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
			.bitmapConfig(Config.RGB_565)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.displayer(new FadeInBitmapDisplayer(150))
			.resetViewBeforeLoading(true)
			.showStubImage(R.drawable.slide_placeholder)
			.build();
			ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(page.getImgLink()), imgView, options);
		}*/
		return root;
	}
	
	@Override
	public void onResume() {
		super.onResume();
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
	}
	
	private void setCmtCount(){
		RestConnector.post(NetworkUtility.GET_DETAIL_COMMENT, ParamBuilder.BuildGetDetailCommentParams(page.getID()), new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				if (content==null) return;
				if (Parser.checkSuccess(content)) if (Parser.getDocDetailComment(content).size()>0){
					tvCmtCount.setText(Parser.getDocDetailComment(content).size() + "");
				}
			}
			
		});
	}
	
	public void setData(DocPage page, int type){
		this.page = page;
	}
	
	public ZoomableImageView getZoomableImageView(){
		return imgView;
	}

}
