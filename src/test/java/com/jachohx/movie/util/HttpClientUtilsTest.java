package com.jachohx.movie.util;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Test;

public class HttpClientUtilsTest {
	@Test
	public void getResponse() throws ParseException, IOException {
		String url = "http://api.douban.com/v2/movie/search?q=rush";
		System.out.println(HttpClientUtils.getResponse(url));
	}
}
