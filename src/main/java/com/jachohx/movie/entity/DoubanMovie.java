package com.jachohx.movie.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jachohx.movie.dao.DoubanCastDAO;
import com.jachohx.movie.util.SpringUtils;

public class DoubanMovie extends DaoEntity {
	
	private int id;
	private String title;
	private String originalTitle;
	private String alt;
	private String imageSmall;
	private String imageMedium;
	private String imageLarge;
	private int rating;
	private String pubdates;
	private int year;
	private String subtype;
	private List<DoubanCast> casts = new ArrayList<DoubanCast>(4);;
	private List<DoubanCast> directors = new ArrayList<DoubanCast>(4);;
	private List<String> aka = new ArrayList<String>();
	private String summary;
	
	public DoubanMovie(){
	}
	public DoubanMovie(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getImageSmall() {
		return imageSmall;
	}
	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public String getImageMedium() {
		return imageMedium;
	}
	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium;
	}

	public String getImageLarge() {
		return imageLarge;
	}
	public void setImageLarge(String imageLarge) {
		this.imageLarge = imageLarge;
	}

	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getPubdates() {
		return pubdates;
	}
	public void setPubdates(String pubdates) {
		this.pubdates = pubdates;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public List<DoubanCast> getCasts() {
		return casts;
	}
	public void setCasts(List<DoubanCast> casts) {
		this.casts = casts;
	}
	public void setCastsStr(String casts) {
		if (casts == null || "".equals(casts))
			return;
		String[] castsArray = casts.split(SPLIT_MARK);
		DoubanCastDAO dao = (DoubanCastDAO)SpringUtils.getInstance().getBean("doubanCastDAO");
		for (String s : castsArray) {
			int _id = Integer.parseInt(s);
			if (_id <= 0)
				continue;
			addCases(dao.select(new DoubanCast(_id)));
		}
	}
	public String getCastsStr() {
		return StringUtils.join(castIds(casts), SPLIT_MARK);
	}
	public void addCases(DoubanCast cast) {
		casts.add(cast);
	}
	public List<DoubanCast> getDirectors() {
		return directors;
	}
	public void setDirectors(List<DoubanCast> directors) {
		this.directors = directors;
	}
	public void setDirectorsStr(String directors) {
		if (directors == null || "".equals(directors))
			return;
		String[] directorsArray = directors.split(SPLIT_MARK);
		DoubanCastDAO dao = (DoubanCastDAO)SpringUtils.getInstance().getBean("doubanCastDAO");
		for (String s : directorsArray) {
			int _id = Integer.parseInt(s);
			if (_id <= 0)
				continue;
			addDirectors(dao.select(new DoubanCast(_id)));
		}
	}
	public String getDirectorsStr() {
		return StringUtils.join(castIds(directors), SPLIT_MARK);
	}
	
	public void addDirectors(DoubanCast director) {
		directors.add(director);
	}
	public List<String> getAka() {
		return aka;
	}
	public void setAka(List<String> aka) {
		this.aka = aka;
	}
	final static String SPLIT_MARK = ","; 
	public String getAkaStr() {
		return StringUtils.join(aka, SPLIT_MARK);
	}
	public void setAkaStr(String aka) {
		String[] akas = aka.split(SPLIT_MARK);
		for (String a : akas) {
			addAka(a);
		}
	}
	public void addAka(String aka) {
		this.aka.add(aka);
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	final static List<Integer> DEFAULT_CAST_IDS = new ArrayList<Integer>(1);
	static {
		DEFAULT_CAST_IDS.add(0);
	}
	private List<Integer> castIds (List<DoubanCast> casts) {
		if (casts == null || casts.size() == 0)
			return DEFAULT_CAST_IDS;
		List<Integer> ids = new ArrayList<Integer>(casts.size());
		for (DoubanCast cast : casts) {
			ids.add(cast.getId());
		}
		return ids;
	}
	private List<String> pks;
	@Override
	public List<String> getPrimaryKey() {
		if (pks == null) {
			pks = new ArrayList<String>(1);
			pks.add("id");
		}
		return pks;
	}
	
	@Override
	public Map<String, Object[]> getFieldInfo() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("id", new Object[] { id, "setId", int.class });
		map.put("title", new Object[] { title, "setTitle", String.class });
		map.put("original_title", new Object[] { originalTitle, "setOriginalTitle", String.class });
		map.put("alt", new Object[] { alt, "setAlt", String.class });
		map.put("image_small", new Object[] { imageSmall, "setImageSmall", String.class });
		map.put("image_medium", new Object[] { imageMedium, "setImageMedium", String.class });
		map.put("image_large", new Object[] { imageLarge, "setImageLarge", String.class });
		map.put("rating", new Object[] { rating, "setRating", int.class });
		map.put("pubdates", new Object[] { pubdates, "setPubdates", String.class });
		map.put("year", new Object[] { year, "setYear", int.class });
		map.put("subtype", new Object[] { subtype, "setSubtype", String.class });
		map.put("casts", new Object[] { getCastsStr(), "setCastsStr", String.class });
		map.put("directors", new Object[] { getDirectorsStr(), "setDirectorsStr", String.class });
		map.put("aka", new Object[] { getAkaStr(), "setAkaStr", String.class });
		map.put("summary", new Object[] { summary, "setSummary", String.class });
		return map;
	}
	
	
	@Override
	public String toString() {
		String result = 
				"id:" + id + "\r\n" +
				"title:" + title + "\r\n" + 
				"englist:" + originalTitle + "\r\n" + 
				"alt:" + alt + "\r\n" + 
				"small:" + imageSmall + "\r\n" + 
				"medium:" + imageMedium + "\r\n" + 
				"large:" + imageLarge + "\r\n" + 
				"rating:" + rating + "\r\n" + 
				"pubdates:" + pubdates + "\r\n" + 
				"year:" + year + "\r\n" + 
				"subtype:" + subtype + "\r\n" + 
				"casts:" + "\r\n" ;
		for (DoubanCast cast : casts) {
			result += "\tid:" + cast.getId() + "\r\n";
			result += "\tname:" + cast.getName() + "\r\n";
			result += "\tid:" + cast.getAlt() + "\r\n";
			result += "\tavatar_small:" + cast.getAvatarSmall() + "\r\n";
			result += "\tavatar_medium:" + cast.getAvatarMedium() + "\r\n";
			result += "\tavatar_lager:" + cast.getAvatarLarge() + "\r\n";
		}
		result += "directors:" + "\r\n" ;
		for (DoubanCast director : directors) {
			result += "\tid:" + director.getId() + "\r\n";
			result += "\tname:" + director.getName() + "\r\n";
			result += "\tid:" + director.getAlt() + "\r\n";
			result += "\tavatar_small:" + director.getAvatarSmall() + "\r\n";
			result += "\tavatar_medium:" + director.getAvatarMedium() + "\r\n";
			result += "\tavatar_lager:" + director.getAvatarLarge() + "\r\n";
		}
		result += "aka:";
		for (String a : aka) {
			result += "\t" + a ;
		}
		result += "\r\n" + summary;
		return result;
	}

}
