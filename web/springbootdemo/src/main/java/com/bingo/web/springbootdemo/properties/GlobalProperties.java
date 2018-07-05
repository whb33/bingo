package com.bingo.web.springbootdemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "global")
@Data
public class GlobalProperties {
	private String projectCode;
	private String shiroSecret;
}
