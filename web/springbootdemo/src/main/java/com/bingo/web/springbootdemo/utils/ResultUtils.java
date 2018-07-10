package com.bingo.web.springbootdemo.utils;

import com.bingo.web.springbootdemo.constant.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 返回结果工具类
 */
public class ResultUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(ResultUtils.class);

    public static Map<Object, Object> getResultMap(RespCode respCode, Object... objects) {
        Map<Object, Object> respMap = new HashMap<Object, Object>();
        respMap.put("status", respCode.getCode());
        respMap.put("msg", respCode.getMsg());
        for (Object obj : objects) {
            if (obj instanceof Map) {
                Set entrySet = ((Map) obj).entrySet();
                for (Object e : entrySet) {
                    Map.Entry entry = (Map.Entry) e;
                    respMap.put(entry.getKey(), entry.getValue());
                }
            } else if (obj instanceof List) {
                respMap.put("list", obj);
            } else if (obj instanceof Set) {
                respMap.put("set", obj);
            } else if (obj instanceof String) {
                respMap.put("string", obj);
            } else if (obj instanceof Integer) {
                respMap.put("int", obj);
            } else if (obj instanceof Long) {
                respMap.put("long", obj);
            } else if (obj instanceof Character) {
                respMap.put("char", obj);
            } else {
                respMap.put(obj.getClass().getSimpleName().toLowerCase(), obj);
            }
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.info("返回结果:{}", respMap);
        return respMap;
    }

    public static Map<String, Object> getRespMap(RespCode respCode, Object data) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put("status", respCode.getCode());
        respMap.put("msg", respCode.getMsg());
        respMap.put("data", data);
        if (LOGGER.isDebugEnabled())
            LOGGER.info("返回结果:{}", respMap);
        return respMap;
    }

    public static Map<String, Object> getRespMap(RespCode respCode) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put("status", respCode.getCode());
        respMap.put("msg", respCode.getMsg());
        if (LOGGER.isDebugEnabled())
            LOGGER.info("返回结果:{}", respMap);
        return respMap;
    }

    public static Map<String, Object> getSuccessRespMap(Object data) {
        return getRespMap(RespCode.SUCCESS, data);
    }

    public static Map<String, Object> getSuccessRespMap() {
        return getRespMap(RespCode.SUCCESS);
    }

    public static Map<String, Object> getFailedRespMap() {
        return getRespMap(RespCode.FAIL);
    }

    public static Map<String, Object> getErrorRespMap() {
        return getRespMap(RespCode.ERROR);
    }

    public static Map<String, Object> getStatusRespMap(Integer status, String message) {
        Map<String, Object> respMap = new HashMap<String, Object>(2);
        respMap.put("status", status.toString());
        if (StringUtils.isNotEmpty(message))
            respMap.put("msg", message);
        if (LOGGER.isDebugEnabled())
            LOGGER.info("返回结果:{}", respMap);
        return respMap;
    }

    public static Map<String, Object> getStatusRespMap(Integer status) {
        return getStatusRespMap(status, null);
    }

}