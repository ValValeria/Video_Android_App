package com.example.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.adapters.VideoAdapter;
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
    private final ArrayList<Video> arrayList;

    public HomeFragment() {
        videoDao = new VideoDao();
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

        new DownloadVideos().execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadVideos extends AsyncTask<Integer, Integer, List<Video>> {

        @Override
        protected void onPostExecute(List<Video> videos) {
            if (arrayList.size() == 0) {
                addNoResults(false);
            } else {
                removeNoResults();
            }
        }

        @Override
        protected List<Video> doInBackground(Integer... integers) {
            try {
                arrayList.addAll(videoDao.findVideos());

                Handler handler = new Handler(Looper.getMainLooper());
                Executor executor = Executors.newSingleThreadExecutor();

                handler.post(() -> {
                    videoAdapter = new VideoAdapter(requireContext(), R.layout.fragment_video2, arrayList);
                    binding.videosList.setAdapter(videoAdapter);
                });
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                addNoResults(true);
            }

            return arrayList;
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

        private void removeNoResults() {
            LinearLayout linearLayout = binding.noResults;
            linearLayout.removeAllViews();
            linearLayout.invalidate();
        }
    }
}