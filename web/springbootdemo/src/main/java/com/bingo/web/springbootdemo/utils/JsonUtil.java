package com.bingo.web.springbootdemo.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JsonUtil {
	
	/**
     * 将对象转换成json字符
     * @param bean 待转化的对象
     * @param excludeProperties 转化过程中序号排除掉的属性名
     * @return
     * @throws Exception
     */
    public static String acquireJsonStringForBean(Object bean,
                                                  String[] excludeProperties) throws Exception {
        JsonConfig cfg = new JsonConfig();
        cfg.setExcludes(excludeProperties);
        cfg.setIgnoreDefaultExcludes(false);
        cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

        cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {

            public Object processObjectValue(String arg0, Object arg1,
                                             JsonConfig arg2) {
                // TODO Auto-generated method stub
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
                if (arg1 == null)
                    return "";
                Date d1 = (Date) arg1;
                return simpleDateFormat.format(d1);
            }

            public Object processArrayValue(Object arg0, JsonConfig arg1) {
                // TODO Auto-generated method stub
                return null;
            }
        });

        // System.out.println(JSONObject.fromObject(bean, cfg).toString());
        return JSONObject.fromObject(bean, cfg).toString();
    }

    public static String acquireJsonStringForList(List<?> list,
                                                  String[] excludeProperties) throws Exception {
    	if(list!=null){
    		JsonConfig cfg = new JsonConfig();
            cfg.setExcludes(excludeProperties);
            cfg.setIgnoreDefaultExcludes(false);
            cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

            cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {

                public Object processObjectValue(String arg0, Object arg1,
                                                 JsonConfig arg2) {
                    // TODO Auto-generated method stub
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                    if (arg1 == null)
                        return "";
                    Date d1 = (Date) arg1;
                    return simpleDateFormat.format(d1);
                }

                public Object processArrayValue(Object arg0, JsonConfig arg1) {
                    // TODO Auto-generated method stub
                    return null;
                }
            });
            // System.out.println(JSONArray.fromObject(list, cfg));
            return JSONArray.fromObject(list, cfg).toString();
    	}
    	return null;
    }

}
