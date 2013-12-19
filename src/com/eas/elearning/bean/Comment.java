package com.eas.elearning.bean;

public class Comment {

	private int ID;
	private int IDUser;
	private String Contents;
	private String ImageLink;
	private String DateCreate;
	private String Fullname;
	private String AccLink;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int iD, String contents, String imageLink,
			String dateCreate, String fullname, String accLink) {
		super();
		ID = iD;
		Contents = contents;
		ImageLink = imageLink;
		DateCreate = dateCreate;
		Fullname = fullname;
		AccLink = accLink;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getIDUser() {
		return IDUser;
	}

	public void setIDUser(int iDUser) {
		IDUser = iDUser;
	}

	public String getContents() {
		return Contents;
	}

	public void setContents(String contents) {
		Contents = contents;
	}

	public String getImageLink() {
		return ImageLink;
	}

	public void setImageLink(String imageLink) {
		ImageLink = imageLink;
	}

	public String getDateCreate() {
		return DateCreate;
	}

	public void setDateCreate(String dateCreate) {
		DateCreate = dateCreate;
	}

	public String getFullname() {
		return Fullname;
	}

	public void setFullname(String fullname) {
		Fullname = fullname;
	}

	public String getAccLink() {
		return AccLink;
	}

	public void setAccLink(String accLink) {
		AccLink = accLink;
	}

}
