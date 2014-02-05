package com.jachohx.movie.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.service.PublicHDDoubanService;
import com.jachohx.movie.service.PublicHDService;
import com.jachohx.movie.util.SpringUtils;

public class PublicHDDoubanRunner implements IRunner {
	private static Logger log = Logger.getLogger(PublicHDRunner.class);
	PublicHDService publicHDService;
	PublicHDDoubanService publicHDDoubanService;
	
	public void setUp() {
		publicHDService = (PublicHDService)SpringUtils.getInstance().getBean("publicHDService");
		publicHDDoubanService = (PublicHDDoubanService)SpringUtils.getInstance().getBean("publicHDDoubanService");
    }
	public void tearDown(){
    }

	@Override
	public void run() throws Exception {
		setUp();
//		init();
		update();
	}
	
	public void init(){
		List<PublicHD> list = publicHDService.listAll();
		int size = 0;
		for (PublicHD phd : list) {
			try {
				publicHDDoubanService.create(phd);
				size ++;
			} catch (Exception e) {
			}
		}
		log.info("insert publichd douban size:" + size);
	}
	
	public void update(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.addAll(publicHDDoubanService.listAll(DoubanMovie.DEFAULT_STATUS));
		list.addAll(publicHDDoubanService.listAll(DoubanMovie.CRAW_ERROR_STATUS));
		list.addAll(publicHDDoubanService.listAll(DoubanMovie.NULL_STATUS));
		int size = 0;
		for (Map<String,Object> pd : list) {
			try {
				if(publicHDDoubanService.updateForTitle(pd)) {
					size ++;
				}
			} catch (Exception e) {
				log.error("params:" + pd + ",error:\r\n" + ExceptionUtils.getStackTrace(e));
			}
		}
		log.info("update publichd douban size:" + size);
	}
	
	public static void main(String[] args) throws Exception {
		PublicHDDoubanRunner runner = new PublicHDDoubanRunner();
		runner.run();
	}
}
