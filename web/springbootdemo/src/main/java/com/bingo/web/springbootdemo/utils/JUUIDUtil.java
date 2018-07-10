package com.bingo.web.springbootdemo.utils;

import java.util.UUID;

public class JUUIDUtil {
	static Object mutex = new Object();

	public static String createUuid() {
		synchronized (mutex) {
			String str = UUID.randomUUID().toString().replace("-", "");
			return str;
		}
	}
}
