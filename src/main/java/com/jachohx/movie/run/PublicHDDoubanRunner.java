package com.jachohx.movie.run;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.service.PublicHDDoubanService;
import com.jachohx.movie.service.PublicHDService;
import com.jachohx.movie.util.SpringUtils;

public class PublicHDDoubanRunner implements IRunner {
	private static Logger log = Logger.getLogger(PublicHDRunner.class);
	Properties prop;
	PublicHDService publicHDService;
	PublicHDDoubanService publicHDDoubanService;
	
	public void setUp() {
		prop = new Properties();
		try {
			prop.load(new FileReader("config/publichd.properties"));
			publicHDService = (PublicHDService)SpringUtils.getInstance().getBean("publicHDService");
			publicHDDoubanService = (PublicHDDoubanService)SpringUtils.getInstance().getBean("publicHDDoubanService");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public void tearDown(){
    }

	@Override
	public void run() throws Exception {
		setUp();
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
	
	public static void main(String[] args) throws Exception {
		PublicHDDoubanRunner runner = new PublicHDDoubanRunner();
		runner.run();
	}
}
