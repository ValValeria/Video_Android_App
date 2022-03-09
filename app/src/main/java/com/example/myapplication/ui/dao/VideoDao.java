package com.example.myapplication.ui.dao;

import com.example.myapplication.ui.models.User;
import com.example.myapplication.ui.models.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class VideoDao extends BaseDao implements IVideoDao {
    @Override
    public Video findVideoById(Integer id) throws SQLException {
        Video video = null;

        try (Connection connection = this.getConnection()) {
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

            while (resultSet.next()) {
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

        PreparedStatement preparedUserStatement = getConnection().prepareStatement("select * from java_android_users where id = ?");

        setUpUser(
                resultSet.getInt("user_id"),
                preparedUserStatement.executeQuery(),
                video
        );

        return video;
    }

    public void saveVideo(Video video) throws SQLException {
        final long time = new java.util.Date().getTime();

        String sql = "insert into java_android_videos(title, path, author, createdAt) " +
                "values(?, ?, ?, ?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, video.getTitle());
        statement.setString(2, video.getPath());
        statement.setInt(3, video.getAuthor().getId());
        statement.setDate(4, new Date(time));

        int rows = statement.executeUpdate();

        if (rows == 0) {
            throw new SQLDataException();
        }
    }

    private void setUpUser(
            Integer userId,
            ResultSet resultSet,
            Video video
    ) throws SQLException {
        User user = new User();
        user.setId(userId);
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));

        video.setAuthor(user);
    }
}
