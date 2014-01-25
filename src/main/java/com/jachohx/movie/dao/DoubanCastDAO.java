package com.jachohx.movie.dao;

import java.util.List;

import com.jachohx.movie.entity.DoubanCast;

public class DoubanCastDAO extends AbstractDAO<DoubanCast> {

	public final static String tableName = "m_douban_cast";
	
	@Override
	protected String getTableName() {
		return tableName;
	}

	@Override
	protected DoubanCast getObject() {
		return new DoubanCast();
	}
	
	public void saveCasts(List<DoubanCast> casts){
		save(casts);
	}
	
	public void saveDirectors(List<DoubanCast> directors){
		save(directors);
	}
	
	private void save(List<DoubanCast> doubanCasts) {
		for (DoubanCast cast : doubanCasts) {
			DoubanCast _cast = select(cast);
			if(_cast == null)
				insert(cast);
		}
	}

}