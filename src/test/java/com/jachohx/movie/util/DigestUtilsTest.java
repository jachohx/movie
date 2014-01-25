package com.jachohx.movie.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class DigestUtilsTest {
	@Test
	public void md5(){
		byte[] md5s = DigestUtils.md5("jacho");
		System.out.println(md5s.length);
		for (byte b : md5s){
			System.out.print(b);
		}
		System.out.println();
		System.out.println(DigestUtils.md5("jacho").toString());
		System.out.println(DigestUtils.md5Hex("jacho"));
	}
}
