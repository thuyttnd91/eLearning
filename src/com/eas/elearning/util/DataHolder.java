package com.eas.elearning.util;

import java.util.ArrayList;

import com.eas.elearning.bean.DocPage;
import com.eas.elearning.bean.Document;
import com.eas.elearning.bean.Post;

public class DataHolder {
	private static Document doc;
	private static ArrayList<DocPage> pages;
	private static Post post;
	public static final String DEVELOPER_KEY = "AIzaSyCFXWADvhtC4H5HcTpKqIgnWEv8ezsl-M4";

	public static Document getDoc() {
		return doc;
	}

	public static void setDoc(Document doc) {
		DataHolder.doc = doc;
	}

	public static ArrayList<DocPage> getPages() {
		return pages;
	}

	public static void setPages(ArrayList<DocPage> pages) {
		DataHolder.pages = pages;
	}

	public static Post getPost() {
		return post;
	}

	public static void setPost(Post post) {
		DataHolder.post = post;
	}

}
