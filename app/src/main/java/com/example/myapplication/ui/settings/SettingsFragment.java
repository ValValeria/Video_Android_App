package com.example.myapplication.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.dao.VideoDao;
import com.example.myapplication.viewmodels.UserViewModel;

import java.sql.SQLException;

public class SettingsFragment extends PreferenceFragmentCompat {
    private final VideoDao videoDao;
    private final UserDao userDao;

    private UserViewModel userViewModel;

    public SettingsFragment() {
        videoDao = new VideoDao();
        userDao = new UserDao();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        PreferenceManager.setDefaultValues(requireActivity(), R.xml.settings, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean shouldDeleteUserProfile = sharedPreferences.getBoolean("delete_user", false);
        boolean shouldDeleteAllVideos = sharedPreferences.getBoolean("delete_videos", false);

        try {
            if (shouldDeleteUserProfile) {
                userDao.deleteUser("");
            } else if (shouldDeleteAllVideos) {
                videoDao.deleteVideosByUser("");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
