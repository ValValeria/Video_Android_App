package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.dao.UserDao;
import com.example.myapplication.ui.dao.VideoDao;
import com.example.myapplication.ui.models.Video;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayAdapter<Video> arrayAdapter;

    private final VideoDao videoDao;
    private final UserDao userDao;

    public HomeFragment() {
        videoDao = new VideoDao();
        userDao = new UserDao();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = binding.videosList;
        listView.setAdapter(this.arrayAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}