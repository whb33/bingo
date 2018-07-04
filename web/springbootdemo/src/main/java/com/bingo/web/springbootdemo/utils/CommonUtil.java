package com.bingo.web.springbootdemo.utils;

import java.util.UUID;

public class CommonUtil {

    /**
     * 获取无“-”符号UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    
    public static String retainCharacter(String str) {
    	str = str.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9.]", "");
        return str;
    }
    
    public static void main(String[] args) {
		System.out.println(retainCharacter("         我的名字（测试.）"));
	}
    
}
