package com.example.myapplication.ui.dao;

import com.example.myapplication.ui.models.Video;

import java.sql.SQLException;

public interface IVideoDao {
    Video findVideoById(Integer id) throws SQLException;
}
