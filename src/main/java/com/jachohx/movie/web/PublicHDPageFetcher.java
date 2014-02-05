package com.jachohx.movie.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.jachohx.movie.entity.MovieInfo;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.util.PropertiesUtils;
import com.jachohx.movie.util.TimeUtils;
import com.jachohx.movie.util.UrlUtils;

public class PublicHDPageFetcher implements IFetcher<PublicHD> {

	//1	index.php?page=torrents&category=5
	//2	Sin Reaper 2012 1080p BluRay x264 RUSTED [PublicHD]
	//3	
	//		http://www.clickansave.net/download/product_download.php?fileName=http://publichd.se/download.php?id=<tag:torrenthashish />&f=Sin.Reaper.2012.1080p.BluRay.x264-RUSTED [PublicHD].torrent&name=Sin.Reaper.2012.1080p.BluRay.x264-RUSTED [PublicHD]
	//		magnet:?xt=urn:btih:JT252WVOTYF4TRDF3KYMGOINLDB5B2NA&dn=Sin.Reaper.2012.1080p.BluRay.x264-RUSTED+[PublicHD]&tr=udp://tracker.publichd.eu/announce&tr=udp://tracker.1337x.org:80/announce&tr=udp://tracker.openbittorrent.com:80/announce&tr=http://fr33dom.h33t.com:3310/announce
	//		http://istoretor.com/t/4cf5dd5aae9e0bc9c465dab0c3390d58c3d0e9a0/Sin.Reaper.2012.1080p.BluRay.x264-RUSTED+%5BPublicHD%5D.torrent
	//4	11/12/2013
	//5	3
	//6	16
	//7	aoloffline
	//8	6.55 GB
	@Override
	public List<PublicHD> crawl(String url, String model) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = new WebClient(BrowserVersion.FIREFOX_17);
		WebClientOptions options = client.getOptions();
		options.setJavaScriptEnabled(false);
		HtmlPage page = null;
		page = client.getPage(url);
		List<?> list = page.getByXPath(model);
		int index = 1;
		PublicHD phd = new PublicHD();
		List<PublicHD> results = new ArrayList<PublicHD>();
		for (Object o : list) {
			switch (index) {
				case 1:	//category
					int category = 0;
					if (o instanceof HtmlTableCell) {
						HtmlTableCell htc = (HtmlTableCell)o;
						DomNode dn = htc.getFirstChild();
						if (dn instanceof HtmlAnchor) {
							HtmlAnchor a = (HtmlAnchor)dn;
							String dl = a.getHrefAttribute();
							String categoryStr = UrlUtils.getParameter(dl, "category");
							if (categoryStr != null) {
								category = Integer.parseInt(categoryStr);
							}
						}
						phd.setCategory(category);
					}
					break;
				case 2:	//name
					DomNode dn = (DomNode)o;
					DomNode _dn = null;
					while ((_dn = dn.getFirstChild()) != null) {
						if (_dn instanceof HtmlAnchor) {
							HtmlAnchor a = (HtmlAnchor)_dn;
							String dl = a.getHrefAttribute();
							phd.setUrl(dl);
							phd.setMd5(DigestUtils.md5Hex(dl));
						}
						dn = _dn;
					}
					phd.setName(dn.getTextContent());
					
					break;
				case 3:	//downloads
					if (o instanceof HtmlTableCell) {
						HtmlTableCell htc = (HtmlTableCell)o;
						for(DomElement de :htc.getChildElements()) {
							if (de instanceof HtmlAnchor) {
								HtmlAnchor a = (HtmlAnchor)de;
								String dl = a.getHrefAttribute();
								//下载地址
								if (dl.endsWith("torrent"))
									phd.setTorrent(dl);
								else if (dl.startsWith("magnet")) 
									phd.setMagnet(dl);
							}
						}
					}
					break;
				case 4:	//time
					phd.setAddDate(TimeUtils.getIntForDMY(((HtmlElement)o).getTextContent()));
					break;
				case 5:	//seeds
					phd.setSeeds(Integer.parseInt(((HtmlElement)o).getTextContent()));
					break;
				case 6: //leechers
					phd.setLeechers(Integer.parseInt(((HtmlElement)o).getTextContent()));
					break;
				case 7:	//uploads
					phd.setUploader(((HtmlElement)o).getTextContent());
					break;
				case 8:	//size
					phd.setSize(((HtmlElement)o).getTextContent());
					break;
			}
			index ++;
			if (index == 9) {
				index = 1;
				results.add(phd);
				phd = new PublicHD();
			}
		}
		return results;
	}
	
	static Pattern pattern = Pattern.compile("(720|1080)p|(([\\[|\\(]){0,1}\\d{4}([\\]|\\)]){0,1})");
	static String TITLE_FILTER_REGEX;
	
	private static String filterTitle(String title) {
		if ("".equals(TITLE_FILTER_REGEX)) {
			return title;
		}
		if (TITLE_FILTER_REGEX == null) {
			try {
				TITLE_FILTER_REGEX = PropertiesUtils.getProperty("config/publichd.properties", "movies.title.filter.regex");
			} catch (Exception e) {
				TITLE_FILTER_REGEX =  "";
			}
		}
		return title.replaceAll(TITLE_FILTER_REGEX, "");
	}
	/**
	 * 从标题里分析出名字与年份。标题是格式是 name + year + ...
	 * @param title
	 * @return
	 */
	public static MovieInfo getMovieInfo(String title) {
		MovieInfo info = null;
		
		String _titleLower = title.toLowerCase();
		Matcher matcher = pattern.matcher(_titleLower);
		String yearStr = null;
		String str = null;
		while(matcher.find()) {
			str = matcher.group();
			//720p,1080p前的也当作是片名
			if ("720p".equals(str) | "1080p".equals(str))
				break;
			str = str.replaceAll("\\[|\\]|\\(|\\)", "");
			//年份只能是4位数
			if (str.length() != 4)
				continue;
			yearStr = str;
		}
		int year = 0;
		String titleSpliteStr = null;
		if (yearStr == null) {//年份为0则看最后一个正则内容，比如720P,1080P
			titleSpliteStr = str;
			year = 0;
		} else {
			titleSpliteStr = yearStr;
			year = NumberUtils.toInt(yearStr, 0);
		}
		if (titleSpliteStr == null)
			return new MovieInfo(title, 0);
		int titleEndIndex = title.lastIndexOf(titleSpliteStr);
		if (titleEndIndex == -1)
			return new MovieInfo(title, 0);
		String movieTitle = filterTitle(title.substring(0, titleEndIndex)).trim();
		info = new MovieInfo();
		info.setName(movieTitle);
		info.setYear(year);
		return info;
	}

}
