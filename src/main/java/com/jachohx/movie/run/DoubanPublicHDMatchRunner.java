package com.jachohx.movie.run;

import java.util.List;

import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.service.DoubanPublicHDMatchService;
import com.jachohx.movie.util.SpringUtils;

public class DoubanPublicHDMatchRunner implements IRunner {
	DoubanPublicHDMatchService service;
	
	public void setUp() {
		service = (DoubanPublicHDMatchService)SpringUtils.getInstance().getBean("doubanPublicHDMatchService");
    }
	public void tearDown(){
    }

	@Override
	public void run() throws Exception {
		setUp();
		list();
	}
	
	public void list() throws Exception {
		int ymd = 20140204;
		List<PublicHDDouban> pds = (List<PublicHDDouban>)service.list(ymd).get("matched");
		System.out.println(pds.size());
		for (PublicHDDouban pd : pds) {
			DoubanMovie dm = pd.getDoubanMovie();
			PublicHD phd = pd.getPublicHD();
			System.out.println("movie:" + dm.getTitle() + "\tpublicHD:" + phd.getName());
		}
		List<PublicHDDouban> mms = (List<PublicHDDouban>)service.list(ymd).get("missMatch");
		for (PublicHDDouban pd : mms) {
			PublicHD phd = pd.getPublicHD();
			System.out.println("no match id:" + pd.getPhdId() + ",title:" + phd.getName() + ",name:" + pd.getName() + ",year:" + pd.getYear());
		}
	}
	
	public static void main(String[] args) throws Exception {
		DoubanPublicHDMatchRunner runner = new DoubanPublicHDMatchRunner();
		runner.run();
	}
}
