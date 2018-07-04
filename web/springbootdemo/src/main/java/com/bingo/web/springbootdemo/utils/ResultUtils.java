package com.bingo.web.springbootdemo.utils;

import com.bingo.web.springbootdemo.constant.ResultEnum;
import com.bingo.web.springbootdemo.entity.Result;

public class ResultUtils {
	
	public static Result<Object> success(Object object) {
		Result<Object> result = new Result<Object>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
	}
	public static Result<Object> error(String code, String msg, Object object) {
		Result<Object> result = new Result<Object>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
	}
	
	public static Result<Object> success() {
        return success(null);
    }
	
	public static Result<Object> error(String code, String msg) {
        return error(code, msg, null);
    }
	
	public static Result<Object> result(ResultEnum resultEnum, Object object) {
		Result<Object> result = new Result<Object>();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMessage());
        result.setData(object);
        return result;
	}
	
	public static Result<Object> result(ResultEnum resultEnum) {
		Result<Object> result = new Result<Object>();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMessage());
        result.setData(null);
        return result;
	}

}
