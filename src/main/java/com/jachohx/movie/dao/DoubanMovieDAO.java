package com.jachohx.movie.dao;

import com.jachohx.movie.entity.DoubanMovie;

public class DoubanMovieDAO extends AbstractDAO<DoubanMovie> {

	public final static String tableName = "m_douban_movie_subject";
	
	@Override
	protected String getTableName() {
		return tableName;
	}

	@Override
	protected DoubanMovie getObject() {
		return new DoubanMovie();
	}

	public void save(DoubanMovie dm) {
		this.insert(dm);
	}
}
