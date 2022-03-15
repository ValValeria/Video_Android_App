package com.example.myapplication.ui.services;

import com.example.myapplication.ui.models.User;

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
