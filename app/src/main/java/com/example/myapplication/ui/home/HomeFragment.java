package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    public void onStart() {
        super.onStart();

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            try {
                this.arrayList.addAll(videoDao.findVideos());

                videoAdapter = new VideoAdapter(requireContext(), R.layout.fragment_video2, this.arrayList);

                this.requireView().post(() -> {
                    binding.videosList.setAdapter(this.videoAdapter);

                    if (this.arrayList.size() == 0) {
                        addNoResults(false);
                    }
                });
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                addNoResults(true);
            }
        };

        executor.execute(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addNoResults(boolean isError) {
        LinearLayout listView = binding.noResults;
        View noResultView = getLayoutInflater().inflate(R.layout.no_result, binding.noResults, false);

        if (isError) {
            TextView textView = noResultView.findViewById(R.id.title);
            textView.setText(R.string.sql_error);
        }

        listView.addView(noResultView);
        listView.invalidate();
    }
}