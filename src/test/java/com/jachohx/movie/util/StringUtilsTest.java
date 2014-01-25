package com.jachohx.movie.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void title(){
		String title = "Sin Reaper 2012 1080p BluRay x264 RUSTED [PublicHD]";
//		title = title.replaceAll("(720|1080)p ", "");
		String _titleLower = title.toLowerCase();
		System.out.println(_titleLower);
		Pattern pattern = Pattern.compile("\\d+[p]{0,1}");
		Matcher matcher = pattern.matcher(_titleLower);
		String yearStr = null;
		String str = null;
		while(matcher.find()) {
			str = matcher.group();
			if ("720p".equals(str) | "1080p".equals(str))
				break;
			yearStr = str;
		}
		int yearIndex = title.indexOf(yearStr);
		String movieTitle = title.substring(0, yearIndex).trim();
		System.out.println(movieTitle + "\t" + yearStr);
	}
	
	@Test
	public void join(){
		String[] strs = new String[]{"123","234","345","456"};
		System.out.println(StringUtils.join(strs, ","));
		Integer[] ints = new Integer[]{123,234,345,456}; 
		System.out.println(StringUtils.join(ints, ","));
	}
}
