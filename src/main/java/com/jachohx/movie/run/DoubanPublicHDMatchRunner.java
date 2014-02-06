package com.jachohx.movie.run;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.service.DoubanPublicHDMatchService;
import com.jachohx.movie.util.JetEngineUtils;
import com.jachohx.movie.util.MailUtils;
import com.jachohx.movie.util.PropertiesUtils;
import com.jachohx.movie.util.SpringUtils;

public class DoubanPublicHDMatchRunner implements IRunner {
	DoubanPublicHDMatchService service;
	
	public void setUp() {
		service = (DoubanPublicHDMatchService)SpringUtils.getInstance().getBean("doubanPublicHDMatchService");
    }
	public void tearDown(){
    }

	@Override
	public void run(String[] args) throws Exception {
		setUp();
		match(args);
	}
	
	public void match(String[] args) throws Exception {
		String subject = args[0];
		int ymd = Integer.parseInt(args[1]);
		Map<String, List<PublicHDDouban>> pds = service.list(ymd);
		Map<String, Object> context = new HashMap<String, Object>(2);
		context.put("matched", pds.get("matched"));
		context.put("missMatch", pds.get("missMatch"));
		String mail = JetEngineUtils.getTemplate("template/mail.jetx", context);
		MailUtils.getInstance().sendMail(subject, mail, PropertiesUtils.getProperty("config/mail.properties", "mail.to"));
	}
	
	public static void main(String[] args) throws Exception {
		DoubanPublicHDMatchRunner runner = new DoubanPublicHDMatchRunner();
		runner.run(args);
	}
}
