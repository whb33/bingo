package com.bingo.web.springbootdemo.configuration;

import com.bingo.web.springbootdemo.properties.DatabaseProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(databaseProperties.getClassName());
        config.addDataSourceProperty("serverName", databaseProperties.getServerName());
        config.addDataSourceProperty("portNumber", databaseProperties.getPortNumber());
        config.addDataSourceProperty("databaseName", databaseProperties.getDatabaseName());
        config.addDataSourceProperty("driverType", databaseProperties.getDriverType());
        config.setUsername(databaseProperties.getUsername());
        config.setPassword(databaseProperties.getPassword());
        config.setMaximumPoolSize(databaseProperties.getMaximumPoolSize());
        config.setMaxLifetime(databaseProperties.getMaxLifetime());
        config.setPoolName(databaseProperties.getPoolName());
        return new HikariDataSource(config);
    }
}
