package com.jachohx.movie.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Test;

import com.jachohx.movie.entity.DoubanMovie;
import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.entity.PublicHDDouban;
import com.jachohx.movie.util.JetEngineUtils;
import com.jachohx.movie.util.MailUtils;
import com.jachohx.movie.util.PropertiesUtils;
import com.jachohx.movie.util.SpringUtils;

public class DoubanPublicHDServiceTest {
	DoubanPublicHDMatchService service;
	@Before
	public void setUp(){
		service = (DoubanPublicHDMatchService)SpringUtils.getInstance().getBean("doubanPublicHDMatchService");
	}
	
//	@Test
	public void list() throws Exception {
		int ymd = 20140204;
		List<PublicHDDouban> pds = (List<PublicHDDouban>)service.list(ymd).get("matched");
		System.out.println(pds.size());
		for (PublicHDDouban pd : pds) {
			DoubanMovie dm = pd.getDoubanMovie();
			PublicHD phd = pd.getPublicHD();
			System.out.println("movie:" + dm.getTitle() + "\tpublicHD:" + phd.getName());
		}
		List<PublicHDDouban> mms = (List<PublicHDDouban>)service.list(ymd).get("missMatch");
		for (PublicHDDouban pd : mms) {
			PublicHD phd = pd.getPublicHD();
			System.out.println("no match id:" + pd.getPhdId() + ",title:" + phd.getName() + ",name:" + pd.getName() + ",year:" + pd.getYear());
		}
	}
	
//	@Test
	public void html() throws FileNotFoundException {
		int ymd = 20140204;
		Map<String, List<PublicHDDouban>> pds = service.list(ymd);
		Map<String, Object> context = new HashMap<String, Object>(2);
		context.put("matched", pds.get("matched"));
		context.put("missMatch", pds.get("missMatch"));
		File file = new File("mail.20140204.html");
		PrintWriter writer = new PrintWriter(file);
		writer.print(JetEngineUtils.getTemplate("template/mail.jetx", context));
		writer.flush();
		writer.close();
		System.out.println("completed! file:" + file.getAbsolutePath());
	}
	
//	@Test
	public void mail() throws EmailException, MessagingException, IOException {
		int ymd = 20140204;
		Map<String, List<PublicHDDouban>> pds = service.list(ymd);
		Map<String, Object> context = new HashMap<String, Object>(2);
		context.put("matched", pds.get("matched"));
		context.put("missMatch", pds.get("missMatch"));
		String mail = JetEngineUtils.getTemplate("template/mail.jetx", context);
		MailUtils.getInstance().sendMail("mail.20140204", mail, PropertiesUtils.getProperty("config/mail.properties", "mail.to"));
		System.out.println("completed! send success!");
	}
}
