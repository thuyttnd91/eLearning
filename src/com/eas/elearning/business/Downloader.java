package com.eas.elearning.business;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.holoeverywhere.widget.Toast;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.eas.elearning.util.FileUtil;
import com.eas.elearning.util.HTMLUtil;
import com.eas.elearning.util.MemoryUtil;

public class Downloader {

	private String fileURL;
	private Context context;
	private OnDownloadListener callback;

	public Downloader(Context context, String URL) {
		fileURL = URL;
		this.context = context;
	};

	public void startDownload() {
		new DownloadTask().execute(fileURL);
	}
	
	public void startDownload(OnDownloadListener listener) {
		callback = listener;
		new DownloadTask().execute(fileURL);
	}

	public void startDownload(String savePath) {
		new DownloadTask().execute(fileURL);
	}

	public void cancelDownload() {

	}

	public void PauseDownload() {

	}

	private int getGoodSaveType(long lengthOfFile) {
		MemoryUtil memoryManager = new MemoryUtil(MemoryUtil.BYTES);
		if (MemoryUtil.checkSDCardAvailable()) {
			if (memoryManager.getFreeSpaceInSDCard() < lengthOfFile)
				return FileUtil.SAVE_TO_SD_CARD;
			else if (memoryManager.getFreeSpaceInInternalStorage() < lengthOfFile)
				return FileUtil.SAVE_TO_INTERNAL;
			else
				return FileUtil.UNABLE_TO_SAVE;
		} else
			return FileUtil.SAVE_TO_INTERNAL;
	}

	private class DownloadTask extends AsyncTask<String, Integer, Long> {

		@Override
		protected void onPreExecute() {
			if(callback != null) callback.onDownloadPrepare();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(String... fileUrl) {
			int count;
			long total = 0;
			try {
				URL url = new URL(fileURL);
				  URLConnection conection = url.openConnection();
		            conection.connect();
		            // getting file length
		            int lenghtOfFile = conection.getContentLength();
//		            Log.i("header", ""+lenghtOfFile);
//		            Log.i("header", conection.getContentType());
//		            Log.i("header", conection.getHeaderField("Content-Disposition"));
//		            String filename = HTMLUtil.getFileNameInContentDispositionTag(conection.getHeaderField("Content-Disposition"));
		            
		            String filename = HTMLUtil.getFileNameFromURL(fileURL);
		            
		            int mSaveType = getGoodSaveType(lenghtOfFile);
		           
		            if (mSaveType == FileUtil.UNABLE_TO_SAVE) {
						Toast.makeText(context, "Bộ nhớ không đủ dung lượng cho file tải", Toast.LENGTH_LONG).show();
						return total;
					}

		            // input stream to read file - with 8k buffer
		            InputStream input = new BufferedInputStream(url.openStream(), 8192);                  
		            
		            // Output stream to write file
		            OutputStream output = FileUtil.saveFile("", filename, mSaveType, context);
		            
		            byte data[] = new byte[1024];

		            

		            while ((count = input.read(data)) != -1) {
		                total += count;
		                // publishing the progress....
		                // After this onProgressUpdate will be called
		                publishProgress((int)((total*100)/lenghtOfFile));
		                
		                // writing data to file
		                output.write(data, 0, count);
		            }

		            // flushing output
		            output.flush();
		            
		            // closing streams
		            output.close();
		            input.close();
		            
		        } catch (Exception e) {
		        	Log.e("Error: ", e.getMessage());
		        }
		        
		        return total;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			if(callback != null) callback.onDownloadProgress(progress);
			super.onProgressUpdate(progress);
		}

		@Override
		protected void onPostExecute(Long result) {
			if(callback != null) callback.onDownloadComplete();
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

	}

}
