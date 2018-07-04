package com.bingo.web.springbootdemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:${spring.profiles.active}/db.properties")
@ConfigurationProperties(prefix = "db")
@Data
public class DatabaseProperties {

    private String className;
    private String jdbcUrl;
    private String username;
    private String password;
    private int maximumPoolSize;
    private Long maxLifetime;
    private String poolName;

}
