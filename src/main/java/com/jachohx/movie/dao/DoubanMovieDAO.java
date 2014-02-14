package com.jachohx.movie.dao;

import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * 根据标题与年份查询，年份跟数据库字段 等于或者大于一年。
	 * @param title
	 * @param year
	 * @return
	 */
	public DoubanMovie selectFromTitleAndYear(String title, int year) {
		String sql = "select * " +
				"FROM "+tableName+" " +
				"WHERE " +
					DoubanMovie.SUBJECTS_ENGLISH_TITLE_COLUMN + " = :"+DoubanMovie.SUBJECTS_ENGLISH_TITLE_COLUMN+" AND " +
					"(:" + DoubanMovie.SUBJECTS_YEAR_COLUMN + "-"+DoubanMovie.SUBJECTS_YEAR_COLUMN+") between 0 and 1 " +
				"ORDER BY "+DoubanMovie.SUBJECTS_YEAR_COLUMN+" desc limit 1";
		Map<String, Object> values = new HashMap<String, Object>(2);
		values.put(DoubanMovie.SUBJECTS_ENGLISH_TITLE_COLUMN, title);
		values.put(DoubanMovie.SUBJECTS_YEAR_COLUMN, year);
		return select(sql, values);
	}
}
