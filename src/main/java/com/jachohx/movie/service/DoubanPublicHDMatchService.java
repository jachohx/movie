package com.jachohx.movie.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jachohx.movie.dao.DoubanMovieDAO;
import com.jachohx.movie.dao.PublicHDDAO;
import com.jachohx.movie.dao.PublicHDDoubanDAO;
import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.util.TimeUtils;

public class DoubanPublicHDMatchService {
	private PublicHDDAO publicHDDAO;
	private PublicHDDoubanDAO publicHDDoubanDAO;
	private DoubanMovieDAO doubanMovieDAO;
	
	public void setPublicHDDAO(PublicHDDAO publicHDDAO) {
		this.publicHDDAO = publicHDDAO;
	}
	public void setPublicHDDoubanDAO(PublicHDDoubanDAO publicHDDoubanDAO) {
		this.publicHDDoubanDAO = publicHDDoubanDAO;
	}
	public void setDoubanMovieDAO(DoubanMovieDAO doubanMovieDAO) {
		this.doubanMovieDAO = doubanMovieDAO;
	}

	/**
	 * 得到ymd获取电影的影响。
	 * @param ymd
	 * @return key : matched(已匹配douban movie), missMatch(没有匹配douban movie)
	 */
	public Map<String, List<PublicHDDouban>> list(int ymd) {
		Date startDate = TimeUtils.getDate(ymd);
		Date endDate = TimeUtils.tomorrow(ymd);
		List<PublicHD> phds = publicHDDAO.list(startDate, endDate);
		List<PublicHDDouban> dphds = new ArrayList<PublicHDDouban>();
		List<PublicHDDouban> missMMs = new ArrayList<PublicHDDouban>();
		for (PublicHD phd : phds) {
			PublicHDDouban pd = publicHDDoubanDAO.select(new PublicHDDouban(phd.getId(), 0, "", 0));
			int doubanId = pd.getDoubanId();
			pd.setPublicHD(phd);
			if (doubanId < 0) {
				missMMs.add(pd);
				continue;
			}
			DoubanMovie dm = doubanMovieDAO.select(new DoubanMovie(doubanId));
			pd.setDoubanMovie(dm);
			dphds.add(pd);
		}
		Map<String, List<PublicHDDouban>> result = new HashMap<String, List<PublicHDDouban>>(2);
		result.put("matched", dphds);
		result.put("missMatch", missMMs);
		return result;
	}
}
