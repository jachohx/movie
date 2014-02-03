package com.jachohx.movie.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
	static Map<String, Properties> propertiesMap = new Hashtable<String, Properties>();
	public static String getProperty(String fileName, String key) throws IOException {
		Properties prop = propertiesMap.get(fileName);
		if (prop == null) {
			prop = new Properties();
			prop.load(new FileReader(fileName));
			propertiesMap.put(fileName, prop);
		}
		return prop.getProperty(key);
	}
}
