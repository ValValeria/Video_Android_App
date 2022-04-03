package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.models.Video;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {
    private final List<Video> videoList;
    private final int resource;
    private final LayoutInflater layoutInflater;

    public VideoAdapter(@NonNull Context context, int resource, @NonNull List<Video> objects) {
        super(context, resource, objects);

        this.videoList = objects;
        this.resource = resource;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Video video = this.videoList.get(position);
        @SuppressLint("ViewHolder") View view = this.layoutInflater.inflate(this.resource, parent, false);

        TextView titleTextView = view.findViewById(R.id.titleVideo);
        titleTextView.setText(video.getTitle());

        return view;
    }
}
