package com.example.howfoodtravelsthroughourbody;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    CardView btnLearn, btnActivities, btnQuiz, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLearn = findViewById(R.id.btnLearn);
        btnActivities = findViewById(R.id.btnActivities);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnAbout = findViewById(R.id.btnFoodScanner);

        // Learn Screen
        btnLearn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LearnActivity.class);
            startActivity(intent);
        });

        // Activities Screen (later)
        btnActivities.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ActivitiesMenuActivity.class);
            startActivity(intent);
        });

        // Quiz Screen (later)
        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        // Food Scanner Screen
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FoodScannerActivity.class);
            startActivity(intent);
        });
    }
}