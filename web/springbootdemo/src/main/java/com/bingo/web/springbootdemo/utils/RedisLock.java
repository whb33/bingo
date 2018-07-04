package com.bingo.web.springbootdemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisLock {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public boolean lock(String key, String value) {
		if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {// setNX 返回boolean
			return true;
		}
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
			// 获取上一个锁的时间
			String oldvalue = stringRedisTemplate.opsForValue().getAndSet(key, value);
			if (!StringUtils.isEmpty(oldvalue) && oldvalue.equals(currentValue)) {
				return true;
			}
		}
		return false;
	}

	public void unlock(String key, String value) {
		try {
			String currentValue = stringRedisTemplate.opsForValue().get(key);
			if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
				stringRedisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			log.error("解锁异常");
		}
	}

}
