package com.eas.elearning;

import java.util.ArrayList;

import com.eas.elearning.bean.DrawerItem;

public class AppConfig {
	
	public static ArrayList<DrawerItem> generateMenuDrawerList(){	
		ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
		int index = 0;
		
		DrawerItem account = new DrawerItem(index, 0, "Tài khoản khách",DrawerItem.SPECIAL_ITEM, null);
		drawerItems.add(account);
		index++;
		//---------	
		DrawerItem login = new DrawerItem(index, R.drawable.ic_login, "Đăng nhập",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(login);
		index++;
		//---------
		DrawerItem register = new DrawerItem(index, R.drawable.ic_register, "Đăng ký tài khoản",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(register);
		index++;
		//---------
		DrawerItem favorite = new DrawerItem(index,
				R.drawable.ic_menu_favorite_ldpi, "Mục ưa thích",
				DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(favorite);
		index++;
		//---------
		DrawerItem download = new DrawerItem(index,
				R.drawable.ic_menu_download_hdpi, "Mục download",
				DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(download);
		index++;
		//---------
		DrawerItem discuss = new DrawerItem(index,
				R.drawable.ic_discuss, "Hỏi đáp",
				DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(discuss);
		index++;
		//---------
		DrawerItem newfeed = new DrawerItem(index, R.drawable.ic_home, "Trang chủ",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(newfeed);
		index++;
//		//---------
//		DrawerItem header = new DrawerItem(index, 0, "TỪ BAN BIÊN TẬP",DrawerItem.HEADER, null);
//		drawerItems.add(header);
//		index++;
//		//---------
//		DrawerItem newfeed = new DrawerItem(index, R.drawable.ic_new, "Mới cập nhật",DrawerItem.NORMAL_ITEM, null);
//		drawerItems.add(newfeed);
//		index++;
//		//---------
//		ArrayList<DrawerItem> subjectlist = new ArrayList<DrawerItem>();
//		DrawerItem daiso = new DrawerItem(0, R.drawable.ic_daiso, "Đại số",DrawerItem.GROUP_MEMBER, null);
//		subjectlist.add(daiso);
//		DrawerItem hinhhoc = new DrawerItem(1, R.drawable.ic_hinhhoc, "Hình học",DrawerItem.GROUP_MEMBER, null);
//		subjectlist.add(hinhhoc);
//		DrawerItem chemistry = new DrawerItem(2, R.drawable.ic_chemistry, "Hóa học",DrawerItem.GROUP_MEMBER, null);
//		subjectlist.add(chemistry);
//		DrawerItem followingsubject = new DrawerItem(index, R.drawable.ic_subject, "Theo môn học",DrawerItem.NORMAL_ITEM, subjectlist);
//		drawerItems.add(followingsubject);
//		index++;
//		//---------
		DrawerItem header2 = new DrawerItem(index, 0, "ỨNG DỤNG",DrawerItem.HEADER, null);
		drawerItems.add(header2);
		index++;
		//---------
		DrawerItem feedback = new DrawerItem(index, R.drawable.ic_feedback, "Gửi phản hồi",DrawerItem.NORMAL_ITEM,null);
		drawerItems.add(feedback);
		index++;
		//---------
		DrawerItem about = new DrawerItem(index, R.drawable.ic_about, "Giới thiệu",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(about);
		index++;
		//---------
		DrawerItem setting = new DrawerItem(index, R.drawable.ic_setting, "Tùy chọn",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(setting);
		index++;
		
//		ArrayList<DrawerItem> temp = new ArrayList<DrawerItem>();
//		DrawerItem feedback = new DrawerItem(0, R.drawable.ic_feedback, "Gửi phản hồi",null);
//		temp.add(feedback);
//		DrawerItem about = new DrawerItem(1, R.drawable.ic_about, "Giới thiệu", null);
//		newfeed.setChildren(temp);
//		drawerItems.set(2, newfeed);
//		temp.add(about);

	
		return drawerItems;
	}
	
	public static ArrayList<DrawerItem> generateMenuDrawerListAfterChange(){	
		ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
		int index = 0;
		
		DrawerItem account = new DrawerItem(index, 0, "Tài khoản khách",DrawerItem.SPECIAL_ITEM, null);
		drawerItems.add(account);
		index++;
		//---------	
		DrawerItem favorite = new DrawerItem(1,
				R.drawable.ic_menu_favorite_ldpi, "Mục ưa thích",
				DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(favorite);
		index++;
		//---------
		DrawerItem download = new DrawerItem(2,
				R.drawable.ic_menu_download_hdpi, "Mục download",
				DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(download);
		index++;
		//---------
		DrawerItem discuss = new DrawerItem(index,
				R.drawable.ic_discuss, "Hỏi đáp",
				DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(discuss);
		index++;
		//---------
		DrawerItem newfeed = new DrawerItem(index, R.drawable.ic_home, "Trang chủ",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(newfeed);
		index++;
//		//---------
//		DrawerItem header = new DrawerItem(index, 0, "TỪ BAN BIÊN TẬP",DrawerItem.HEADER, null);
//		drawerItems.add(header);
//		index++;
//		//---------
//		DrawerItem newfeed = new DrawerItem(index, R.drawable.ic_new, "Mới cập nhật",DrawerItem.NORMAL_ITEM, null);
//		drawerItems.add(newfeed);
//		index++;
//		//---------
//		ArrayList<DrawerItem> subjectlist = new ArrayList<DrawerItem>();
//		DrawerItem daiso = new DrawerItem(0, R.drawable.ic_daiso, "Đại số",DrawerItem.GROUP_MEMBER, null);
//		subjectlist.add(daiso);
//		DrawerItem hinhhoc = new DrawerItem(1, R.drawable.ic_hinhhoc, "Hình học",DrawerItem.GROUP_MEMBER, null);
//		subjectlist.add(hinhhoc);
//		DrawerItem chemistry = new DrawerItem(2, R.drawable.ic_chemistry, "Hóa học",DrawerItem.GROUP_MEMBER, null);
//		subjectlist.add(chemistry);
//		DrawerItem followingsubject = new DrawerItem(index, R.drawable.ic_subject, "Theo môn học",DrawerItem.NORMAL_ITEM, subjectlist);
//		drawerItems.add(followingsubject);
//		index++;
//		//---------
		DrawerItem header2 = new DrawerItem(index, 0, "ỨNG DỤNG",DrawerItem.HEADER, null);
		drawerItems.add(header2);
		index++;
		//---------
		DrawerItem feedback = new DrawerItem(index, R.drawable.ic_feedback, "Gửi phản hồi",DrawerItem.NORMAL_ITEM,null);
		drawerItems.add(feedback);
		index++;
		//---------
		DrawerItem about = new DrawerItem(index, R.drawable.ic_about, "Giới thiệu",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(about);
		index++;
		//---------
		DrawerItem setting = new DrawerItem(index, R.drawable.ic_setting, "Tùy chọn",DrawerItem.NORMAL_ITEM, null);
		drawerItems.add(setting);
		index++;
		
//		ArrayList<DrawerItem> temp = new ArrayList<DrawerItem>();
//		DrawerItem feedback = new DrawerItem(0, R.drawable.ic_feedback, "Gửi phản hồi",null);
//		temp.add(feedback);
//		DrawerItem about = new DrawerItem(1, R.drawable.ic_about, "Giới thiệu", null);
//		newfeed.setChildren(temp);
//		drawerItems.set(2, newfeed);
//		temp.add(about);

	
		return drawerItems;
	}
	
	public static ArrayList<String> generateAccountSettingItems(){
		ArrayList<String> items = new ArrayList<String>();
		items.add("Thông tin tài khoản");
		items.add("Cập nhật thông tin tài khoản");
		items.add("Đăng xuất");
		return items;
	}
	public static ArrayList<String> generateAccountSettingItems1(){
		ArrayList<String> items = new ArrayList<String>();
		items.add("Thông tin tài khoản");
		items.add("Cập nhật thông tin tài khoản");
		items.add("Đăng xuất");
		return items;
	}

}
