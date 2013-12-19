package com.eas.elearning.bean;

import java.util.ArrayList;

public class DrawerItem {
	
	public static int NORMAL_ITEM = 0;
	public static int GROUP_MEMBER = 1;
	public static int SPECIAL_ITEM = 2;
	public static int HEADER = 3;
	
	
	private int position;
	private int iconRes;
	private String title;
	private boolean hasChildren;
	private int type; //0:normal item; 1:group member; 2:special item; 3:header
	private boolean isChild;
	private ArrayList<DrawerItem> children;
	

	public DrawerItem(int position, int iconRes, String title, int type, ArrayList<DrawerItem> children) {
		this.position = position;
		this.iconRes = iconRes;
		this.title = title;
		this.type = type;
		this.children = children;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int id) {
		this.position = id;
	}

	public int getIconRes() {
		return iconRes;
	}

	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean hasChildren() {
		if(children !=null)
			return true;
		else return false;
	}

	public ArrayList<DrawerItem> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<DrawerItem> children) {
		this.children = children;
	}

	public boolean isChild() {
		if(type==NORMAL_ITEM) return true;
		else return false;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


}
