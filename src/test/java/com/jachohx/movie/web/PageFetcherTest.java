package com.jachohx.movie.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jachohx.movie.entity.MovieInfo;
import com.jachohx.movie.entity.PublicHD;

public class PageFetcherTest{

	PublicHDPageFetcher fetcher;
	Properties prop;
	@Before
    public void setUp() {
		fetcher = new PublicHDPageFetcher();
		prop = new Properties();
		try {
			prop.load(new FileReader("config/publichd.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @After
    public void tearDown(){
    }
    
    /*
     * 1	
     */
//    @Test
    public void crawl() throws Exception {
    	String url = prop.getProperty("movies.list.url") + 1;
    	String model = prop.getProperty("movies.list.model");
    	System.out.println("url:" + url);
    	System.out.println("model:" + model);
    	List<PublicHD> results = fetcher.crawl(url, model);
    	System.out.println("size:" + results.size());
    	for (PublicHD phd : results){
    		printPublicHD(phd);
    	}
    }
    
    private void printPublicHD(PublicHD phd) {
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
    }
    
    @Test
    public void getMovieInfo(){
    	MovieInfo info =PublicHDPageFetcher.getMovieInfo("R I P D  2013 BDRip 1080p x264 DTS multi extras HighCode");
    	System.out.println("name:" + info.getName() + "\tyear:" + info.getYear());
    	Assert.assertEquals(info.getName(), "R I P D");
    	Assert.assertEquals(info.getYear(), 2013);
    	
    	info =PublicHDPageFetcher.getMovieInfo("R I P D 2013  2013 BDRip 1080p x264 DTS multi extras HighCode");
    	System.out.println("name:" + info.getName() + "\tyear:" + info.getYear());
    	Assert.assertEquals(info.getName(), "R I P D 2013");
    	Assert.assertEquals(info.getYear(), 2013);
    	
    	info =PublicHDPageFetcher.getMovieInfo("The Crash Reel LIMITED DOCU 720p BluRay x264 iMMORTALs [PublicHD]");
    	System.out.println("name:" + info.getName() + "\tyear:" + info.getYear());
    	Assert.assertEquals(info.getName(), "The Crash Reel LIMITED DOCU");
    	Assert.assertEquals(info.getYear(), 0);
    	
    	info =PublicHDPageFetcher.getMovieInfo("Pacific Rim [2013] 1080p BluRay QEBSx AAC51 FASM[ETRG]");
    	System.out.println("name:" + info.getName() + "\tyear:" + info.getYear());
    	Assert.assertEquals(info.getName(), "Pacific Rim");
    	Assert.assertEquals(info.getYear(), 2013);
    	
    	info =PublicHDPageFetcher.getMovieInfo("Casino 1995 900p BDRip x264 DTS Multisubs NoT PublicHD");
    	System.out.println("name:" + info.getName() + "\tyear:" + info.getYear());
    	Assert.assertEquals(info.getName(), "Casino");
    	Assert.assertEquals(info.getYear(), 1995);
    	
    	info =PublicHDPageFetcher.getMovieInfo("The Grey (RC) (2012) 1080p MKV AAC 15 MultiSubs SilenceM");
    	System.out.println("name:" + info.getName() + "\tyear:" + info.getYear());
    	Assert.assertEquals(info.getName(), "The Grey");
    	Assert.assertEquals(info.getYear(), 2012);
    	
    	info =PublicHDPageFetcher.getMovieInfo("2012 [2009] BluRay 1080p HD [aZZa]");
    	System.out.println("name:" + info.getName() + "\tyear:" + info.getYear());
    	Assert.assertEquals(info.getName(), "2012");
    	Assert.assertEquals(info.getYear(), 2009);
    }
    
    @Test
    public void filterTitle() {
    	String title = "Pacific Rim (2013) 1080p BluRay X264 QEBSx AAC51 FASM[ETRG]";
		System.out.println(title.replaceAll("\\[|\\(|\\]|\\)|(x264)|(X264)", ""));
	}
    
    @Test
    public void regex() {
    	Pattern pattern  = PublicHDPageFetcher.pattern;
    	String title = "R I P D  [2013] BDRip 1080p x264 DTS multi extras HighCode".toLowerCase();
    	Matcher matcher = pattern.matcher(title);
    	while(matcher.find()) {
    		String str = matcher.group();
    		str = str.replaceAll("\\[|\\]|\\(|\\)", "");
			System.out.println(str);
    	}
    }
}
