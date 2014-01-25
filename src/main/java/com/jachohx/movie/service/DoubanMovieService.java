package com.jachohx.movie.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jachohx.movie.dao.DoubanCastDAO;
import com.jachohx.movie.dao.DoubanMovieDAO;
import com.jachohx.movie.entity.DoubanCast;
import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.util.HttpClientUtils;

public class DoubanMovieService {
	
	final static String SUBJECTS_ID = "id";
	final static String SUBJECTS_TITLE = "title";
	final static String SUBJECTS_ENGLISH_TITLE = "original_title";
	final static String SUBJECTS_ALT = "alt";
	final static String SUBJECTS_IMAGE = "images";
	final static String SUBJECTS_IMAGE_SMALL = "small";
	final static String SUBJECTS_IMAGE_MEDIUM = "medium";
	final static String SUBJECTS_IMAGE_LARGE = "large";
	final static String SUBJECTS_RATING = "rating";
	final static String SUBJECTS_RATING_AVERAGE = "average";
	final static String SUBJECTS_PUBDATAS = "pubdatas";
	final static String SUBJECTS_YEAR = "year";
	final static String SUBJECTS_SUBTYPE = "subtype";
	//casts演员
	final static String SUBJECTS_CAST = "casts";
	final static String SUBJECTS_CAST_AVATARS = "avatars";
	final static String SUBJECTS_CAST_AVATARS_SMALL = "small";
	final static String SUBJECTS_CAST_AVATARS_MEDIUM = "medium";
	final static String SUBJECTS_CAST_AVATARS_LARGE = "large";
	final static String SUBJECTS_CAST_ALT = "alt";
	final static String SUBJECTS_CAST_ID = "id";
	final static String SUBJECTS_CAST_NAME = "name";
	//directors导演
	final static String SUBJECTS_DIRECTOR = "directors";
	final static String SUBJECTS_DIRECTOR_AVATARS = "avatars";
	final static String SUBJECTS_DIRECTOR_AVATARS_SMALL = "small";
	final static String SUBJECTS_DIRECTOR_AVATARS_MEDIUM = "medium";
	final static String SUBJECTS_DIRECTOR_AVATARS_LARGE = "large";
	final static String SUBJECTS_DIRECTOR_ALT = "alt";
	final static String SUBJECTS_DIRECTOR_ID = "id";
	final static String SUBJECTS_DIRECTOR_NAME = "name";	
	final static String SUBJECTS_AKA = "aka";
	final static String SUBJECTS_SUMMARY = "summary";
	
	//database
	final static String SUBJECTS_ENGLISH_TITLE_COLUMN = "original_title";
	final static String SUBJECTS_YEAR_COLUMN = "year";
	
	static Properties prop = new Properties();;
	static String MOVIE_SEARCH_API; 
	static String MOVIE_SUBJECT_API; 
	private DoubanMovieDAO doubanMovieDAO;
	private DoubanCastDAO doubanCastDAO;
	public void init() throws IOException {
		prop.load(new FileReader("config/douban.progerties"));
		MOVIE_SEARCH_API = prop.getProperty("douban.movie.search.api");
		MOVIE_SUBJECT_API = prop.getProperty("douban.movie.subject.api");
	}
	
	public void setDoubanMovieDAO(DoubanMovieDAO doubanMovieDAO) {
		this.doubanMovieDAO = doubanMovieDAO;
	}
	public void setDoubanCastDAO(DoubanCastDAO doubanCastDAO) {
		this.doubanCastDAO = doubanCastDAO;
	}

	/**
	 * get douban movie subject
	 * @param title
	 * @param year
	 * @return
	 * @throws IOException
	 */
	public DoubanMovie getSubject(String title, int year) throws IOException {
		DoubanMovie dm = getSujectFromDB(title, year);
		//是否已存在数据库里，如果存在就不需要再查询API
		if (dm != null)
			return dm;
		
		String jsonStr = HttpClientUtils.getResponse(MOVIE_SEARCH_API + title);
		JSONObject json = new JSONObject(jsonStr);
		JSONArray jsonArray = json.getJSONArray("subjects");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jo = jsonArray.getJSONObject(i);
			dm = jsonToDoubanMovie(jo);
			if (title.equalsIgnoreCase(dm.getOriginalTitle()) && dm.getYear() == year){
				getSubject(dm);
				save(dm);
				return dm;
			}
		}
		return null;
	}
	
	private DoubanMovie getSubject(DoubanMovie dm) throws IOException {
		String jsonStr = HttpClientUtils.getResponse(MOVIE_SUBJECT_API + dm.getId());
		JSONObject json = new JSONObject(jsonStr);
		//cast
		JSONArray ja = json.getJSONArray(SUBJECTS_CAST);
		for (int i = 0; i < ja.length(); i++) {
			DoubanCast cast = new DoubanCast();
			JSONObject jo = ja.getJSONObject(i);
			cast.setId(NumberUtils.toInt(jo.getString(SUBJECTS_CAST_ID)));
			cast.setName(getJsonString(jo, SUBJECTS_CAST_NAME));
			cast.setAlt(getJsonString(jo, SUBJECTS_CAST_ALT));
			JSONObject avatars = jo.getJSONObject(SUBJECTS_CAST_AVATARS); 
			cast.setAvatarSmall(getJsonString(avatars, SUBJECTS_CAST_AVATARS_SMALL));
			cast.setAvatarMedium(getJsonString(avatars, SUBJECTS_CAST_AVATARS_MEDIUM));
			cast.setAvatarLarge(getJsonString(avatars, SUBJECTS_CAST_AVATARS_LARGE));
			dm.addCases(cast);
		}
		//directors
		ja = json.getJSONArray(SUBJECTS_DIRECTOR);
		for (int i = 0; i < ja.length(); i++) {
			DoubanCast director = new DoubanCast();
			JSONObject jo = ja.getJSONObject(i);
			director.setId(NumberUtils.toInt(jo.getString(SUBJECTS_DIRECTOR_ID)));
			director.setName(getJsonString(jo, SUBJECTS_DIRECTOR_NAME));
			director.setAlt(getJsonString(jo, SUBJECTS_DIRECTOR_ALT));
			JSONObject avatars = jo.getJSONObject(SUBJECTS_DIRECTOR_AVATARS); 
			director.setAvatarSmall(getJsonString(avatars, SUBJECTS_DIRECTOR_AVATARS_SMALL));
			director.setAvatarMedium(getJsonString(avatars, SUBJECTS_DIRECTOR_AVATARS_MEDIUM));
			director.setAvatarLarge(getJsonString(avatars, SUBJECTS_DIRECTOR_AVATARS_LARGE));
			dm.addDirectors(director);
		}
		//aka又名
		JSONArray aka = json.getJSONArray(SUBJECTS_AKA);
		for (int i = 0; i < aka.length(); i++) {
			dm.addAka(aka.getString(i));
		}
		//summary
		dm.setSummary(getJsonString(json, SUBJECTS_SUMMARY));
		return dm;
	}
	
	/**
	 * json to DoubanMovie
	 * @param object
	 * @return
	 */
	private DoubanMovie jsonToDoubanMovie(JSONObject object) {
		DoubanMovie dm = new DoubanMovie();
		dm.setId(NumberUtils.toInt(object.getString(SUBJECTS_ID)));
		dm.setTitle(object.getString(SUBJECTS_TITLE));
		dm.setOriginalTitle(object.getString(SUBJECTS_ENGLISH_TITLE));
		dm.setAlt(object.getString(SUBJECTS_ALT));
		JSONObject jo = object.getJSONObject(SUBJECTS_IMAGE);
		dm.setImageSmall(jo.getString(SUBJECTS_IMAGE_SMALL));
		dm.setImageMedium(jo.getString(SUBJECTS_IMAGE_MEDIUM));
		dm.setImageLarge(jo.getString(SUBJECTS_IMAGE_LARGE));
		jo = object.getJSONObject(SUBJECTS_RATING);
		dm.setRating((int)(jo.getDouble(SUBJECTS_RATING_AVERAGE) * 10));
		dm.setPubdates(getJsonString(object, SUBJECTS_PUBDATAS));
		dm.setYear(NumberUtils.toInt(object.getString(SUBJECTS_YEAR),0));
		dm.setSubtype(object.getString(SUBJECTS_SUBTYPE));
		return dm;
	}
	
	private String getJsonString(JSONObject object, String key) {
		String value = "";
		try {
			value = object.getString(key);
		} catch (Exception e) {
		} 
		return value;
	}
	
	private DoubanMovie getSujectFromDB(String title, int year) {
		DoubanMovie dm = new DoubanMovie();
		dm.setOriginalTitle(title);
		dm.setYear(year);
		return doubanMovieDAO.select(dm, SUBJECTS_ENGLISH_TITLE_COLUMN, SUBJECTS_YEAR_COLUMN);
	}
	
	private boolean save(DoubanMovie dm) {
		doubanMovieDAO.save(dm);
		doubanCastDAO.saveDirectors(dm.getDirectors());
		doubanCastDAO.saveCasts(dm.getCasts());
		return true;
	}
}
