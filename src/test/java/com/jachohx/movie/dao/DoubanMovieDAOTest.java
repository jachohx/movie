package com.jachohx.movie.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.util.SpringUtils;

public class DoubanMovieDAOTest {
	DoubanMovieDAO dao;
	@Before
    public void setUp() {
		dao = (DoubanMovieDAO)SpringUtils.getInstance().getBean("doubanMovieDAO");
	}
	@After
    public void tearDown(){
    }
	
	
	@Test
	public void selectFromTitleAndYear() {
		DoubanMovie dm = dao.selectFromTitleAndYear("Inland Empire", 2006);
		System.out.println(dm);
	}
	
}
