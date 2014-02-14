package com.jachohx.movie.service;


import org.junit.Before;
import org.junit.Test;

import com.jachohx.movie.dao.PublicHDDoubanDAO;
//import com.jachohx.movie.entity.PublicHD;
//import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.util.SpringUtils;

public class PublicHDDoubanServiceTest {
	PublicHDDoubanService service;
	PublicHDDoubanDAO dao;
	@Before
	public void setUp(){
		service = (PublicHDDoubanService)SpringUtils.getInstance().getBean("publicHDDoubanService");
		dao = (PublicHDDoubanDAO)SpringUtils.getInstance().getBean("publicHDDoubanDAO");
	}
	@Test
	public void create() throws Exception {
//		dao.delete(new PublicHDDouban(524, 0, "", 0));
//		PublicHD phd = new PublicHD();
//		phd.setId(524);
//		phd.setName("The Frozen Ground 2013 BDRip 1080p x264 DTS HighCode");
//		System.out.println(service.create(phd));
//		dao.delete(new PublicHDDouban(523, 0, "", 0));
//		PublicHD phd = new PublicHD();
//		phd.setId(523);
//		phd.setName("Code Unknown Incomplete Tales Of Several Journeys 2000 720p BluRay DTS x264 PublicHD");
//		System.out.println(service.create(phd));
	}
}
