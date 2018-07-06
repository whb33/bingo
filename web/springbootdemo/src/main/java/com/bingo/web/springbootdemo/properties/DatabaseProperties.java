package com.bingo.web.springbootdemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oracleDatabase")
public class DatabaseProperties {
    private String className;
    private String serverName;
    private String portNumber;
    private String databaseName;
    private String driverType;
    private String username;
    private String password;
    private int maximumPoolSize;
    private Long maxLifetime;
    private String poolName;
}
