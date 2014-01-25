package com.jachohx.movie.util;

public class UrlUtils {
	public static String getParameter(String url, String parameter){
		String result = null;
		int index = url.indexOf(parameter);
		if (index < 0)
			return result;
		index += parameter.length() + 1;
		int index2 = url.indexOf("&", index);
		if (index2 < 0)
			return url.substring(index);
		else return url.substring(index, index2);
	}
}
