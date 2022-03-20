package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.adapters.VideoAdapter;
import com.example.myapplication.ui.dao.UserDao;
import com.example.myapplication.ui.dao.VideoDao;
import com.example.myapplication.ui.models.Video;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private VideoAdapter videoAdapter;

    private final VideoDao videoDao;
    private final UserDao userDao;
    private final ArrayList<Video> arrayList;

    public HomeFragment() {
        videoDao = new VideoDao();
        userDao = new UserDao();
        arrayList = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            try {
                this.arrayList.addAll(videoDao.findVideos());

                videoAdapter = new VideoAdapter(requireContext(), R.layout.fragment_video2, this.arrayList);

                this.requireView().post(() -> {
                    binding.videosList.setAdapter(this.videoAdapter);

                    if (this.arrayList.size() == 0) {
                       View noResultView = getLayoutInflater().inflate(R.layout.no_result, binding.noResults, false);

                       binding.noResults.addView(noResultView);
                       binding.noResults.invalidate();
                    }
                });
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        };

        executor.execute(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}