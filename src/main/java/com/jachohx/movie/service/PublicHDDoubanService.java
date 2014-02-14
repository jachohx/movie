package com.jachohx.movie.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jachohx.movie.dao.PublicHDDoubanDAO;
import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.entity.MovieInfo;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.web.PublicHDPageFetcher;

public class PublicHDDoubanService {
	private static Logger log = Logger.getLogger(PublicHDDoubanService.class);
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
		PublicHDDouban pd = new PublicHDDouban(phd.getId(), DoubanMovie.DEFAULT_STATUS, name, year);
		PublicHDDouban _pd = dao.selectFromNameAndYear(pd.getName(), pd.getYear());
		if (_pd != null && _pd.getDoubanId() > 0)
			pd.setDoubanId(_pd.getDoubanId());
		return dao.insert(pd);
	}
	public List<Map<String,Object>> listAll(int doubanId) {
		return dao.listForOriTitle(doubanId);
	}
	/**
	 * 根据content的title分析出名字，如果有改变刚更新，douban_id设置为-4
	 * @param content
	 * @return
	 */
	public boolean updateForTitle(Map<String,Object> content) {
		String title = (String)content.get("title");
		String name = (String)content.get("name");
		int year = (Integer)content.get("year");
		MovieInfo mi = PublicHDPageFetcher.getMovieInfo(title);
		if (mi == null)
			return false;
		if (name.equals(mi.getName()) && year == mi.getYear())
			return false;
		int phdId = (Integer)content.get("phd_id");
		year = mi.getYear();
		name = mi.getName();
		log.info("update public douban title:" + title + ",name:" + name);
		return dao.update(new PublicHDDouban(phdId, DoubanMovie.TEMP_STATUS, name, year)) > 0;
	}
}
