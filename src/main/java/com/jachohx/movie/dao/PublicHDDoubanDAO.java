package com.jachohx.movie.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 查找列表，title为publichd name
	 * @param doubanId
	 * @return
	 */
	public List<Map<String,Object>> listForOriTitle(int doubanId) {
		String sql = "Select pd.*,p.name title from " + tableName + " pd," + PublicHDDAO.tableName + " p where pd.douban_id = " + doubanId + " and p.id = pd.phd_id order by phd_id desc";
		return queryForList(sql);
	}
	
	public PublicHDDouban selectFromNameAndYear(String name, int year) {
		String sql = "select * " +
				"FROM "+tableName+" " +
				"WHERE " + 
					PublicHDDouban.NAME_COLUMN + " = :"+PublicHDDouban.NAME_COLUMN+" AND " + 
					PublicHDDouban.YEAR_COLUMN + " = :" + PublicHDDouban.YEAR_COLUMN + " " +
				"order by " + PublicHDDouban.PHD_ID_COLUMN + " desc limit 1";
		Map<String, Object> values = new HashMap<String, Object>(2);
		values.put(PublicHDDouban.NAME_COLUMN, name);
		values.put(PublicHDDouban.YEAR_COLUMN, year);
		return select(sql, values);
	}
	
}