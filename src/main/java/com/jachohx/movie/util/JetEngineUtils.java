package com.jachohx.movie.util;

import java.io.StringWriter;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

public class JetEngineUtils {
	public static String getTemplate(String templateFile, Map<String, Object> context) {
		// 创建一个默认的 JetEngine
		JetEngine engine = JetEngine.create();
		 
		// 获取一个模板对象
		JetTemplate template = engine.getTemplate(templateFile);
		 
		// 创建 context 对象
//		Map<String, Object> context = new HashMap<String, Object>();
//		context.put("user", user);
//		context.put("books", books);
		 
		// 渲染模板
		StringWriter writer = new StringWriter();
		template.render(context, writer);
		 
		// 结果
		return writer.toString();
	}
}
