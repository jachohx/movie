package com.jachohx.movie.run;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jachohx.movie.dao.PublicHDDAO;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.service.PublicHDDoubanService;
import com.jachohx.movie.util.SpringUtils;
import com.jachohx.movie.web.PublicHDPageFetcher;

public class PublicHDRunner implements IRunner{
	private static Logger log = Logger.getLogger(PublicHDRunner.class);
	PublicHDPageFetcher fetcher;
	Properties prop;
//	PrintWriter writer;
	PublicHDDAO dao;
	PublicHDDoubanService publicHDDoubanService;
	
	int pageSize;
	int pageLimit = 0;
	boolean init = false;
	int initPageLimit = 0;
	
	@Override
	public void run(String[] args) throws Exception{
		setUp();
		if (init) initData();
		crawl();
		tearDown();
	}
	
	
	public void setUp() {
		fetcher = new PublicHDPageFetcher();
		prop = new Properties();
		try {
			prop.load(new FileReader("config/publichd.properties"));
			pageSize = Integer.parseInt(String.valueOf(prop.get("movies.list.size")));
			pageLimit = Integer.parseInt(String.valueOf(prop.get("movies.list.once.maxPageSize")));
			init = Boolean.parseBoolean(String.valueOf(prop.get("movies.list.init")));
			initPageLimit = Integer.parseInt(String.valueOf(prop.get("movies.list.init.pageLimit")));
			dao = (PublicHDDAO)SpringUtils.getInstance().getBean("publicHDDAO");
			publicHDDoubanService = (PublicHDDoubanService)SpringUtils.getInstance().getBean("publicHDDoubanService");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public void tearDown(){
    }
	/**
	 * 初始化网站的数据
	 * @throws Exception
	 */
	public void initData() throws Exception{
		List<PublicHD> allPhds = new ArrayList<PublicHD>();
		for (int pageNo = 1 ; pageNo < initPageLimit; pageNo++) {
			List<PublicHD> phds = crawl(pageNo);
			allPhds.addAll(phds);
			int size = phds.size();
			log.info("pageNo:" + pageNo + "\tsize:" + size);
			if (size != pageSize)
				break;
		}
		Collections.reverse(allPhds);
		insert2DB(allPhds);
	}
	
	/**
	 * 爬取网站数据
	 * @throws Exception
	 */
	public void crawl() throws Exception{
		List<PublicHD> allPhds = new ArrayList<PublicHD>();
		for (int pageNo = 1 ; pageNo < pageLimit; pageNo++) {
			List<PublicHD> phds = crawl(pageNo);
			int size = 0;
			for (PublicHD phd : phds){
				//如果此地址已爬取，则不需要再往下分页
				if (dao.isExist(phd)) {
					break;
				}
				size ++;
				allPhds.add(phd);
			}
			if (size != phds.size())
				break;
		}
		Collections.reverse(allPhds);
		insert2DB(allPhds);
	}
	
	/**
	 * 爬取某分页的数据
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public List<PublicHD> crawl(int pageNo) throws Exception {
    	String url = prop.getProperty("movies.list.url") + pageNo;
    	String model = prop.getProperty("movies.list.model");
    	log.info("url:" + url);
    	log.info("model:" + model);
    	List<PublicHD> results = fetcher.crawl(url, model);
    	log.info("size:" + results.size());
    	return results;
    }
	
	/**
	 * 把爬取的数据插入到数据库中
	 * @param phds
	 * @return
	 */
	protected int insert2DB(List<PublicHD> phds) {
		int size = 0;
		for (PublicHD phd : phds){ 
			if (dao.isExist(phd)) {
				log.info(phd.getUrl() + "\t is exist");
    			continue;
			}
			if (dao.create(phd) && publicHDDoubanService.create(phd)) {
				size ++;
			}
		}
		log.info("insert size:" + size);
		return size;
	}
	
}
