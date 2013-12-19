package com.eas.elearning.util;

import android.util.Log;

public class DateTimeFormat {

	public static final int LONG_VALUE = 1;
	public static final int SHORT_VALUE = 0;

	public static String format(String raw, int type) {
		String dateValue = "27/09/2013";
		String fullValue = "22h00 27/09/2013";
		String returnValue = "";
		try {
			if (raw.contains("+") == false) {
				String date = raw.substring(0, raw.indexOf("T"));
				String dateValues[] = date.split("-");
				dateValue = dateValues[2] + "/" + dateValues[1] + "/"
						+ dateValues[0];
				String time = raw.substring(raw.indexOf("T") + 1);
				String timeValues[] = time.split(":");
				String timeValue = timeValues[2] + "h:" + timeValues[1];
				if (type == 0)
					returnValue = dateValue;
				else {

					fullValue = timeValue + " " + dateValue;
					returnValue = fullValue;
				}
			} else {
				Log.d("datetime", "here");
				String date = raw.substring(0, raw.indexOf("T"));
				String dateValues[] = date.split("-");
				dateValue = dateValues[2] + "/" + dateValues[1] + "/"
						+ dateValues[0];
				String time = raw.substring(raw.indexOf("T") + 1,
						raw.indexOf("+") - 1);
				String timeValues[] = time.split(":");
				String timeValue = timeValues[2] + "h:" + timeValues[1];
				if (type == 0)
					returnValue = dateValue;
				else {
					fullValue = timeValue + " " + dateValue;
					returnValue = fullValue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}
