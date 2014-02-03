package com.jachohx.movie.service;


import org.junit.Before;
import org.junit.Test;

import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.util.SpringUtils;

public class DoubanMovieServiceTest {
	DoubanMovieService service;
	@Before
	public void setUp(){
		service = (DoubanMovieService)SpringUtils.getInstance().getBean("doubanMovieService");
	}
	@Test
	public void getSubject() throws Exception {
//		DoubanMovie dm = service.getSubject("rush", 2013);
//		System.out.println(dm);
//		DoubanMovie dm = service.getSubject("Inland Empire", 2006);
//		System.out.println(dm);
		DoubanMovie dm = service.getSubject("Darkroom", 2013);
		System.out.println(dm);
	}
}
