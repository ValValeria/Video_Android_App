package com.example.myapplication.dao;

import com.example.myapplication.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    public Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            String url = "jdbc:mysql://remotemysql.com:3306/".concat(BuildConfig.DB_NAME);

            Class.forName("com.mysql.jdbc.Driver");

            String user = BuildConfig.DB_USER;
            String passwd = BuildConfig.DB_PASSWORD;
            connection = DriverManager.getConnection(url, user, passwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
