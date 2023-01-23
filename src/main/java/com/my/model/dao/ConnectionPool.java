package com.my.model.dao;

import com.my.model.dao.exceptions.DBException;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final DataSource dataSource;
    static {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/Payments?characterEncoding=UTF-8");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("1111");
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(10);
        basicDataSource.setMaxOpenPreparedStatements(100);
        dataSource = basicDataSource;
    }
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBException("Failed to get connection", e);
        }
    }
}
