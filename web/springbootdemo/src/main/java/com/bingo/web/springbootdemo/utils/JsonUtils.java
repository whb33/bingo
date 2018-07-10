package com.bingo.web.springbootdemo.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static String object2Json(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T json2Bean(String str, Class<T> beanClass) {
        return JSON.parseObject(str, beanClass);
    }

    public static <T> List<T> json2List(String str, Class<T> beanClass) {
        return JSON.parseArray(str, beanClass);
    }

    /**
     * 将json格式封装的简单实体类型数据转换成javabean
     *
     * @return
     */
    public static Object JSON2Bean(String jsonStr, Object obj) {
        // 简单对象
        // jsonStr = "{\"age\":23,\"id\":123,\"name\":\"tt_2009\"," + "\"province\":\"上海\",\"sex\":\"男\"}";
        // 复杂对象
        // jsonStr = "{\"address\":[\"北京\",\"上海\",\"广州\"]," +
        // "\"age\":23,\"id\":123,\"name\":\"tt_2009\",\"province\":\"上海\",\"sex\":\"男\"}";
        if (StringUtils.isNotEmpty(jsonStr)) {
            JSONObject jsonBean = JSONObject.fromObject(jsonStr);
            return JSONObject.toBean(jsonBean, obj.getClass());
        } else {
            return null;
        }
    }

    /**
     * 将json格式封装的列表数据转换成java的List数据
     *
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object JSON2List(String jsonStr, Object obj) throws InstantiationException, IllegalAccessException {
        // String jsonStr =
        //
        // "[{\"age\":23,\"id\":123,\"name\":\"tt_2009_0\",\"province\":\"上海\",\"sex\":\"男\"}," +
        //
        // "{\"age\":24,\"id\":123,\"name\":\"tt_2009_1\",\"province\":\"上海\",\"sex\":\"男\"}," +
        //
        // "{\"age\":32,\"id\":123,\"name\":\"tt_2009_9\",\"province\":\"上海\",\"sex\":\"男\"}]";
        return JSONArray.toList(JSONArray.fromObject(jsonStr), obj.getClass().newInstance(), new JsonConfig());
    }

    /**
     * 将json格式封装的列表数据转换成java的List数据
     *
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public static List<Object> JSON2List(String jsonStr) throws InstantiationException, IllegalAccessException {
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(jsonStr);
        List<Object> list = (List<Object>) JSONSerializer.toJava(jsonArray);
        return list;
    }

    /**
     * 将json格式封装的字符串数据转换成java中的Map数据
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> JSON2Map(String jsonStr, Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        // String jsonMapStr = "{\"tt_2009_4\":{\"age\":27,\"id\":123,\"name\":\"tt_2009_4\",\"province\":\"上海\",\"sex\":\"男\"},"
        // + "\"tt_2009_6\":{\"age\":29,\"id\":123,\"name\":\"tt_2009_6\",\"province\":\"上海\",\"sex\":\"男\"},"
        // + "\"tt_2009_0\":{\"age\":23,\"id\":123,\"name\":\"tt_2009_0\",\"province\":\"上海\",\"sex\":\"男\"}}";
        jsonStr = jsonStr.replaceAll("\"", "\\\"");
        JSONObject jsonMap = JSONObject.fromObject(jsonStr);
        Iterator<String> it = jsonMap.keys();
        while (it.hasNext()) {
            String key = it.next();
            Object u = JSONObject.toBean(JSONObject.fromObject(jsonMap.get(key)), obj.getClass());
            map.put(key, u);
        }
        return map;
    }

    /**
     * 将json格式封装的字符串数据转换成java中的Map数据
     *
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> JSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonMap = JSONObject.fromObject(jsonStr.replace("=", ""));
        Iterator<String> it = jsonMap.keys();
        while (it.hasNext()) {
            String key = it.next();
            map.put(key, jsonMap.get(key).toString());
        }
        return map;
    }

    /**
     * bean、list、map->json
     *
     * @param obj
     * @return
     */
    public static String Object2JSON(Object obj) {
        JSONObject json = JSONObject.fromObject(obj);
        return json.toString(); // 结果为：{"enabled":true,"username":"hzucmj","age":22}
    }

}
