package com.example.myapplication.ui.upload_video;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentUploadVideoBinding;
import com.example.myapplication.ui.dao.VideoDao;
import com.example.myapplication.ui.models.Video;

import java.sql.SQLException;

public class UploadVideoFragment extends Fragment {
    private final VideoDao videoDao;

    private FragmentUploadVideoBinding fragmentUploadVideoBinding;
    private ActivityResultLauncher<String> mGetContent;

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
        fragmentUploadVideoBinding.videoContent.setOnClickListener(this::onUploadVideo);
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            VideoView videoView = fragmentUploadVideoBinding.video;
            videoView.setVisibility(View.VISIBLE);

            fragmentUploadVideoBinding.videoContent.setVisibility(View.GONE);

            videoView.setVideoURI(result);
        });

        return fragmentUploadVideoBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void onUploadVideo(View view) {
        mGetContent.launch("video/*");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void onSubmit(View view) {
        String message;

        try {
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