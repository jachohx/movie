package com.jachohx.movie.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicHD extends DaoEntity{
	private int id;
	private String url;
	private String md5;
	private int category;
	private String name;
	private String magnet;
	private String torrent;
	private int addDate;
	private int seeds;
	private int leechers;
	private String uploader;
	private String size;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMagnet() {
		return magnet;
	}
	public void setMagnet(String magnet) {
		this.magnet = magnet;
	}
	public String getTorrent() {
		return torrent;
	}
	public void setTorrent(String torrent) {
		this.torrent = torrent;
	}
	public int getAddDate() {
		return addDate;
	}
	public void setAddDate(int addDate) {
		this.addDate = addDate;
	}
	public int getSeeds() {
		return seeds;
	}
	public void setSeeds(int seeds) {
		this.seeds = seeds;
	}
	public int getLeechers() {
		return leechers;
	}
	public void setLeechers(int leechers) {
		this.leechers = leechers;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public Map<String, Object[]> getFieldInfo() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("id", new Object[] { id, "setId", int.class });
		map.put("url", new Object[] { url, "setUrl", String.class });
		map.put("md5", new Object[] { md5, "setMd5", String.class });
		map.put("category", new Object[] { category, "setCategory", int.class });
		map.put("name", new Object[] { name, "setName", String.class });
		map.put("magnet", new Object[] { magnet, "setMagnet", String.class });
		map.put("torrent", new Object[] { torrent, "setTorrent", String.class });
		map.put("add_date", new Object[] { addDate, "setAddDate", int.class });
		map.put("seeds", new Object[] { seeds, "setSeeds", int.class });
		map.put("leechers", new Object[] { leechers, "setLeechers", int.class });
		map.put("uploader", new Object[] { uploader, "setUploader", String.class });
		map.put("size", new Object[] { size, "setSize", String.class });
//		map.put("create_time", new Object[] { createTime, "setCreateTime", Date.class });
		return map;
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
	
}
