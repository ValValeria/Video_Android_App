package com.example.myapplication.ui.dao;

import com.example.myapplication.models.Video;

import java.sql.SQLException;
import java.util.List;

public interface IVideoDao {
    Video findVideoById(Integer id) throws SQLException;
    List<Video> findVideos() throws SQLException;
}
