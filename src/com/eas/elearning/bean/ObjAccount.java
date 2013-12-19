package com.eas.elearning.bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ObjAccount implements Serializable {
	public int id;
	public String userName;
	public String passWord;
	public String fullName;
	public String email;
	public int gender;
	public String birthday;
	public int idClass;
	public int idSchool;
	public String nameSchool;
	public String imgAvatar;
	public int type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public int getIdClass() {
		return idClass;
	}
	public void setIdClass(int idClass) {
		this.idClass = idClass;
	}
	public int getIdSchool() {
		return idSchool;
	}
	public void setIdSchool(int idSchool) {
		this.idSchool = idSchool;
	}
	public String getImgAvatar() {
		return imgAvatar;
	}
	public void setImgAvatar(String imgAvatar) {
		this.imgAvatar = imgAvatar;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNameSchool() {
		return nameSchool;
	}
	public void setNameSchool(String nameSchool) {
		this.nameSchool = nameSchool;
	}
	
}
