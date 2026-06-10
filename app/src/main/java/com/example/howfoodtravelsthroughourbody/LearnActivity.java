package com.example.howfoodtravelsthroughourbody;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LearnActivity extends AppCompatActivity {

    ImageButton btnMouthAudio, btnEsophagusAudio, btnStomachAudio;
    ImageButton btnSmallIntestineAudio, btnLargeIntestineAudio;
    Button btnBackHome;
    CardView btnWatchVideo;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        btnMouthAudio = findViewById(R.id.btnMouthAudio);
        btnEsophagusAudio = findViewById(R.id.btnEsophagusAudio);
        btnStomachAudio = findViewById(R.id.btnStomachAudio);
        btnSmallIntestineAudio = findViewById(R.id.btnSmallIntestineAudio);
        btnLargeIntestineAudio = findViewById(R.id.btnLargeIntestineAudio);
        btnWatchVideo = findViewById(R.id.btnWatchVideo);
        btnBackHome = findViewById(R.id.btnBackHome);

        btnMouthAudio.setOnClickListener(v -> {
            Toast.makeText(this, "Mouth audio clicked", Toast.LENGTH_SHORT).show();
            playAudio(R.raw.mouth_audio);
        });
        btnEsophagusAudio.setOnClickListener(v -> playAudio(R.raw.esophagus_audio));
        btnStomachAudio.setOnClickListener(v -> playAudio(R.raw.stomach_audio));
        btnSmallIntestineAudio.setOnClickListener(v -> playAudio(R.raw.small_intestine_audio));
        btnLargeIntestineAudio.setOnClickListener(v -> playAudio(R.raw.large_intestine_audio));

        btnWatchVideo.setOnClickListener(v -> {
            Intent intent = new Intent(LearnActivity.this, VideoActivity.class);
            startActivity(intent);
        });

        btnBackHome.setOnClickListener(v -> finish());
    }

    private void playAudio(int audioResId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(this, audioResId);

        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                mediaPlayer = null;
            });
        } else {
            Toast.makeText(this, "Audio cannot play", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}