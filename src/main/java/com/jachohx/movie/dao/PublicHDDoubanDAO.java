package com.jachohx.movie.dao;

import java.util.List;

import com.jachohx.movie.entity.PublicHDDouban;

public class PublicHDDoubanDAO extends AbstractDAO<PublicHDDouban> {

	public final static String tableName = "m_publichd_douban";
	
	@Override
	protected String getTableName() {
		return tableName;
	}

	@Override
	protected PublicHDDouban getObject() {
		return new PublicHDDouban();
	}
	
	public List<PublicHDDouban> pager(int pageNo, int pageSize) {
		String sql = "Select * from " + tableName + " where douban_id = -1 order by phd_id desc limit " + (pageNo - 1) + "," + pageSize;
		return list(sql);
	}

}