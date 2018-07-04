package com.bingo.web.springbootdemo.aspect;

import com.bingo.web.springbootdemo.constant.ResultEnum;
import com.bingo.web.springbootdemo.exception.ResultException;
import com.bingo.web.springbootdemo.utils.IllegalStrFilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class CheckInputParameterAspect {
	
	// 存在SQL注入风险
    private static final String IS_SQL_INJECTION = "输入参数存在SQL注入风险";
    private static final String UNVALIDATED_INPUT = "输入参数含有非法字符";
    
    @Pointcut("execution(* com.bingo.web.springbootdemo.controller..*(..))")
    public void params() {
    }
    
    @Around("params()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("当前调用接口-[" + request.getRequestURL() + "]");
        Object[] args = joinPoint.getArgs();// 参数
        for (Object o : args) {
        	if (o instanceof String) {
                String str = o.toString();
                if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
                	log.info(IS_SQL_INJECTION);
                	throw new ResultException(ResultEnum.ERORR_INPUT);
                }
                if (IllegalStrFilterUtil.isIllegalStr(str)) {
                    log.info(UNVALIDATED_INPUT);
                    throw new ResultException(ResultEnum.ERORR_INPUT);
                }
        	}
        }
        Object result = joinPoint.proceed();
        return result;
    }

}
