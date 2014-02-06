package com.jachohx.movie.util;

import org.junit.Test;

public class JetEngineUtilsTest {
	@Test
	public void getTemplate(){
		System.out.println(JetEngineUtils.getTemplate("template/test.jetx", null));
	}
}
