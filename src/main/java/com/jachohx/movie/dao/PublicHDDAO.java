package com.jachohx.movie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.jachohx.movie.entity.PublicHD;

public class PublicHDDAO extends AbstractDAO<PublicHD>{
	
	public final static String tableName = "m_publichd";

	@Override
	protected String getTableName() {
		return tableName;
	}

	@Override
	protected PublicHD getObject() {
		return new PublicHD();
	}
	
	public PublicHD getPublicHD4Md5(String md5) {
		PublicHD phd = null;
		String sql = "SELECT * FROM " + getTableName() + " WHERE 1 and md5 = '" + md5 + "'";
		try {
			phd = simpleJdbcTemplate.queryForObject(sql, objectRowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return phd;
	}
	
	public boolean isExist(PublicHD phd) {
		PublicHD _phd = getPublicHD4Md5(phd.getMd5());
		if (_phd != null && _phd.getId() > 0) {
			return true;
		}
		return false;
	}
	
	final String sql = "INSERT INTO m_publichd (id,category,uploader,name,md5,add_date,seeds,leechers,magnet,torrent,url,size) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
	public boolean create(final PublicHD phd) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = jdbcTemplate.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				ps.setInt(index ++, phd.getId());
				ps.setInt(index ++, phd.getCategory());
				ps.setString(index ++, phd.getUploader());
				ps.setString(index ++, phd.getName());
				ps.setString(index ++, phd.getMd5());
				ps.setInt(index ++, phd.getAddDate());
				ps.setInt(index ++, phd.getSeeds());
				ps.setInt(index ++, phd.getLeechers());
				ps.setString(index ++, phd.getMagnet());
				ps.setString(index ++, phd.getTorrent());
				ps.setString(index ++, phd.getUrl());
				ps.setString(index ++, phd.getSize());
				return ps;
			}
		}, keyHolder);
		phd.setId(keyHolder.getKey().intValue());
		return row > 0 ? true : false;
	}
}
