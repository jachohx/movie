package com.jachohx.movie.util;

import org.junit.Test;

public class TimeUtilsTest {
	@Test
	public void getIntForDMY() {
		System.out.println(TimeUtils.getIntForDMY("10/12/2013"));
	}
	@Test
	public void getDate(){
		int ymd = 20140206;
		System.out.println(ymd + " Date:" + TimeUtils.getDate(ymd));
	}
	@Test
	public void tomorrow(){
		int ymd = 20140206;
		System.out.println(ymd + " tomorrow:" + TimeUtils.tomorrow(ymd));
	}
}
