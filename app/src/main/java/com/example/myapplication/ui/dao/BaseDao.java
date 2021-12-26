package com.example.myapplication.ui.dao;

import com.example.myapplication.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    protected Connection getConnection() throws SQLException {
        String url = "jdbc:h2:mem:test";
        String user = BuildConfig.DB_USER;
        String passwd = BuildConfig.DB_PASSWORD;
        return DriverManager.getConnection(url, user, passwd);
    }
}
