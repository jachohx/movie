package com.jachohx.movie.util;

import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.junit.Test;

public class MailUtilsTest {
	@Test
	public void send() throws EmailException, MessagingException {
		String subject = "一定要收到";
		String content = "你好，这邮件一定要收到";
		String to = "huang9370@163.com";
		MailUtils.getInstance().sendMail(subject, content, to);
	}
}
