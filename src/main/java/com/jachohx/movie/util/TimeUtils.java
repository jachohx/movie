package com.jachohx.movie.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	public static int getIntForDMY(String dmy) {
		String[] dmys = dmy.split("/");
		if (dmys.length != 3)
			return 0;
		int time = Integer.parseInt(dmys[2]) *10000 + Integer.parseInt(dmys[1]) * 100 + 
				Integer.parseInt(dmys[0]);
		return time;
	}
	
	public static long getYMDHM(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		return Long.parseLong(sdf.format(new Date()));
	}
	
	public static Date getDate(int ymd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(String.valueOf(ymd));
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Date tomorrow(int ymd) {
		Date ymdDate = getDate(ymd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(ymdDate);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}
}
