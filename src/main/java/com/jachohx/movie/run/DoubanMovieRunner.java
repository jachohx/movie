package com.jachohx.movie.run;

import java.util.List;

import org.apache.log4j.Logger;

import com.jachohx.movie.dao.PublicHDDoubanDAO;
import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.service.DoubanMovieService;
import com.jachohx.movie.util.SpringUtils;

public class DoubanMovieRunner implements IRunner {
	private static Logger log = Logger.getLogger(PublicHDRunner.class);
	DoubanMovieService service;
	PublicHDDoubanDAO publicHDDoubanDAO;
	final int ONE_FETCHER = 4;
	
	public void setUp() {
		service = (DoubanMovieService)SpringUtils.getInstance().getBean("doubanMovieService");
		publicHDDoubanDAO = (PublicHDDoubanDAO)SpringUtils.getInstance().getBean("publicHDDoubanDAO");
    }
	public void tearDown(){
    }

	@Override
	public void run() throws Exception {
		setUp();
		List<PublicHDDouban> list = publicHDDoubanDAO.pager(1, 4);
		for (PublicHDDouban phdd : list) {
			int did = DoubanMovie.CRAW_ERROR_STATUS;
			try {
				DoubanMovie dm = service.getSubject(phdd.getName(), phdd.getYear());
				did = dm.getId();
			} catch (Exception e) {
				log.error("phd name:" + phdd.getName() + ",year:" + phdd.getYear() + ",error" + e.getMessage());
			}
			phdd.setDoubanId(did);
			publicHDDoubanDAO.update(phdd);
			log.info("update publichd douban phdId:" + phdd.getPhdId() + ",doubanId:" + did);
		}
	}
	
	public static void main(String[] args) throws Exception {
		DoubanMovieRunner runner = new DoubanMovieRunner();
		runner.run();
	}
}
