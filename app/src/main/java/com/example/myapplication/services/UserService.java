package com.example.myapplication.services;

import com.example.myapplication.models.User;

public class UserService {
    private User user = new User();
    private boolean isAuth = false;

    public User getUser() {
        return user;
    }

    public boolean isAuth() {
        return isAuth;
    }
}
