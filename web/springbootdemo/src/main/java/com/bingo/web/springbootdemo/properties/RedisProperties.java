package com.bingo.web.springbootdemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisProperties {
	private String ip;
	private Integer port;
	private int maxTotal;
	private int maxIdle;
	private int minIdle;
	private Long maxWaitMillis;
	private boolean testOnBorrow;
	private Long softMinEvictableIdleTimeMillis;
}
