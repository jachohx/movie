package com.jachohx.movie.dao;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.jachohx.movie.entity.DaoEntity;

public abstract class AbstractDAO<T extends DaoEntity> {
	private static Logger log = Logger.getLogger("dao");
	
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	protected JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	protected abstract String getTableName();
	
	private String insertSQL;
	private String updateSQL;
	private String deleteSQL;
	private String selectSQL;
	public void init() {
		initInsertSQL();
		initUpdateSQL();
		initDeleteSQL();
		initSelectSQL();
	}
	
	private void initInsertSQL(){
		Map<String, Object[]> map = getObject().getFieldInfo();
		StringBuilder fieldNames = new StringBuilder();
		StringBuilder fieldValues = new StringBuilder();
		for (String column : map.keySet()) {
			if (fieldNames.length() > 0) {
				fieldNames.append(",");
				fieldValues.append(",");
			}
			fieldNames.append(column);
			fieldValues.append(":" + column);
		}
		insertSQL = "INSERT INTO " + getTableName() + " ( " + fieldNames + " ) " + " VALUES (" +fieldValues + " ); ";
		log.debug("initInsertSQL:" + insertSQL);
	}
	private void initUpdateSQL(){
		Map<String, Object[]> map = getObject().getFieldInfo();
		StringBuilder fieldNames = new StringBuilder();
		StringBuilder primaryKeyNames = new StringBuilder();
		List<String> pks = getObject().getPrimaryKey();
		for (String column : map.keySet()) {
			if (pks.contains(column)) {
				if (primaryKeyNames.length() > 0) {
					primaryKeyNames.append(" AND ");
				}
				primaryKeyNames.append(column + " = :" + column);
				continue;
			}
			if (fieldNames.length() > 0) {
				fieldNames.append(",");
			}
			fieldNames.append(column + " = :" + column);
		}
		updateSQL = "UPDATE " + getTableName() + " SET " + fieldNames + " WHERE " +primaryKeyNames;
		log.debug("initUpdateSQL:" + updateSQL);
	}
	private void initDeleteSQL(){
		Map<String, Object[]> map = getObject().getFieldInfo();
		StringBuilder primaryKeyNames = new StringBuilder();
		List<String> pks = getObject().getPrimaryKey();
		for (String column : map.keySet()) {
			if (pks.contains(column)) {
				if (primaryKeyNames.length() > 0) {
					primaryKeyNames.append(" AND ");
				}
				primaryKeyNames.append(column + " = :" + column);
				continue;
			}
		}
		deleteSQL = "DELETE FROM " + getTableName() + " WHERE " +primaryKeyNames;
		log.debug("initDeleteSQL:" +deleteSQL);
	}
	private void initSelectSQL(){
		selectSQL = getSelectSQL(getObject().getPrimaryKey());
		log.debug("initSelectSQL:" +selectSQL);
	}
	
	private String getSelectSQL(List<String> keys) {
		StringBuilder primaryKeyNames = new StringBuilder();
		for (String key : keys) {
			if (primaryKeyNames.length() > 0) {
				primaryKeyNames.append(" AND ");
			}
			primaryKeyNames.append(key + " = :" + key);
		}
		String sql = "select * FROM " + getTableName() + " WHERE " +primaryKeyNames;
		return sql;
	}
	
	@SuppressWarnings("rawtypes")
	protected ParameterizedRowMapper<T> objectRowMapper = new ParameterizedRowMapper<T>(){
//	protected class ObjectRowMapper implements ParameterizedRowMapper<T>{
		@Override
		public T mapRow(ResultSet rs, int index) throws SQLException {
			T object = getObject();
			Map<String, Object[]> map = object.getFieldInfo();
			for (String key : map.keySet()) {
				Object o[] = map.get(key);
				try {
					Method m = object.getClass().getMethod((String) o[1], new Class[] { (Class) o[2] });
					if (m != null) {
						if (o[2] == int.class) {
							m.invoke(object, rs.getInt(key));
						} else if (o[2] == long.class) {
							m.invoke(object, rs.getLong(key));
						} else if (o[2] == String.class) {
							m.invoke(object, rs.getString(key));
						} else if (o[2] == Date.class) {
							m.invoke(object, rs.getTimestamp(key));
						} else if (o[2] == boolean.class) {
							m.invoke(object, rs.getBoolean(key));
						}
					}
				} catch (Exception e) {
				}
			}
			return object;
		}
	};
	
	protected abstract T getObject();
	protected Map<String, Object> entity2Map(T entity) {
		Map<String, Object[]> map = entity.getFieldInfo();
		Map<String, Object> values = new HashMap<String, Object>(map.size());
		for (Map.Entry<String, Object[]> mapEntity : map.entrySet()) {
			values.put(mapEntity.getKey(), mapEntity.getValue()[0]);
		}
		return values;
	}
	
	private int updateOption(T entity, String sql) {
		if (entity == null) {
			return 0;
		}
		Map<String, Object> values = entity2Map(entity);
		log.info(sql + "[" + values + "]");
		return simpleJdbcTemplate.update(sql, values);
	}
	private T queryOption(T entity, List<String> keys) {
		if (entity == null) {
			return null;
		}
		//sql
		String sql = "";
		if (keys == null || keys.size() == 0) {
			keys = entity.getPrimaryKey();
			sql = selectSQL;
		} else {
			sql = getSelectSQL(keys);
		}
		//values
		Map<String, Object[]> fieldInfo = entity.getFieldInfo();
		Map<String, Object> values = new HashMap<String, Object>(keys.size());
		for (String pk : keys) {
			values.put(pk, fieldInfo.get(pk)[0]);
		}
		log.info(sql + "[" + values + "]");
		//object
		T t = null;
		try {
			t = simpleJdbcTemplate.queryForObject(sql, objectRowMapper, values);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return t;
	}
	public boolean insert(T entity) {
		int rows = 0;
		try {
			rows = updateOption(entity, insertSQL);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return rows > 0 ? true : false;
	}
	public int update(T entity) {
		return updateOption(entity, updateSQL);
	}
	public int delete(T entity) {
		return updateOption(entity, deleteSQL);
	}
	/**
	 * 如果keys为空，则以主键为key
	 * @param entity
	 * @param keys
	 * @return
	 */
	public T select(T entity, String... keys){
		List<String> keyList = null;
		if (keys != null && keys.length > 0) {
			keyList = new ArrayList<String>();
			Collections.addAll(keyList, keys);
		}
		return queryOption(entity, keyList);
	}
	
	public List<T> list(String sql){
		log.info(sql);
		return simpleJdbcTemplate.query(sql, objectRowMapper);
	}
	
	public List<T> list(String sql, Map<String, Object> params){
		log.info(sql + "[" + params + "]");
		return simpleJdbcTemplate.query(sql, objectRowMapper, params);
	}
	
	public List<Map<String,Object>> queryForList(String sql) {
		log.info(sql);
		return simpleJdbcTemplate.queryForList(sql);
	}
}
