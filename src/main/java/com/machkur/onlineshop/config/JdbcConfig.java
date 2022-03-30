package com.machkur.onlineshop.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class JdbcConfig {

    @Bean
    public PGSimpleDataSource getPGSimpleDataSource(@Value("${db.url}") String url,
                                                    @Value("${db.username}") String username,
                                                    @Value("${db.password}") String password) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(url);
        pgSimpleDataSource.setUser(username);
        pgSimpleDataSource.setPassword(password);

        return pgSimpleDataSource;
    }

}
