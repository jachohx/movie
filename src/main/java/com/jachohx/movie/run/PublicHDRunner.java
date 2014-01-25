package com.jachohx.movie.run;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jachohx.movie.dao.PublicHDDAO;
import com.jachohx.movie.dao.PublicHDDoubanDAO;
import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.entity.MovieInfo;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.service.DoubanMovieService;
import com.jachohx.movie.util.SpringUtils;
import com.jachohx.movie.util.TimeUtils;
import com.jachohx.movie.web.PublicHDPageFetcher;

public class PublicHDRunner implements IRunner{
	private static Logger log = Logger.getLogger(PublicHDRunner.class);
	PublicHDPageFetcher fetcher;
	Properties prop;
	PrintWriter writer;
	PublicHDDAO dao;
	DoubanMovieService doubanMovieService;
	PublicHDDoubanDAO publicHDDoubanDAO;
	
	int pageSize;
	int pageLimit = 0;
	boolean init = false;
	int initPageLimit = 0;
	
	@Override
	public void run() throws Exception{
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
			writer = new PrintWriter(prop.getProperty("movies.out.dir") + TimeUtils.getYMDHM());
			pageSize = Integer.parseInt(String.valueOf(prop.get("movies.list.size")));
			pageLimit = Integer.parseInt(String.valueOf(prop.get("movies.list.once.maxPageSize")));
			init = Boolean.parseBoolean(String.valueOf(prop.get("movies.list.init")));
			initPageLimit = Integer.parseInt(String.valueOf(prop.get("movies.list.init.pageLimit")));
			dao = (PublicHDDAO)SpringUtils.getInstance().getBean("publicHDDAO");
			doubanMovieService = (DoubanMovieService)SpringUtils.getInstance().getBean("doubanMovieService");
			publicHDDoubanDAO = (PublicHDDoubanDAO)SpringUtils.getInstance().getBean("publicHDDoubanDAO");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public void tearDown(){
		writer.flush();
		writer.close();
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
//    	for (PublicHD phd : results){
//    		PublicHD _phd = dao.getPublicHD4Md5(phd.getMd5());
//    		if (_phd != null && _phd.getId() > 0) {
//    			System.out.println(phd.getUrl() + "\t is exist");
//    			continue;
//    		}
//    		dao.create(phd);
//    		outPublicHD(phd);
//    	}
    	return results;
    }
	
	/**
	 * 把爬取的数据插入到数据库中
	 * @param phds
	 * @return
	 */
	private int insert2DB(List<PublicHD> phds) {
		int size = 0;
		for (PublicHD phd : phds){ 
			if (dao.isExist(phd)) {
				log.info(phd.getUrl() + "\t is exist");
    			continue;
			}
			if (dao.create(phd)) {
				MovieInfo mi = PublicHDPageFetcher.getMovieInfo(phd.getName());
				if (mi == null)
					continue;
				String name = mi.getName();
				int year = mi.getYear();
				int doubanId = -1;
				try {
					DoubanMovie dm = doubanMovieService.getSubject(name, year);
					if (dm == null)
						doubanId = 0;
					else doubanId = dm.getId();
				} catch (Exception e) {
					log.error("id:" + phd.getId() + "error:" + e.getMessage());
				}
				publicHDDoubanDAO.insert(new PublicHDDouban(phd.getId(), doubanId, name, year));
				size ++;
			}
		}
		log.info("insert size:" + size);
		return size;
	}
	
//	private void outPublicHD(PublicHD phd) {
//		int index = 1;
//		writer.println(index++ + "\t" + phd.getCategory());
//		writer.println(index++ + "\t" + phd.getName());
//		writer.println(index++ + "\t" + phd.getUrl());
//		writer.println(index++ + "\t" + phd.getMd5());
//		writer.println(index++ + "\t" + phd.getMagnet());
//		writer.println(index++ + "\t" + phd.getTorrent());
//		writer.println(index++ + "\t" + phd.getAddDate());
//		writer.println(index++ + "\t" + phd.getSeeds());
//		writer.println(index++ + "\t" + phd.getLeechers());
//		writer.println(index++ + "\t" + phd.getUploader());
//		writer.println(index++ + "\t" + phd.getSize());
//		writer.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		writer.flush();
//	}
	
    /*private void printPublicHD(PublicHD phd) {
    	int index = 1;
		System.out.println(index++ + "\t" + phd.getCategory());
		System.out.println(index++ + "\t" + phd.getName());
		System.out.println(index++ + "\t" + phd.getMagnet());
		System.out.println(index++ + "\t" + phd.getTorrent());
		System.out.println(index++ + "\t" + phd.getAddDate());
		System.out.println(index++ + "\t" + phd.getSeeds());
		System.out.println(index++ + "\t" + phd.getLeechers());
		System.out.println(index++ + "\t" + phd.getUploader());
		System.out.println(index++ + "\t" + phd.getSize());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }*/
}
