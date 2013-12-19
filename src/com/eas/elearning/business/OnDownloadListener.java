package com.eas.elearning.business;

public interface OnDownloadListener {
	
	public void onDownloadPrepare();
	
	public void onDownloadProgress(Integer... progress);
	
	public void onDownloadComplete();
}
