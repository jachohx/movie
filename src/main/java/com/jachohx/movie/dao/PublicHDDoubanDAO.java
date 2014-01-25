package com.jachohx.movie.dao;

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

}