package com.jachohx.movie.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	public static String getResponse(String url) throws ParseException, IOException {
		url = url.replace(" ", "%20");
		HttpClientBuilder hcb = HttpClientBuilder.create();
		CloseableHttpClient client = hcb.build();
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		String content = EntityUtils.toString(response.getEntity());
		client.close();
		return content;
	}
}
