package com.eas.elearning.business;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.Toast;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.eas.elearning.bean.Document;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.util.FileUtil;
import com.eas.elearning.util.HTMLUtil;
import com.eas.elearning.util.MemoryUtil;

public class BullShitDownloader {

	public static ArrayList<String> filePaths = new ArrayList<String>();

	// Progress Dialog
	private ProgressDialog pDialog;
	// Progress dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	private String path = "";
	private String filename = "";
	private DownloadFileFromURL downloader;
	private boolean isDownloading = false;
	DBAccount mdb;
	static int ID = 0;
	private static String title = "";

	private Context context;

	// File url to download
	// private static String file_url =
	// "http://api.androidhive.info/progressdialog/hive.jpg";

	private static String file_url = "";

	public BullShitDownloader(Context context, String fileURL, int id) {
		this.ID = id;
		this.context = context;
		downloader = new DownloadFileFromURL();
		downloader.execute(fileURL);
		mdb = new DBAccount(context);
		MyPreference.getInstance().Initialize(context);
	}

	public BullShitDownloader(Context context, String fileURL, String title,
			int id) {
		this.ID = id;
		this.title = title;
		this.context = context;
		downloader = new DownloadFileFromURL();
		downloader.execute(fileURL);
		mdb = new DBAccount(context);
		MyPreference.getInstance().Initialize(context);
	}

	private int getGoodSaveType(long lengthOfFile) {
		MemoryUtil memoryManager = new MemoryUtil(MemoryUtil.MB);
		if (MemoryUtil.checkSDCardAvailable()) {
			if (memoryManager.getFreeSpaceInSDCard() < (lengthOfFile / 1048576))
				return FileUtil.SAVE_TO_SD_CARD;
			else if (memoryManager.getFreeSpaceInInternalStorage() < (lengthOfFile / 1048576))
				return FileUtil.SAVE_TO_INTERNAL;
			else
				return FileUtil.UNABLE_TO_SAVE;
		} else
			return FileUtil.SAVE_TO_INTERNAL;
	}

	protected Dialog showDialog(int id) {
		switch (id) {
		case progress_bar_type:
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Downloading file. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					downloader.cancel(true);
				}
			});
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}

	/**
	 * Background Async Task to download file
	 * */
	class DownloadFileFromURL extends AsyncTask<String, Integer, Long> {
		/**
		 * Before starting background thread Show Progress Bar Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (NetworkUtility.checkNetworkState(context)) {
				showDialog(progress_bar_type);
			} else {
				NetworkUtility.showNoInternetToast(context);
				downloader.cancel(true);
			}
		}

		/**
		 * Downloading file in background thread
		 * */
		@Override
		protected Long doInBackground(String... f_url) {
			int count;
			long total = 0;
			
				try {
					URL url = new URL(f_url[0]);
					URLConnection conection = url.openConnection();
					conection.connect();
					// getting file length
					int lenghtOfFile = conection.getContentLength();
					// Log.i("header", ""+lenghtOfFile);

					// input stream to read file - with 8k buffer
					InputStream input = new BufferedInputStream(
							url.openStream(), 8192);

					// create a File object for the parent directory
					path = "/sdcard/eas_elearning_download/";
					// have the object build the directory structure, if needed.
					File folder = new File(path);
					folder.mkdirs();
					path = path + title
							+ HTMLUtil.getFileExtensionFromURL(f_url[0]);
					// create a File object for the output file
					File outputFile = new File(path);
					// now attach the OutputStream to the file object, instead
					// of a String representation
					OutputStream output = new FileOutputStream(outputFile);

					// String filename =
					// HTMLUtil.getFileNameInContentDispositionTag(conection.getHeaderField("Content-Disposition"));

					// filename = HTMLUtil.getFileNameFromURL(f_url[0]);
					// int saveType = getGoodSaveType(lenghtOfFile);
					// if(saveType==3) Toast.makeText(context, "Lỗi",
					// Toast.LENGTH_LONG).show();

					// path = "/sdcard/" + filename;
					// path = "/sdcard/" + title
					// +HTMLUtil.getFileExtensionFromURL(f_url[0]);
					// Output stream to write file
					// OutputStream output = new FileOutputStream(path);
					// OutputStream output = FileUtil.saveFile("", filename, 2,
					// context);
					// OutputStream output =
					// openFileOutput("downloadedfile.pdf",
					// getApplicationContext().MODE_PRIVATE);
					// Log.i("header", conection.getContentType());
					// Log.i("header",
					// conection.getHeaderField("Content-Disposition"));

					byte data[] = new byte[1024];
					
					while ((count = input.read(data)) != -1) {
						total += count;
						// publishing the progress....
						// After this onProgressUpdate will be called
						publishProgress((int) ((total * 100) / lenghtOfFile));

						// writing data to file
							output.write(data, 0, count);
						
					}

					// flushing output
					output.flush();

					// closing streams
					output.close();
					input.close();
					
					
				} catch (Exception e) {
					isDownloading = true;
					e.printStackTrace();
				}

			return  total;
		}

		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(Integer... progress) {
			// setting progress percentage
			pDialog.setProgress(progress[0]);
		}

		@Override
		protected void onCancelled() {
			if (isDownloading) {
				Toast.makeText(
						context,
						"Không thể tiếp tục download, vui lòng kiểm tra kết nối mạng hoặc bộ nhớ",
						Toast.LENGTH_SHORT).show();
				pDialog.dismiss();
				isDownloading = false;
			}
			super.onCancelled();
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(Long result) {
			// dismiss the dialog after the file was downloaded
			boolean allowSave = false;
			if(isDownloading == false){
			Toast.makeText(context, "Download successfull!", Toast.LENGTH_SHORT)
					.show();
			pDialog.dismiss();
				filePaths.add(path);
				String strID = MyPreference.getInstance().getString(
						"SETTING_ID");
				if (strID.equals("") == false) {
					ArrayList<Document> doc = mdb.getDocumentDownloaded(Integer.parseInt(strID));
					for (int i = 0; i < doc.size(); i++) {
						if(doc.get(i).getDocumentLink().equals(path)){
							allowSave = true;
						}
					}
					if(!allowSave){
					Document objDocument = new Document();
					objDocument.setAccountID(Integer.parseInt(strID));
					objDocument.setDocumentLink(path);
					objDocument.setID(ID);
					String mydate = java.text.DateFormat.getDateTimeInstance()
							.format(Calendar.getInstance().getTime());
					objDocument.setDate(mydate);

					mdb.AddDocumentDownloaded(objDocument);
					allowSave = false;
					}
				} else {
					ArrayList<Document> doc = mdb.getDocumentDownloaded(-9999);
					for (int i = 0; i < doc.size(); i++) {
						if(doc.get(i).getDocumentLink().equals(path)){
							allowSave = true;
						}
					}
					if(!allowSave){
					Document objDocument = new Document();
					objDocument.setAccountID(-9999);
					objDocument.setDocumentLink(path);
					objDocument.setID(ID);
					String mydate = java.text.DateFormat.getDateTimeInstance()
							.format(Calendar.getInstance().getTime());
					objDocument.setDate(mydate);

					mdb.AddDocumentDownloaded(objDocument);
					allowSave = false;
					}
				}
		} else {
			Toast.makeText(
					context,
					"Không thể tiếp tục download, vui lòng kiểm tra kết nối mạng hoặc bộ nhớ",
					Toast.LENGTH_SHORT).show();
			pDialog.dismiss();
			isDownloading = false;
		}
			}

	}
}
