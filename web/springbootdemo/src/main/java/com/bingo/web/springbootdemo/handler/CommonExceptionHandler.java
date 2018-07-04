package com.bingo.web.springbootdemo.handler;

import com.bingo.web.springbootdemo.entity.Result;
import com.bingo.web.springbootdemo.exception.ResultException;
import com.bingo.web.springbootdemo.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.bingo.web.springbootdemo.utils.ResultUtils.error;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(value = ResultException.class)
	@ResponseBody
	public Result<Object> ResultExceptionHandle(Exception e) {
		ResultException exception = (ResultException) e;
		return ResultUtils.error(exception.getCode(), exception.getMessage(), exception.getObject());
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public void Handle(Exception e) throws Exception {
		log.info(e.getMessage(), e);
        throw e;
	}

}
