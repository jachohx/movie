package com.jachohx.movie.web;

import java.util.List;

public interface IFetcher<T> {
	List<T> crawl(String url, String model) throws Exception;
}
