package com.example.myapplication.ui.dao;

import android.util.Log;

import com.example.myapplication.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://remotemysql.com/".concat(BuildConfig.DB_NAME);

        Log.i("BaseDao", url);

        String user = BuildConfig.DB_USER;
        String passwd = BuildConfig.DB_PASSWORD;
        return DriverManager.getConnection(url, user, passwd);
    }
}
