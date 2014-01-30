package com.jachohx.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jachohx.movie.dao.PublicHDDoubanDAO;
import com.jachohx.movie.entity.MovieInfo;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.web.PublicHDPageFetcher;

public class PublicHDDoubanService {
	@Autowired
	PublicHDDoubanDAO dao;
	public void setPublicHDDoubanDAO(PublicHDDoubanDAO dao) {
		this.dao = dao;
	}
	public List<PublicHDDouban> pager(int pageNo, int pageSize) {
		return dao.pager(pageNo, pageSize);
	}
	public boolean create(PublicHD phd) {
		MovieInfo mi = PublicHDPageFetcher.getMovieInfo(phd.getName());
		if (mi == null)
			return false;
		String name = mi.getName();
		int year = mi.getYear();
		return dao.insert(new PublicHDDouban(phd.getId(), DoubanMovieService.DEFAULT_STATUS, name, year));
	}
}
