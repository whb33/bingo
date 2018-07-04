package com.bingo.web.springbootdemo.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseUtills {

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = 0;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
				chLength++;
			}
		}
		return count / chLength > 0.4;
	}

	public static String toChinese(String msg) {
		String tempMsg = StringUtils.isNotEmpty(msg) ? msg.trim() : "";
		if (isMessyCode(tempMsg)) {
			try {
				return new String(tempMsg.getBytes("ISO8859-1"), "UTF-8");
			} catch (Exception e) {
			}
		}
		return tempMsg;
	}
	
	public static Boolean isChinese(String str) {
		Boolean isChinese = true;//[\u4e00-\u9fa5]    [\u0391-\uFFE5]
		String chinese = "[\u4e00-\u9fa5]";
		if (!StringUtils.isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
				} else {
					isChinese = false;
				}
			}
		}
		return isChinese;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(isChinese("123"));
		System.out.println(isChinese("中文"));
		
		System.out.println(toChinese("中文"));
		System.out.println(toChinese("abc"));
		
		System.out.println(new String("中文".getBytes("UTF-8"), "ISO8859-1"));
		System.out.println(new String("abc".getBytes("UTF-8"), "ISO8859-1"));
		
		System.out.println(toChinese(new String("中文".getBytes("UTF-8"), "ISO8859-1")));

	}

}
