package com.jachohx.movie.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoubanCast extends DaoEntity {
	
	private int id;
	private String name;
	private String alt;
	private String avatarSmall;
	private String avatarMedium;
	private String avatarLarge;

	public DoubanCast(){
	}
	public DoubanCast(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getAvatarSmall() {
		return avatarSmall;
	}
	public void setAvatarSmall(String avatarSmall) {
		this.avatarSmall = avatarSmall;
	}

	public String getAvatarMedium() {
		return avatarMedium;
	}
	public void setAvatarMedium(String avatarMedium) {
		this.avatarMedium = avatarMedium;
	}

	public String getAvatarLarge() {
		return avatarLarge;
	}
	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
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
		map.put("name", new Object[] { name, "setName", String.class });
		map.put("alt", new Object[] { alt, "setAlt", String.class });
		map.put("avatar_small", new Object[] { avatarSmall, "setAvatarSmall", String.class });
		map.put("avatar_medium", new Object[] { avatarMedium, "setAvatarMedium", String.class });
		map.put("avatar_large", new Object[] { avatarLarge, "setAvatarLarge", String.class });
		return map;
	}

}
