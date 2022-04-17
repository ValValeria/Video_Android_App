package com.example.myapplication.ui.search;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.dao.VideoDao;
import com.example.myapplication.models.Video;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchFragment extends Fragment {
    private final ArrayList<Video> arrayList;
    private final VideoDao videoDao;
    private final ExecutorService executorService;
    private final Handler handler;

    public SearchFragment() {
        super();

        this.arrayList = new ArrayList<>();
        this.videoDao = new VideoDao();
        this.executorService = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(
       LayoutInflater inflater,
       ViewGroup container,
       Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.executorService.submit(this::setVideosUI);
    }

    private void setVideosUI() {
        final LinearLayout layout = this.requireActivity().findViewById(R.id.search_items);

        try{
            arrayList.addAll(this.videoDao.findVideos());

            this.handler.post(() -> {
                if (arrayList.size() == 0) {
                    LayoutInflater layoutInflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.no_result, layout, false);

                    layout.addView(view);
                    layout.invalidate();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}