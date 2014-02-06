package com.jachohx.movie.util;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class MailUtils {
	
	String EMAIL_HOST_NAME;
	String EMAIL_FROM;
	String EMAIL_CHARSET;
	String EMAIL_USERNAME;
	String EMAIL_PASSWORD;
	int EMAIL_SMTP_PORT;
	
	static MailUtils mailUtils;
	
	public static MailUtils getInstance() {
		if (mailUtils == null) {
			mailUtils = new MailUtils();
		}
		return mailUtils;
	}
	
	public MailUtils() {
		init();
	}
	
	/**
	 * 发送邮件
	 * @param subject		主题
	 * @param content		内容
	 * @param mailaddress   发送地址
	 * @throws EmailException 
	 * @throws MessagingException 
	 */
	public void sendMail(String subject, String content, String... mailaddress) throws EmailException, MessagingException {
		sendMail(subject, content, null, mailaddress);
	}
	
	/**
	 * 发送带有附件的邮件
	 * @param subject		主题
	 * @param content		内容
	 * @param attach		附件
	 * @param mailaddress   发送地址
	 * @throws EmailException 
	 * @throws MessagingException 
	 */
	public void sendMail(String subject, String content, File attach, String... mailaddress) throws EmailException, MessagingException {
		HtmlEmail email = new HtmlEmail();
		if (EMAIL_SMTP_PORT != 0)
			email.setSmtpPort(EMAIL_SMTP_PORT);
		email.setAuthenticator(new DefaultAuthenticator(EMAIL_USERNAME, EMAIL_PASSWORD));
		email.setHostName(EMAIL_HOST_NAME);
		email.setCharset(EMAIL_CHARSET);
		email.setFrom(EMAIL_FROM);
		email.setSubject(subject);
		email.setMsg(content);
		if(attach != null)
			email.attach(attach);
		email.addTo(mailaddress);
		email.send();
	}
	
	private void init(){
		try {
			EMAIL_HOST_NAME = PropertiesUtils.getProperty("config/mail.properties", "mail.host");
			EMAIL_FROM = PropertiesUtils.getProperty("config/mail.properties", "mail.from");
			EMAIL_CHARSET = PropertiesUtils.getProperty("config/mail.properties", "mail.charset");
			EMAIL_USERNAME =PropertiesUtils.getProperty("config/mail.properties", "mail.username");
			EMAIL_PASSWORD = PropertiesUtils.getProperty("config/mail.properties", "mail.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws EmailException, MessagingException {
		if (args.length < 3) {
			System.err.println("the MailUtils parameters' length must gt 2");
		}
		String subject = args[0];
		String content = args[1];
		String[] mailTos = args[2].split(",");
		File file = null;
		if (args.length > 3)
			 file = new File(args[3]);
		MailUtils.getInstance().sendMail(subject, content, file, mailTos);
	}
	
}
