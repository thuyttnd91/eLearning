package com.eas.elearning.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;

public class FileUtil {
	public final static int SAVE_TO_INTERNAL = 1;
	public final static int SAVE_TO_SD_CARD = 2;
	public final static int UNABLE_TO_SAVE = 3;
	private static final String FOLDER_ROOT = "/com.eas.downloaded/";
	

	private static FileOutputStream saveFileToSDCard(String folderName,
			String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory())
				.append(FOLDER_ROOT).append(folderName).append("/");
		String path = builder.toString();
		File folderOutput = new File(path);
		if (!folderOutput.exists())
			folderOutput.mkdirs();
		try {
			File fileOutput = new File(path, fileName);
			if (fileOutput.exists())
				fileOutput.delete();
			fileOutput.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileOutput);
			return fos;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static FileOutputStream saveFileToInternalStorage(Context context,
			String folderName, String fileName) {
		String path = new StringBuilder().append(folderName).append("_")
				.append(fileName).toString();
		try {
			FileOutputStream fos = context.openFileOutput(path,
					Context.MODE_PRIVATE);
			return fos;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param fileName
	 *            must be format "id/fileName.bin"
	 * @return
	 * @author VIETBQ
	 */
	public static InputStream openFileFromSDCard(String Directory, String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory())
				.append(FOLDER_ROOT).append(Directory).append("/").append(fileName);
		String path = builder.toString();
		File file = new File(path);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			return bis;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Xoa file
	 * @param folder
	 * @param saveType
	 */
	public static boolean deleteAllFileInFolder(String folder,int saveType,Context context){
		boolean rs = false;
		switch (saveType) {
		case FileUtil.SAVE_TO_SD_CARD:
			rs = deleteFolderFromSDCard(folder);
			return rs;
		case FileUtil.SAVE_TO_INTERNAL:
			rs = deleteFromInternalStorage(folder,context);
			return rs;
		default:
			return rs;
		}
	}
	
	private static boolean deleteFolderFromSDCard(String folder){
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory())
				.append(FOLDER_ROOT).append(folder);
		File file = new File(builder.toString());
		String[] fileNames = file.list();
		for(String fileName : fileNames){
			File file2 = new File(builder.toString(), fileName);
			file2.delete();
		}
		return file.delete();
	}
	
	private static boolean deleteFromInternalStorage(String folder,Context context){
		String[] files = context.fileList();
		boolean rs = false;
		for(String storyName : files){
			if(storyName.startsWith(folder)){
				rs = context.deleteFile(storyName);
			}
		}
		return rs;
	}
	
	public static void deleteFolder(String folder){
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory())
				.append(FOLDER_ROOT).append(folder);
		File file = new File(builder.toString());
		file.delete();
	}
	
	/**
	 * 
	 * @param fileName
	 *            must be format "id_fileName.bin"
	 * @return
	 * @author VIETBQ
	 */
	public static InputStream openFileFromInternal(Context context, String folderStory,
			String fileName) {
		try {
			FileInputStream fis = context.openFileInput(folderStory+ "_" + fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			return bis;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static FileOutputStream saveFile(String folderStory,
			String fileName, int mSaveType, Context context) {
		switch (mSaveType) {
		case SAVE_TO_SD_CARD:
			return saveFileToSDCard(folderStory, fileName);
		case SAVE_TO_INTERNAL:
			return saveFileToInternalStorage(context, folderStory, fileName);
		default: return handleUnableSavingStatus();
		}
	}
	
	public static InputStream openFile(int mSaveType, String folderStory,String fileName,
			Context context) {
		if (mSaveType == SAVE_TO_SD_CARD) {
			return openFileFromSDCard(folderStory, fileName);
		} else {
			return openFileFromInternal(context, folderStory, fileName);
		}
	}
	
	
	/**
	 * Kiem tra xem truyen co da co hay chua
	 * @param folderStory : Ten folder (Id truyen)
	 * @param rs : Kieu luu
	 * @author VIETBQ
	 */
	public static int validStoryExist(String folderStory,Context context) {
		int placeBook = 0;
		if(checkStoryFromSDCard(folderStory)){
			placeBook = 2;
		}else if(checkStoryFromInternalStorage(folderStory,context)){
			placeBook = 1;
		}
		return placeBook;
	}
	/**
	 * Kiem tra xem folder co ton tai trong bo nho trong hay khong
	 * @param folderStory
	 * @param context
	 * @return
	 */
	public static boolean checkStoryFromInternalStorage(String folderStory,Context context) {
		String[] files = context.fileList();
		for(String fileName : files){
			if(fileName.startsWith(folderStory)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Kiem tra xem folder co ton tai trong the nho hay khong
	 * @param folderStory
	 * @return
	 */
	private static boolean checkStoryFromSDCard(String folderStory){
		String direction = new StringBuilder()
		.append(Environment.getExternalStorageDirectory())
		.append(FOLDER_ROOT).append(File.separator).append(folderStory)
		.toString();
		File file = new File(direction);
		return file.exists();
	}

	
	// su ly cac loi down 
	//copy to the true folder to complate download sonNH
	public static void copyToCompleteDownload(String fromFolder, String toFolder,
			int mSaveType, Context context){
		switch (mSaveType) {
		case SAVE_TO_INTERNAL:
			copyFileOfInternalStorage(fromFolder,
					toFolder, context);
			break;
		case SAVE_TO_SD_CARD:
			copyFileOfSDCard(fromFolder,
					toFolder, context);
			break;
			
		default:
			break;
		}
		
	}
	
	private static void copyFileOfInternalStorage( String src,
			String dst, Context context){
		String[] files = context.fileList();
		//context.
		for(String storyName : files){
			// lay duong dan thu muc chua file Truyen
			String filePath = context.getFilesDir().getAbsolutePath();
			if(storyName.contains(src)){
				// thay ten moi bang ten cu
				String newFile = dst + storyName.substring(storyName.indexOf("_"),
						storyName.length());
				String oldPart = filePath + "/" + storyName;
				File srcF = new File(oldPart);
				InputStream in;
				OutputStream out;
			    	try{
				        in = new FileInputStream(oldPart);
				        out = context.openFileOutput(newFile,
								Context.MODE_PRIVATE);
				        copyFile(in, out);
				        in.close();
				        in = null;
				        out.flush();
				        out.close();
				        out = null;
				        srcF.delete();
			    	}
			    	catch(IOException ex){
			    	}
			}
			
			
		}
	}
	
	private static boolean copyFileOfSDCard(String src,
			String dst, Context context){	
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory())
				.append(FOLDER_ROOT).append(src);
		
		//tao thu muc moi
//		File folder = new File(Environment.getExternalStorageDirectory() + "/"+dst);
//		boolean success = false;
//		success = folder.mkdir();
		
		
		StringBuilder builderDst = new StringBuilder();
		builderDst.append(Environment.getExternalStorageDirectory())
				.append(FOLDER_ROOT).append(dst);
		
		File oldNameFolder = new File(builder.toString());
		File newNameFolder = new File(builderDst.toString());
		return oldNameFolder.renameTo(newNameFolder);
		
//		File file = new File(builder.toString());
//		String[] fileNames = file.list();
//		String p = builder.toString();
//		for(String fileName : fileNames){
//				File srcF = new File(builder.toString(), fileName);
//				String path = builderDst.toString();
//				File fileOutput = new File(path, fileName);
//				
//				InputStream in;
//				OutputStream out;
//			    	try{
//			    		fileOutput.createNewFile();
//				        in = new FileInputStream(srcF);
//				        out = new FileOutputStream(fileOutput);
//				        copyFile(in, out);
//				        in.close();
//				        in = null;
//				        out.flush();
//				        out.close();
//				        out = null;
//			    	}
//			    	catch(IOException ex){
//			    	}
//				srcF.delete();
//		}
//		return file.delete();
		
		
	}
	
	public static void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	        out.write(buffer, 0, read);
	    }
	}
	
	public static FileOutputStream handleUnableSavingStatus(){
		
		
		return null;
	}
	
}
