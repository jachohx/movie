package com.jachohx.movie.dao;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.util.SpringUtils;

public class PublicHDDoubanDAOTest {
	PublicHDDoubanDAO dao;
	@Before
    public void setUp() {
		dao = (PublicHDDoubanDAO)SpringUtils.getInstance().getBean("publicHDDoubanDAO");
	}
	@After
    public void tearDown(){
    }
	@Test
	public void insert(){
//		dao.insert(new PublicHDDouban(1, -1, "1", 1));
	}
	
	@Test
	public void page(){
		List<PublicHDDouban> list = dao.pager(1, 2);
		for (PublicHDDouban phdd : list) {
			System.out.println(phdd);
		}
	}
	
}
