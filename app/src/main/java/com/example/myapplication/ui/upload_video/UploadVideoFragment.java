package com.example.myapplication.ui.upload_video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentUploadVideoBinding;
import com.example.myapplication.ui.dao.VideoDao;
import com.example.myapplication.ui.models.Video;

import java.sql.SQLException;

public class UploadVideoFragment extends Fragment {
    private final VideoDao videoDao;

    private FragmentUploadVideoBinding fragmentUploadVideoBinding;

    public UploadVideoFragment() {
        videoDao = new VideoDao();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        fragmentUploadVideoBinding = FragmentUploadVideoBinding.inflate(inflater);
        fragmentUploadVideoBinding.navUploadVideo.setOnClickListener(this::onSubmit);

        return fragmentUploadVideoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void onSubmit(View view) {
        String message;

        try{
            final String title = fragmentUploadVideoBinding.titleVideo.getText().toString();
            message = "Uploaded";

            Video video = new Video();
            video.setTitle(title);

            this.videoDao.saveVideo(video);
        } catch (SQLException sqlException) {
            message = "SQL ERROR";

            sqlException.printStackTrace();
        }

        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show();
    }
}