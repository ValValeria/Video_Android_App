package com.example.myapplication.ui.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.ui.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao implements IUserDao {
    @Override
    public List<User> findUsers() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from java_android_users order by id");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();

            while (resultSet.next()) {
                userList.add(setUpResultSet(resultSet));
            }

            return userList;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public User findUser(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from java_android_users where id = ?");
            preparedStatement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;

            while (resultSet.next()) {
                user = setUpResultSet(resultSet);
            }

            return user;
        }
    }

    @Override
    public User setUpResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));

        return user;
    }

    public void deleteUser(String username) throws SQLException {
       Connection connection = getConnection();
       PreparedStatement preparedStatement = connection.prepareStatement("delete from java_android_users where username = ?");
       preparedStatement.setString(1, username);

       preparedStatement.executeUpdate();
    }
}
