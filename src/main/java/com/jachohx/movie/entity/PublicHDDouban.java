package com.jachohx.movie.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicHDDouban extends DaoEntity{
	private int phdId;
	private int doubanId;
	private String name;
	private int year;
	private DoubanMovie doubanMovie;
	private PublicHD publicHD;
	public PublicHDDouban(){
	}
	public PublicHDDouban(int phdId, int doubanId, String name, int year){
		this.phdId = phdId;
		this.doubanId =doubanId;
		this.name = name;
		this.year = year;
	}
	public int getPhdId() {
		return phdId;
	}
	public void setPhdId(int phdId) {
		this.phdId = phdId;
	}
	public int getDoubanId() {
		return doubanId;
	}
	public void setDoubanId(int doubanId) {
		this.doubanId = doubanId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public DoubanMovie getDoubanMovie() {
		return doubanMovie;
	}
	public void setDoubanMovie(DoubanMovie doubanMovie) {
		this.doubanMovie = doubanMovie;
	}
	public PublicHD getPublicHD() {
		return publicHD;
	}
	public void setPublicHD(PublicHD publicHD) {
		this.publicHD = publicHD;
	}
	@Override
	public Map<String, Object[]> getFieldInfo() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("phd_id", new Object[] { phdId, "setPhdId", int.class });
		map.put("douban_id", new Object[] { doubanId, "setDoubanId", int.class });
		map.put("name", new Object[] { name, "setName", String.class });
		map.put("year", new Object[] { year, "setYear", int.class });
		return map;
	}
	private List<String> pks;
	@Override
	public List<String> getPrimaryKey() {
		if (pks == null) {
			pks = new ArrayList<String>(1);
			pks.add("phd_id");
		}
		return pks;
	}
	
	@Override
	public String toString(){
		return "phdId:" + phdId + "\tdoubanId:" + doubanId + "\tname:" + name + "\tyear:" + year;
	}
	
}
