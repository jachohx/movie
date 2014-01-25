package com.jachohx.movie.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.support.FileSystemXmlApplicationContext;


public class SpringUtils {
	static SpringUtils utils = new SpringUtils();
	Properties prop;
	FileSystemXmlApplicationContext context;
	private SpringUtils(){
		prop = new Properties();
		try {
			prop.load(new FileReader("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		context = new FileSystemXmlApplicationContext(prop.getProperty("context.path"));
	}
	public static SpringUtils getInstance() {
		return utils;
	}
	public Object getBean(String bean){
		return context.getBean(bean);
	}
}
