package com.example.myapplication.ui.dao;

import com.example.myapplication.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    List<User> findUsers() throws SQLException;
    User findUser(Long id) throws SQLException;
    User setUpResultSet(ResultSet resultSet) throws SQLException;
}
