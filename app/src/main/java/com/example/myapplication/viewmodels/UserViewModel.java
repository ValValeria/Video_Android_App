package com.example.myapplication.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.services.UserService;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<UserService> userMutableLiveData = new MutableLiveData<>();

    public UserViewModel() {
        super();

        userMutableLiveData.setValue(new UserService());
    }

    public MutableLiveData<UserService> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
