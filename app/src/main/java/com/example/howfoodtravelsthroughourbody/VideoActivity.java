package com.example.howfoodtravelsthroughourbody;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        btnBack = findViewById(R.id.btnBack);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.digestion_video);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(false);
            mp.setVolume(1.0f, 1.0f);
            videoView.start();
            Toast.makeText(this, "Video is playing.", Toast.LENGTH_SHORT).show();
        });

        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(this, "Video cannot play. Try another MP4 file.", Toast.LENGTH_LONG).show();
            return true;
        });

        btnBack.setOnClickListener(v -> finish());
    }
}