package com.example.myapplication.ui.dao;

import com.example.myapplication.ui.models.User;
import com.example.myapplication.ui.models.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VideoDao extends BaseDao implements IVideoDao{
    @Override
    public Video findVideoById(Integer id) throws SQLException {
        Video video = null;

        try(Connection connection = this.getConnection()) {
            PreparedStatement preparedVideoStatement = connection.prepareStatement("select * from java_android_videos where id = ?");
            preparedVideoStatement.setInt(1, id);

            boolean hasResults = preparedVideoStatement.execute();

            if (hasResults) {
                ResultSet resultSet = preparedVideoStatement.getResultSet();
                resultSet.next();

                video = setupResultSet(resultSet);
            }
        }

        return video;
    }

    @Override
    public List<Video> findVideos() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedVideoStatement = connection.prepareStatement("select * from java_android_videos");
            ResultSet resultSet = preparedVideoStatement.executeQuery();
            List<Video> videoList = new ArrayList<>();

            while(resultSet.next()) {
                videoList.add(setupResultSet(resultSet));
            }

            return videoList;
        }
    }

    private Video setupResultSet(ResultSet resultSet) throws SQLException {
        Video video = new Video();
        video.setId(resultSet.getInt("id"));
        video.setTitle(resultSet.getString("title"));
        video.setLikes(resultSet.getInt("likes"));
        video.setPath(resultSet.getString("path"));
        video.setCreatedAt(resultSet.getDate("created_at"));

        Integer userId = resultSet.getInt("user_id");

        PreparedStatement preparedUserStatement = getConnection().prepareStatement("select * from java_android_users where id = ?");
        ResultSet resultUserSet = preparedUserStatement.executeQuery();

        User user = new User();
        user.setId(userId);
        user.setUsername(resultUserSet.getString("username"));
        user.setEmail(resultUserSet.getString("email"));
        user.setPassword(resultUserSet.getString("password"));

        video.setAuthor(user);

        return video;
    }

    public void saveVideo(Video video) throws SQLException {
        Statement statement = getConnection().createStatement();
    }
}
