package com.eas.elearning.db;

import java.util.ArrayList;

import com.eas.elearning.bean.Document;
import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.bean.ObjArea;
import com.eas.elearning.bean.ObjClass;
import com.eas.elearning.bean.ObjSchool;
import com.eas.elearning.bean.ObjSubject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAccount extends SQLiteOpenHelper {
	private static final String DATABASE = "db";
	// table school
	public static final String TABLE_SCHOOL = "school";
	public static final String COLUMN_ID_SCHOOL = "idSchool";
	public static final String COLUMN_ID_SERVER_SCHOOL = "idServerSchool";
	public static final String COLUMN_NAME_SCHOOL = "nameSChool";
	public static final String COLUMN_ID_AREA= "areaID";
	public static final String COLUMN_DESCRIPTION= "description";
	//table account
	public static final String TABLE_ACCOUNT="account";
	public static final String COLUMN_ID_ACCOUNT="idAccount";
	public static final String COLUMN_ID_SERVER_ACCOUNT="idServerAccount";
	public static final String COLUMN_NAME_ACCOUNT="nameAccount";
	public static final String COLUMN_ID_SCHOOL_ACCOUNT = "idASchool";
	public static final String COLUMN_EMAIL="email";
	public static final String COLUMN_FULLNAME="fullname";
	public static final String COLUMN_GENDER="gender";
	public static final String COLUMN_BIRTHDAY="birthday";
	public static final String COLUMN_CLASS="class";
	public static final String COLUMN_AVATAR="avatar";
	//table document download
	public static final String TABLE_DOWNLOAD="download";
	public static final String COLUMN_ID_DOWNLOAD="idDownload";
	public static final String COLUMN_ID_DOCUMENT="idDocument";
	public static final String COLUMN_TIME_DOWNLOAD="datetime";
	public static final String COLUMN_PATH_DOCUMENT="pathDocument";
	//table document favorite
		public static final String TABLE_FAVORITE="favorite";
		public static final String COLUMN_ID_FAVORITE="idFavorite";
		public static final String COLUMN_ID_DOCUMENT_FAVORITE="idDocument";
		public static final String COLUMN_DOCUMENT_TITLE="documentTitle";
		public static final String COLUMN_DOCUMENT_TYPE="documentType";
		public static final String COLUMN_DOCUMENT_LINK="documentLink";
		public static final String COLUMN_DOCUMENT_DATE_UPLOAD="dateUpload";
		public static final String COLUMN_ACCOUNT_ID="accountID";
		public static final String COLUMN_USER_NAME="userName";
		public static final String COLUMN_AVATAR_URL="avatarUrl";
		public static final String COLUMN_DOCUMENT_IMG_LINK="imageLink";
		public static final String COLUMN_DOCUMENT_IMG_LINK_THUMB="imageLinkThumb";
		public static final String COLUMN_DOCUMENT_DESCRIPTION="description";
		public static final String COLUMN_DOCUMENT_VIEW="views";
		public static final String COLUMN_DOCUMENT_COMMENT="comments";
		public static final String COLUMN_DOCUMENT_DOWNLOAD="download";
		public static final String COLUMN_DOCUMENT_SUBJECT_ID="subjectID";
		public static final String COLUMN_TYPE="type";
		public static final String COLUMN_GRADE="grade";
	//table setting
		public static final String TABLE_SETTING="setting";
		public static final String COLUMN_ID_SETTING="idSetting";
		public static final String COLUMN_ID_SUBJECT="idSubject";
		//table Area
		public static final String TABLE_AREA="area";
		public static final String COLUMN_ID="id";
		public static final String COLUMN_ID_AREA_SERVER="idArea";
		public static final String COLUMN_NAME_AREA="nameArea";
		//table class
				public static final String TABLE_CLASS="class_table";
				public static final String COLUMN_ID_CLASS="idClass";
				public static final String COLUMN_ID_CLASS_SERVER="idClassServer";
				public static final String COLUMN_NAME_CLASS="nameClass";
	//table subject
				public static final String TABLE_SUBJECT="subject";
				public static final String COLUMN_ID_SUBJECT_SERVER="idSubjectServer";
				public static final String COLUMN_NAME_SUBJECT="nameSubject";
				private static final String COLUMN_DOCUMENT_GRADE = "grade";
		
	Context mContext=null;

	public DBAccount(Context ctx) {
		super(ctx, DATABASE, null, 2);
	}

	public DBAccount(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		onUpgrade(db, 1, 2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("SQLiteDatabase", "here");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOAD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
		
		db.execSQL("CREATE TABLE "
		+ TABLE_SCHOOL
		+ " ("+ COLUMN_ID_SCHOOL+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_SERVER_SCHOOL
		+ " INTEGER, "+COLUMN_NAME_SCHOOL + " TEXT, "+COLUMN_ID_AREA + " INTEGER, " +COLUMN_DESCRIPTION + " TEXT)");
db.execSQL("CREATE TABLE "
		+ TABLE_ACCOUNT
		+ " ("+ COLUMN_ID_ACCOUNT+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_SERVER_ACCOUNT
		+ " INTEGER, "+COLUMN_NAME_ACCOUNT + " TEXT, " +COLUMN_EMAIL + " TEXT , "+ COLUMN_FULLNAME + " TEXT, "
		+COLUMN_GENDER + " INTEGER, "+ COLUMN_BIRTHDAY + " TEXT, "+ COLUMN_CLASS + " INTEGER, "+ COLUMN_ID_SCHOOL_ACCOUNT + " INTEGER, "+COLUMN_AVATAR + " TEXT, FOREIGN KEY(idASchool) REFERENCES school(idServerSchool))");
db.execSQL("CREATE TABLE "
		+ TABLE_DOWNLOAD
		+ " ("+ COLUMN_ID_DOWNLOAD+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_ACCOUNT
		+ " INTEGER, "+ COLUMN_ID_DOCUMENT + " INTEGER, "+COLUMN_TIME_DOWNLOAD+ " TEXT, "+COLUMN_PATH_DOCUMENT + " TEXT)");
db.execSQL("CREATE TABLE "
		+ TABLE_FAVORITE
		+ " ("+ COLUMN_ID_FAVORITE+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_DOCUMENT_FAVORITE
		+ " INTEGER, "+ COLUMN_USER_NAME + " TEXT, " + COLUMN_AVATAR_URL + " TEXT, " + COLUMN_TYPE +" INTEGER, "+ COLUMN_DOCUMENT_TITLE + " TEXT, "+COLUMN_DOCUMENT_TYPE+ " INTEGER, "+ COLUMN_DOCUMENT_DATE_UPLOAD+ " TEXT, " +COLUMN_ACCOUNT_ID +" INTEGER, "+COLUMN_DOCUMENT_IMG_LINK +" TEXT, "+COLUMN_DOCUMENT_IMG_LINK_THUMB + " TEXT, " +COLUMN_DOCUMENT_DESCRIPTION + " TEXT, "+COLUMN_DOCUMENT_VIEW + " INTEGER, "+COLUMN_DOCUMENT_COMMENT + " INTEGER, "+COLUMN_DOCUMENT_DOWNLOAD + " INTEGER, "+COLUMN_DOCUMENT_SUBJECT_ID +" INTEGER,"+ COLUMN_GRADE + " TEXT, "+COLUMN_DOCUMENT_LINK + " TEXT)");
db.execSQL("CREATE TABLE "
		+ TABLE_SETTING
		+ " ("+ COLUMN_ID_SETTING+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_SUBJECT + " INTEGER)");
db.execSQL("CREATE TABLE "
		+ TABLE_AREA
		+ " ("+ COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_AREA_SERVER
		+ " INTEGER, "+ COLUMN_NAME_AREA + " TEXT)");
db.execSQL("CREATE TABLE "
		+ TABLE_CLASS
		+ " ("+ COLUMN_ID_CLASS+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_CLASS_SERVER
		+ " INTEGER, "+ COLUMN_NAME_CLASS + " TEXT)");
db.execSQL("CREATE TABLE "
		+ TABLE_SUBJECT
		+ " ("+ COLUMN_ID_SUBJECT+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+ COLUMN_ID_SUBJECT_SERVER
		+ " INTEGER, "+ COLUMN_NAME_SUBJECT + " TEXT)");
		
	}

	public void AddSchool(ObjSchool obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		// cv.put(COLUMN_ID_CATALOGY, obj.getId_catalogy());
		cv.put(COLUMN_ID_SERVER_SCHOOL, obj.getIdSchool());
		cv.put(COLUMN_NAME_SCHOOL, obj.getNameSchool());
		cv.put(COLUMN_ID_AREA, obj.getIdArea());
		cv.put(COLUMN_DESCRIPTION, obj.getDescription());
		db.insert(TABLE_SCHOOL, null, cv);
		db.close();

	}
	public void AddAccount(ObjAccount obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ID_SERVER_ACCOUNT, obj.getId());
		cv.put(COLUMN_NAME_ACCOUNT, obj.getUserName());
		cv.put(COLUMN_EMAIL, obj.getEmail());
		cv.put(COLUMN_FULLNAME, obj.getFullName());
		cv.put(COLUMN_GENDER, obj.getGender());
		cv.put(COLUMN_BIRTHDAY,""+obj.getBirthday());
		cv.put(COLUMN_CLASS,""+ obj.getIdClass());
		cv.put(COLUMN_ID_SCHOOL_ACCOUNT, obj.getIdSchool());
		cv.put(COLUMN_AVATAR, obj.getImgAvatar());
		db.insert(TABLE_ACCOUNT, null, cv);
		db.close();

	}
	public void AddDocumentDownloaded(Document obj){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_ID_ACCOUNT,obj.getAccountID());
		cv.put(COLUMN_PATH_DOCUMENT, obj.getDocumentLink());
		cv.put(COLUMN_ID_DOCUMENT, obj.getID());
		cv.put(COLUMN_TIME_DOWNLOAD, obj.getDate());
		db.insert(TABLE_DOWNLOAD, null, cv);
		db.close();
	}
	public void AddDocumentFavorite(Document obj){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_ID_DOCUMENT_FAVORITE, obj.getID());
		cv.put(COLUMN_USER_NAME, obj.getFullName());
		cv.put(COLUMN_AVATAR_URL, obj.getAccountImgLink());
		cv.put(COLUMN_DOCUMENT_TITLE, obj.getTitle());
		cv.put(COLUMN_DOCUMENT_DESCRIPTION, obj.getDes());
		cv.put(COLUMN_DOCUMENT_TYPE, obj.getDocumentType());
		cv.put(COLUMN_DOCUMENT_LINK, obj.getDocumentLink());
		cv.put(COLUMN_DOCUMENT_DATE_UPLOAD, obj.getDateUpload());
		cv.put(COLUMN_ACCOUNT_ID, obj.getAccountID());
		cv.put(COLUMN_DOCUMENT_IMG_LINK, obj.getImageLink());
		cv.put(COLUMN_DOCUMENT_IMG_LINK_THUMB, obj.getImageLinkThumb());
		cv.put(COLUMN_DOCUMENT_SUBJECT_ID, obj.getSubjectID());
		cv.put(COLUMN_DOCUMENT_GRADE, obj.getGrade());
		cv.put(COLUMN_DOCUMENT_VIEW, obj.getView());
		cv.put(COLUMN_DOCUMENT_DOWNLOAD, obj.getDownload());
		cv.put(COLUMN_DOCUMENT_COMMENT, obj.getComments());
		
		db.insert(TABLE_FAVORITE, null, cv);
		db.close();
	}
	
	public ArrayList<Document> getDocumentFavorite(int id){
		ArrayList<Document> arrDocument=new ArrayList<Document>();
		SQLiteDatabase db=this.getWritableDatabase();
		String query="SELECT * FROM "+TABLE_FAVORITE +" WHERE "+COLUMN_ACCOUNT_ID +" = "+id;
		Cursor cursor=db.rawQuery(query, null);
		if(cursor!=null || cursor.getCount()>0){
			if(cursor.moveToFirst()){
				do {
					int idDoc = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DOCUMENT_FAVORITE));
					String Title = cursor.getString(cursor.getColumnIndex(COLUMN_DOCUMENT_TITLE));
					int DocumentType = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCUMENT_TYPE)); 
					String DocumentLink = cursor.getString(cursor.getColumnIndex(COLUMN_DOCUMENT_LINK));
					String DateUpload = cursor.getString(cursor.getColumnIndex(COLUMN_DOCUMENT_DATE_UPLOAD));
					int AccountID = cursor.getInt(cursor.getColumnIndex(COLUMN_ACCOUNT_ID));
					String userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
					String avatarUrl = cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_URL));
					String ImageLink = cursor.getString(cursor.getColumnIndex(COLUMN_DOCUMENT_IMG_LINK));
					String ImageLinkThumb = cursor.getString(cursor.getColumnIndex(COLUMN_DOCUMENT_IMG_LINK_THUMB));
					int SubjectID = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCUMENT_SUBJECT_ID));
					int Grade = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCUMENT_GRADE));
					String Des = cursor.getString(cursor.getColumnIndex(COLUMN_DOCUMENT_DESCRIPTION));
					int View = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCUMENT_VIEW));
					int Download = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCUMENT_DOWNLOAD));
					int Comments = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCUMENT_COMMENT));				
				
					Document objDocument = new Document(idDoc, Title, DocumentType,
							DocumentLink, DateUpload, AccountID, "",
							"", ImageLink, ImageLinkThumb, SubjectID,
							Grade, Des, View, Download, Comments, 0);
					objDocument.setFullName(userName);
					objDocument.setAccountImgLink(avatarUrl);
					
					arrDocument.add(objDocument);
					
				} while (cursor.moveToNext());
			}
		}
		return arrDocument;
		
	}
	
	public ArrayList<Document> getDocumentDownloaded(int id){
		ArrayList<Document> arrDocument=new ArrayList<Document>();
		SQLiteDatabase db=this.getWritableDatabase();
		String query="SELECT * FROM "+TABLE_DOWNLOAD +" WHERE "+COLUMN_ID_ACCOUNT +" = "+id;
		Cursor cursor=db.rawQuery(query, null);
		if(cursor!=null || cursor.getCount()>0){
			if(cursor.moveToFirst()){
				do {
					Document objDocument=new Document();
					String document=cursor.getString(cursor.getColumnIndex(COLUMN_PATH_DOCUMENT));
					int idDocument=cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DOCUMENT));
					String date=cursor.getString(cursor.getColumnIndex(COLUMN_TIME_DOWNLOAD));
					objDocument.setID(idDocument);
					objDocument.setDocumentLink(document);
					objDocument.setDate(date);
					arrDocument.add(objDocument);
					
				} while (cursor.moveToNext());
			}
		}
		return arrDocument;
		
	}
	public boolean CheckIDSCHOOL(){
		SQLiteDatabase db = this.getWritableDatabase();
		String countQuery = "SELECT * FROM " + TABLE_SCHOOL ;
		Cursor cursor = db.rawQuery(countQuery, null);
		if(cursor.getCount()>0){
			return true;
		}
		return false;
}
	public boolean CheckIDAccount(int  id){
			SQLiteDatabase db = this.getWritableDatabase();
			String countQuery = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE "
					+ COLUMN_ID_SERVER_ACCOUNT + " = " + id ;
			Cursor cursor = db.rawQuery(countQuery, null);
			if(cursor.getCount()>0){
				return true;
			}
			return false;
	}
	public void UpdateAccount(ObjAccount obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_FULLNAME, obj.getFullName());
		cv.put(COLUMN_GENDER, obj.getGender());
		cv.put(COLUMN_BIRTHDAY,""+obj.getBirthday());
		cv.put(COLUMN_CLASS,""+ obj.getIdClass());
		cv.put(COLUMN_ID_SCHOOL_ACCOUNT, obj.getIdSchool());
		cv.put(COLUMN_AVATAR, obj.getImgAvatar());
		db.update(TABLE_ACCOUNT, cv, COLUMN_ID_SERVER_ACCOUNT + "=" + obj.getId(), null);
		db.close();

	}
	
	public ArrayList<ObjSchool> getIDSchool(int id) {
		ArrayList<ObjSchool> listSchool=new ArrayList<ObjSchool>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from school where areaID= "+id, null);
		if (cursor.getCount()!=0) {
			if (cursor.moveToFirst()) {
				do {
					ObjSchool obj=new ObjSchool();
					int idSchool=cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SERVER_SCHOOL));
					String nameSchool=cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCHOOL));
					obj.setIdSchool(idSchool);
					obj.setNameSchool(nameSchool);
					listSchool.add(obj);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		return listSchool;
	}
	public String getNameSchoolbyArea(int id,int area) {
		String nameSchool="";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from school where idServerSchool= "+id +" and areaID ="+area, null);
		if (cursor.getCount()!=0) {
			if (cursor.moveToFirst()) {
					 nameSchool=cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCHOOL));
				
			}
		}
		cursor.close();
		db.close();
		return nameSchool;
	}
	public String getNameSchool(int id) {
		String nameSchool="";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from school where idServerSchool= "+id , null);
		if (cursor.getCount()!=0) {
			if (cursor.moveToFirst()) {
					 nameSchool=cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCHOOL));
				
			}
		}
		cursor.close();
		db.close();
		return nameSchool;
	}
	public void deleteDocumentDownload(int id) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from download where idDocument ='" + id + "'");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteDocumentFavorite(int id) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from favorite where accountID ='" + id + "'");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ObjAccount getAccount(int id){
		ObjAccount obj=new ObjAccount();
		SQLiteDatabase db=this.getWritableDatabase();
		final String MY_QUERY = "SELECT * FROM account where idServerAccount=" + id;
		Cursor cursor=db.rawQuery(MY_QUERY , null);
		if(cursor.getCount()>0){
			if(cursor.moveToFirst()){
				obj.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SERVER_ACCOUNT)));
				obj.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ACCOUNT)));
				obj.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
				obj.setFullName(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));
				obj.setGender(cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER)));
				obj.setBirthday(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)));
				obj.setIdClass(cursor.getInt(cursor.getColumnIndex(COLUMN_CLASS)));
				obj.setIdSchool(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SCHOOL_ACCOUNT)));
				obj.setImgAvatar(cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR)));
			}
				
				
				
		}
		else{
			obj=null;
		}
		return obj;
	}
	public ObjAccount Account(int id){
		ObjAccount obj=new ObjAccount();
		SQLiteDatabase db=this.getWritableDatabase();
		final String MY_QUERY = "SELECT *,school.nameSChool FROM account a inner join school b on  a.idASchool=b.idServerSchool where idServerAccount=" + id;
		Cursor cursor=db.rawQuery(MY_QUERY , null);
		if(cursor.getCount()>0){
			if(cursor.moveToFirst()){
				obj.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SERVER_ACCOUNT)));
				obj.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ACCOUNT)));
				obj.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
				obj.setFullName(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));
				obj.setGender(cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER)));
				obj.setBirthday(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)));
				obj.setIdClass(cursor.getInt(cursor.getColumnIndex(COLUMN_CLASS)));
				obj.setIdSchool(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SCHOOL_ACCOUNT)));
				obj.setImgAvatar(cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR)));
			}
				
				
				
		}
		else{
			obj=null;
		}
		return obj;
	}
	public ArrayList<Integer> getIDSubject(){
		SQLiteDatabase db=this.getWritableDatabase();
		ArrayList<Integer> listSubject=new ArrayList<Integer>();
		String query="SELECT * FROM setting ";
		Cursor cursor=db.rawQuery(query, null);
		if(cursor.getCount()>0){
			if(cursor.moveToFirst()){
				int id=cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SUBJECT));
				listSubject.add(id);
			}
		}
		return listSubject;
		
	}
	
	public void addSetting(int idSubject){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_ID_SUBJECT, idSubject);
		db.insert(TABLE_SETTING, null, cv);
		db.close();
		
	}
	public void addArea(ObjArea obj){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_ID_AREA_SERVER, obj.getIdArea());
		cv.put(COLUMN_NAME_AREA, obj.getNameArea());
		db.insert(TABLE_AREA, null, cv);
		db.close();
	}
	public void addClass(ObjClass obj){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_ID_CLASS_SERVER, obj.getIdClass());
		cv.put(COLUMN_NAME_CLASS, obj.getNameClass());
		db.insert(TABLE_CLASS, null, cv);
		db.close();
	}
	public void addSubject(ObjSubject obj){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_ID_SUBJECT_SERVER, obj.getIdSubject());
		cv.put(COLUMN_NAME_SUBJECT, obj.getNameSubject());
		db.insert(TABLE_SUBJECT, null, cv);
		db.close();
	}
	public ArrayList<ObjArea> getArea(){
		ArrayList<ObjArea> arrArea=new ArrayList<ObjArea>();
		SQLiteDatabase db=this.getWritableDatabase();
		String query="Select * from area";
		Cursor cursor=db.rawQuery(query, null);
		try {
			if(cursor.getCount()>0){
				if(cursor.moveToFirst()){
					do {
						int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_AREA_SERVER));
						if (getIDSchool(id).size() < 1) continue;
						ObjArea obj=new ObjArea();
						obj.setIdArea(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_AREA_SERVER)));
						obj.setNameArea(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_AREA)));
						arrArea.add(obj);
						
					} while (cursor.moveToNext());
				}
			}
		} catch (CursorIndexOutOfBoundsException e) {
			
		}
		return arrArea;
	}
	public ArrayList<String> getIDClass(){
		ArrayList<String> arrClass=new ArrayList<String>();
		SQLiteDatabase db=this.getWritableDatabase();
		String query="Select * from class_table";
		Cursor cursor=db.rawQuery(query, null);
		try {
			if(cursor.getCount()>0){
				if(cursor.moveToFirst()){
					do {
						ObjClass obj=new ObjClass();
						obj.setIdClass(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CLASS_SERVER)));
						String name=cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLASS));
						arrClass.add(name);
						
					} while (cursor.moveToNext());
				}
			}
		} catch (CursorIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		return arrClass;
	}
	public String getNameClassByID(int id){
		String name_class="";
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM class_table WHERE idClassServer = '"+id+"'", null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				name_class=c.getString(c.getColumnIndex(COLUMN_NAME_CLASS));
			}
		}
		return name_class;
		
	}
	
	public ArrayList<ObjSubject> getSubject(){
		ArrayList<ObjSubject> arrSubject=new ArrayList<ObjSubject>();
		SQLiteDatabase db=this.getWritableDatabase();
		String query="Select * from subject";
		Cursor cursor=db.rawQuery(query, null);
		try {
			if(cursor.getCount()>0){
				if(cursor.moveToFirst()){
					do {
						ObjSubject obj=new ObjSubject();
						obj.setIdSubject(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SUBJECT_SERVER)));
						obj.setNameSubject(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUBJECT)));
						arrSubject.add(obj);
						
					} while (cursor.moveToNext());
				}
			}
		} catch (CursorIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		return arrSubject;
	}
	public void deleteIDSubject(int id) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from setting where idSubject ='" + id + "'");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean CheckIDSubject(int id){
		boolean isCheck=false;
		SQLiteDatabase db=this.getWritableDatabase();
		try {
			String query="Select * from setting where idSubject ='" + id;
			Cursor cursor=db.rawQuery(query, null);
			if(cursor.getCount()>0){
				return isCheck=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isCheck;
		
	}
	public ArrayList<Integer> getIDSubjectSetting(){
		ArrayList<Integer> arrSubject=new ArrayList<Integer>();
		SQLiteDatabase db=this.getWritableDatabase();
		String query="Select * from setting";
		Cursor cursor=db.rawQuery(query, null);
		try {
			if(cursor.getCount()>0){
				if(cursor.moveToFirst()){
					do {
						int id=cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SUBJECT));
						arrSubject.add(id);
						
					} while (cursor.moveToNext());
				}
			}
		} catch (CursorIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		return arrSubject;
	}
	public Integer getIdClassSetting(String name){
		int id=0;
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM class_table WHERE TRIM(nameClass) = '"+name.trim()+"'", null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				id=c.getInt(c.getColumnIndex(COLUMN_ID_CLASS_SERVER));
			}
		}
		return id;
	}
	public Integer getIdArea(String name){
		int id=0;
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM area WHERE TRIM(nameArea) = '"+name.trim()+"'", null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				id=c.getInt(c.getColumnIndex(COLUMN_ID_AREA_SERVER));
			}
		}
		return id;
	}
	public void deleteAllSetting(){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from setting");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteAllArea(){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from area");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteAllSchool(){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from school");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteAllClass(){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from class");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteAllSubject(){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from subject");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Integer getAreabySchool(int idSchool){
		int id=0;
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM school WHERE idServerSchool = '"+idSchool+"'", null);
			if(cur.getCount()>0){
				if(cur.moveToFirst()){
					id=cur.getInt(cur.getColumnIndex(COLUMN_ID_AREA));
				}
			}	
		return id;
		
				
	}
	public String getNameArea(int id){
		String name="";
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM area WHERE idArea = '"+id+"'", null);
			if(cur.getCount()>0){
				if(cur.moveToFirst()){
					name=cur.getString(cur.getColumnIndex(COLUMN_NAME_AREA));
				}
			}	
		return name;
		
				
	}
	public boolean CheckExistFavorite(int id,int idUser){
		boolean isCheck=false;
		SQLiteDatabase db=this.getWritableDatabase();
		String query="select * from favorite where idDocument= "+id + " and accountID= "+idUser;
		Cursor c=db.rawQuery(query, null);
		if(c.getCount()>0){
			return isCheck=true;
		}
		return isCheck;
	}
	public void DeleteFavorite(int id){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from favorite where idDocument ='" + id + "'");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
}
