package com.jachohx.movie.util;

import java.text.SimpleDateFormat;
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
}
