package com.eas.elearning.bean;

public class Document {

	private int ID;
	private String Title;
	private int DocumentType;
	private String DocumentLink;
	private String DateUpload;
	private int AccountID;
	private String FullName;
	private String AccountImgLink;
	private String ImageLink;
	private String ImageLinkThumb;
	private int SubjectID;
	private int Grade;
	private String Des;
	private int View;
	private int Download;
	private int Comments;
	private int status;
	private String date;

	public Document() {
	}

	public Document(String title, String thumbUrl, String des, int view,
			int download, int comments) {
		Title = title;
//		ThumbUrl = thumbUrl;
		Des = des;
		View = view;
		Download = download;
		Comments = comments;
	}




	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Document(int iD, String title, int documentType,
			String documentLink, String dateUpload, int accountID,
			String fullName, String accountImgLink, String imageLink,
			String imageLinkThumb, int subjectID, int grade, String des,
			int view, int download, int comments, int status) {
		ID = iD;
		Title = title;
		DocumentType = documentType;
		DocumentLink = documentLink;
		DateUpload = dateUpload;
		AccountID = accountID;
		FullName = fullName;
		AccountImgLink = accountImgLink;
		ImageLink = imageLink;
		ImageLinkThumb = imageLinkThumb;
		SubjectID = subjectID;
		Grade = grade;
		Des = des;
		View = view;
		Download = download;
		Comments = comments;
		this.status = status;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDes() {
		return Des;
	}

	public void setDes(String des) {
		Des = des;
	}

	public int getView() {
		return View;
	}

	public void setView(int view) {
		View = view;
	}

	public int getDownload() {
		return Download;
	}

	public void setDownload(int download) {
		Download = download;
	}

	public int getComments() {
		return Comments;
	}

	public void setComments(int comments) {
		Comments = comments;
	}

	public int getDocumentType() {
		return DocumentType;
	}

	public void setDocumentType(int documentType) {
		DocumentType = documentType;
	}

	public String getDocumentLink() {
		return DocumentLink;
	}

	public void setDocumentLink(String documentLink) {
		DocumentLink = documentLink;
	}


	public String getDateUpload() {
		return DateUpload;
	}

	public void setDateUpload(String dateUpload) {
		DateUpload = dateUpload;
	}

	public int getAccountID() {
		return AccountID;
	}

	public void setAccountID(int accountID) {
		AccountID = accountID;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getAccountImgLink() {
		return AccountImgLink;
	}

	public void setAccountImgLink(String accountImgLink) {
		AccountImgLink = accountImgLink;
	}

	public String getImageLink() {
		return ImageLink;
	}

	public void setImageLink(String imageLink) {
		ImageLink = imageLink;
	}

	public int getSubjectID() {
		return SubjectID;
	}

	public void setSubjectID(int subjectID) {
		SubjectID = subjectID;
	}

	public int getGrade() {
		return Grade;
	}

	public void setGrade(int grade) {
		Grade = grade;
	}

	public String getImageLinkThumb() {
		return ImageLinkThumb;
	}

	public void setImageLinkThumb(String imageLinkThumb) {
		ImageLinkThumb = imageLinkThumb;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setThumbUrl(String string) {
		// TODO Auto-generated method stub
		
	}

	
	

}
