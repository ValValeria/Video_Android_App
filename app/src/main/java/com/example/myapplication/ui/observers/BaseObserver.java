package com.example.myapplication.ui.observers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.myapplication.ui.dao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executors;


public class BaseObserver implements DefaultLifecycleObserver{
    private final BaseDao baseDao = new BaseDao();
    private final String TAG = BaseDao.class.getName();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Runnable runnable = () -> {
            try{
                Log.i(TAG, "Connecting to db");

                Connection connection = this.baseDao.getConnection();
                Statement statement = connection.createStatement();

                statement.executeUpdate("create table if not exists java_android_users(" +
                        "    id integer AUTO_INCREMENT," +
                        "    username varchar(30)," +
                        "    password varchar(40)," +
                        "    email varchar(40)," +
                        "    CONSTRAINT pk_id PRIMARY key(id)" +
                        ");");

                statement.executeUpdate("create table if not exists java_android_videos(" +
                        "    id integer AUTO_INCREMENT," +
                        "    title varchar(40)," +
                        "    path varchar(50)," +
                        "    author_id integer," +
                        "    created_at date," +
                        "    likes integer," +
                        "    CONSTRAINT pk_id PRIMARY key(id)" +
                        ");");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        };

        Executors.newSingleThreadExecutor().submit(runnable);
    }
}
