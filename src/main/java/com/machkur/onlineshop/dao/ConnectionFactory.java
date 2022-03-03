package com.machkur.onlineshop.dao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private final String url;
    private final String username;
    private final String password;

    public ConnectionFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            PGSimpleDataSource postgreDS = new PGSimpleDataSource();
            postgreDS.setURL(url);
            postgreDS.setUser(username);
            postgreDS.setPassword(password);
            return postgreDS.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }
}
