package com.jachohx.movie.util;

import org.junit.Test;

public class UrlUtilsTest {
	@Test
	public void getParameter(){
		String url = "index.php?page=torrents&category=5";
		String parameter = "category";
		System.out.println(UrlUtils.getParameter(url, parameter));
	}
}
