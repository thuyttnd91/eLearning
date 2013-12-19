package com.eas.elearning.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eas.elearning.bean.Answer;
import com.eas.elearning.bean.Comment;
import com.eas.elearning.bean.DocPage;
import com.eas.elearning.bean.Document;
import com.eas.elearning.bean.Post;

public class Parser {

	public static boolean checkSuccess(String response) {
		try {
			JSONObject resObject = new JSONObject(response);
			if (resObject.getString(NetworkUtility.RESULT_CODE).equals(
					NetworkUtility.RESULT_CODE_OK))
				return true;
			else
				return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Comment> getDocComment(String response) {

		ArrayList<Comment> result = new ArrayList<Comment>();
		JSONArray data = new JSONArray();

		try {
			data = new JSONObject(response).getJSONObject(
					NetworkUtility.COMMETNS).getJSONArray(
					NetworkUtility.LIST_COMMENT);
		} catch (JSONException e) {
			e.printStackTrace();
			return result;
		}

		for (int i = 0; i < data.length(); i++) {
			try {
				Comment cmt = new Comment();
				JSONObject comment = data.getJSONObject(i);
				cmt.setFullname(comment.getString(NetworkUtility.FULL_NAME));
				cmt.setContents(comment.getString(NetworkUtility.CONTENTS));
				cmt.setDateCreate(comment.getString(NetworkUtility.DATE_CREATE));
				cmt.setAccLink(comment.getString(NetworkUtility.AVATAR_LINK));
				cmt.setImageLink(comment.getString(NetworkUtility.IMG_LINK));
				result.add(cmt);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}

		return result;
	}
	
	public static ArrayList<Comment> getDocDetailComment(String response) {

		ArrayList<Comment> result = new ArrayList<Comment>();
		JSONArray data = new JSONArray();

		try {
			data = new JSONObject(response).getJSONObject(
					NetworkUtility.COMMETNS).getJSONArray(
					"_ListComment");
		} catch (JSONException e) {
			e.printStackTrace();
			return result;
		}

		for (int i = 0; i < data.length(); i++) {
			try {
				Comment cmt = new Comment();
				JSONObject comment = data.getJSONObject(i);
				cmt.setFullname(comment.getString(NetworkUtility.FULL_NAME));
				cmt.setContents(comment.getString(NetworkUtility.CONTENTS));
				cmt.setDateCreate(comment.getString(NetworkUtility.DATE_CREATE));
				cmt.setAccLink(comment.getString(NetworkUtility.AVATAR_LINK));
				cmt.setImageLink(comment.getString(NetworkUtility.IMG_LINK));
				result.add(cmt);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}

		return result;
	}

	public static ArrayList<DocPage> getDocSlide(String response) {

		ArrayList<DocPage> result = new ArrayList<DocPage>();
		JSONArray data = new JSONArray();

		try {
			data = new JSONObject(response)
					.getJSONObject(NetworkUtility.DETAIL).getJSONArray(
							"_Detail");
		} catch (JSONException e) {
			e.printStackTrace();
			return result;
		}

		for (int i = 0; i < data.length(); i++) {
			try {
				DocPage page = new DocPage();
				JSONObject docPage = data.getJSONObject(i);
				page.setImgLink(docPage.getString(NetworkUtility.IMG_LINK));
				page.setID(docPage.getInt(NetworkUtility.ID));
				result.add(page);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}

		return result;
	}

	public static String getTag(String response) {
		JSONObject jsonResponse = null;
		String tag = null;
		try {
			jsonResponse = new JSONObject(response);
			tag = jsonResponse.getString(NetworkUtility.TAG);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tag;

	}

	public static ArrayList<Document> getDocument(String response) {

		int ID;
		String Title;
		int DocumentType;
		String DocumentLink;
		String DateUpload;
		int AccountID;
		String FullName;
		String AccountImgLink;
		String ImageLink;
		String ImageLinkThumb;
		int SubjectID;
		int Grade;
		String Des;
		int View;
		int Download;
		int Comments;
		int status;

		ArrayList<Document> documents = new ArrayList<Document>();
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject(response);
			JSONObject jsonContent = jsonResponse
					.getJSONObject(NetworkUtility.MAIN_CONTENT);
			JSONArray jsonCore = jsonContent
					.getJSONArray(NetworkUtility.CONTENT_CORE);
			for (int i = 0; i < jsonCore.length(); i++) {
				JSONObject jsonElement = jsonCore.getJSONObject(i);
				ID = jsonElement.getInt(NetworkUtility.DOCUMENT_ID);
				Title = jsonElement.getString(NetworkUtility.DOCUMENT_TITLE);
				DocumentType = jsonElement.getInt(NetworkUtility.DOCUMENT_TYPE);
				DocumentLink = jsonElement
						.getString(NetworkUtility.DOCUMENT_LINK);
				DateUpload = jsonElement.getString(NetworkUtility.DATE_UPLOAD);
				AccountID = jsonElement.getInt(NetworkUtility.ACCOUNT_ID);
				AccountImgLink = jsonElement
						.getString(NetworkUtility.ACCOUNT_AVT);
				FullName = jsonElement
						.getString(NetworkUtility.ACCOUNT_USERNAME);
				View = jsonElement.getInt(NetworkUtility.VIEW_COUNT);
				Download = jsonElement.getInt(NetworkUtility.DOWNLOAD_COUNT);
				SubjectID = jsonElement.getInt(NetworkUtility.SUBJECT_ID);
				Grade = jsonElement.getInt(NetworkUtility.DOCUMENT_GRADE);
				Des = jsonElement
						.getString(NetworkUtility.DOCUMENT_DESCRIPTION);
				ImageLink = jsonElement.getString(NetworkUtility.IMG_LINK);
				ImageLinkThumb = jsonElement
						.getString(NetworkUtility.IMG_THUMB);
				Comments = jsonElement.getInt(NetworkUtility.COMMENT_COUNT);
				status = jsonElement.getInt(NetworkUtility.STATUS);

				Document aDoc = new Document(ID, Title, DocumentType,
						DocumentLink, DateUpload, AccountID, FullName,
						AccountImgLink, ImageLink, ImageLinkThumb, SubjectID,
						Grade, Des, View, Download, Comments, status);
				documents.add(aDoc);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return documents;
	}
	
	public static ArrayList<Document> getFarvDoc(String response) {

		int ID;
		String Title;
		int DocumentType;
		String DocumentLink;
		String DateUpload;
		int AccountID;
		String FullName;
		String AccountImgLink;
		String ImageLink;
		String ImageLinkThumb;
		int SubjectID;
		int Grade;
		String Des;
		int View;
		int Download;
		int Comments;
		int status;

		ArrayList<Document> documents = new ArrayList<Document>();
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject(response);
			JSONObject jsonContent = jsonResponse
					.getJSONObject("ListFavorite");
			JSONArray jsonCore = jsonContent
					.getJSONArray("_List");
			for (int i = 0; i < jsonCore.length(); i++) {
				JSONObject jsonElement = jsonCore.getJSONObject(i);
				ID = jsonElement.getInt(NetworkUtility.DOCUMENT_ID);
				Title = jsonElement.getString(NetworkUtility.DOCUMENT_TITLE);
				DocumentType = jsonElement.getInt(NetworkUtility.DOCUMENT_TYPE);
				DocumentLink = jsonElement
						.getString(NetworkUtility.DOCUMENT_LINK);
				DateUpload = jsonElement.getString(NetworkUtility.DATE_UPLOAD);
				AccountID = jsonElement.getInt(NetworkUtility.ACCOUNT_ID);
				AccountImgLink = jsonElement
						.getString(NetworkUtility.ACCOUNT_AVT);
				FullName = jsonElement
						.getString(NetworkUtility.ACCOUNT_USERNAME);
				View = jsonElement.getInt(NetworkUtility.VIEW_COUNT);
				Download = jsonElement.getInt(NetworkUtility.DOWNLOAD_COUNT);
				SubjectID = jsonElement.getInt(NetworkUtility.SUBJECT_ID);
				Grade = jsonElement.getInt(NetworkUtility.DOCUMENT_GRADE);
				Des = jsonElement
						.getString(NetworkUtility.DOCUMENT_DESCRIPTION);
				ImageLink = jsonElement.getString(NetworkUtility.IMG_LINK);
				ImageLinkThumb = jsonElement
						.getString(NetworkUtility.IMG_THUMB);
				Comments = jsonElement.getInt(NetworkUtility.COMMENT_COUNT);
				status = jsonElement.getInt(NetworkUtility.STATUS);

				Document aDoc = new Document(ID, Title, DocumentType,
						DocumentLink, DateUpload, AccountID, FullName,
						AccountImgLink, ImageLink, ImageLinkThumb, SubjectID,
						Grade, Des, View, Download, Comments, status);
				documents.add(aDoc);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return documents;
	}
	
	public static ArrayList<Post> getPosts(String response) {

		ArrayList<Post> result = new ArrayList<Post>();
		JSONArray data = new JSONArray();

		try {
			data = new JSONObject(response).getJSONObject("ListQuestion").getJSONArray("Question");
		} catch (JSONException e) {
			e.printStackTrace();
			return result;
		}

		for (int i = 0; i < data.length(); i++) {
			try {
				Post p	= new Post();
				JSONObject post = data.getJSONObject(i);
				
				p.setId(post.getInt(NetworkUtility.ID));
				p.setContent(post.getString(NetworkUtility.TITLE));
				p.setUserName(post.getString("AccountName"));
				p.setDateCreate(post.getString(NetworkUtility.DATE_CREATE));
				p.setAvatarLink(post.getString("AccountLink"));
				
				result.add(p);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}

		return result;
	}
	
	public static ArrayList<Answer> getAnswers(String response) {

		ArrayList<Answer> result = new ArrayList<Answer>();
		JSONArray data = new JSONArray();

		try {
			data = new JSONObject(response).getJSONObject("ListAnswer").getJSONArray("_ListAnswer");
		} catch (JSONException e) {
			e.printStackTrace();
			return result;
		}

		for (int i = 0; i < data.length(); i++) {
			try {
				Answer a	= new Answer();
				JSONObject ans = data.getJSONObject(i);
				
				a.setContent(ans.getString("Contents"));
				a.setUserName(ans.getString("AccountName"));
				a.setDateCreate(ans.getString(NetworkUtility.DATE_CREATE));
				a.setAvatarLink(ans.getString("AccountLink"));
				
				result.add(a);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}

		return result;
	}

	public static int getPageCount(String response){
		JSONObject jsonResponse = null;
		int pageCount = 0;
		try {
			jsonResponse = new JSONObject(response);
			pageCount = jsonResponse.getInt(NetworkUtility.PAGE_COUNT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pageCount;		
	}
	
	public static int getCurrentPage(String response){
		JSONObject jsonResponse = null;
		int currentPage = 0;
		try {
			jsonResponse = new JSONObject(response);
			currentPage = jsonResponse.getInt(NetworkUtility.CURRENT_PAGE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return currentPage;		
	}
}
