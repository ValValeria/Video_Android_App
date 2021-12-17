package com.example.myapplication.ui.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    protected Connection getConnection() throws SQLException {
        String url = "jdbc:h2:mem:test";
        String user = "";
        String passwd = "";
        return DriverManager.getConnection(url, user, passwd);
    }
}
