package com.jachohx.movie.entity;

import java.util.List;
import java.util.Map;

public abstract class DaoEntity {
	public abstract List<String> getPrimaryKey();
	public abstract Map<String, Object[]> getFieldInfo();
}
