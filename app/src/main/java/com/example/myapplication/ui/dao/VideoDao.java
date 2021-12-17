package com.example.myapplication.ui.dao;

import com.example.myapplication.ui.models.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoDao extends BaseDao implements IVideoDao{
    @Override
    public Video findVideoById(Integer id) throws SQLException {
        Video video = null;

        try(Connection connection = this.getConnection()) {
            PreparedStatement preparedVideoStatement = connection.prepareStatement("select * from videos where id = ?");
            preparedVideoStatement.setInt(1, id);

            boolean hasResults = preparedVideoStatement.execute();

            if (hasResults) {
                ResultSet resultSet = preparedVideoStatement.getResultSet();
                resultSet.next();

                video = new Video();
                video.setId(resultSet.getInt("id"));
                video.setTitle(resultSet.getString("title"));
                video.setLikes(resultSet.getInt("likes"));
                video.setPath(resultSet.getString("path"));

                Integer userId = resultSet.getInt("user_id");

                PreparedStatement preparedUserStatement = connection.prepareStatement("select * from users where id = ?");
            }
        }

        return video;
    }
}
