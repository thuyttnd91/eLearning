package com.eas.elearning.util;

import android.os.Environment;
import android.os.StatFs;

public class MemoryUtil {

	private static long RETURN_MEGABYTES = 1048576;
	private static long RETURN_BYTES = 1024;
	public static int MB = 0;
	public static int BYTES = 1;
	private static long divideSize;

	public MemoryUtil(long returnType) {
		if (returnType == MB)
			divideSize = RETURN_MEGABYTES;
		else if (returnType == BYTES)
			divideSize = RETURN_BYTES;
	}

	public static boolean checkSDCardAvailable() {
		Boolean isSDPresent = android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);

		if (isSDPresent)
			return true;
		else
			return false;
	}

	public long getTotalSpaceInInternalStorage() {
		StatFs statFs = new StatFs(Environment.getRootDirectory()
				.getAbsolutePath());
		long Total = ((long) statFs.getBlockCount() * (long) statFs
				.getBlockSize()) / divideSize;
		return Total;
	}

	public long getFreeSpaceInInternalStorage() {
		StatFs statFs = new StatFs(Environment.getRootDirectory()
				.getAbsolutePath());
		long Free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize())
				/ divideSize;
		return Free;
	}

	public long getBusySpaceInInternalStorage() {
		StatFs statFs = new StatFs(Environment.getRootDirectory()
				.getAbsolutePath());
		long Total = ((long) statFs.getBlockCount() * (long) statFs
				.getBlockSize()) / divideSize;
		long Free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize())
				/ divideSize;
		long Busy = Total - Free;
		return Busy;
	}
	
	public long getTotalSpaceInSDCard() {
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
		long Total = ((long) statFs.getBlockCount() * (long) statFs
				.getBlockSize()) / divideSize;
		return Total;
	}

	public long getFreeSpaceInSDCard() {
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
		long Free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize())
				/ divideSize;
		return Free;
	}

	public long getBusySpaceInSDCard() {
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
		long Total = ((long) statFs.getBlockCount() * (long) statFs
				.getBlockSize()) / divideSize;
		long Free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize())
				/ divideSize;
		long Busy = Total - Free;
		return Busy;
	}

}
