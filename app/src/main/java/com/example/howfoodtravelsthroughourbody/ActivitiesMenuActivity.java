package com.example.howfoodtravelsthroughourbody;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivitiesMenuActivity extends AppCompatActivity {

    CardView cardOrganExplorer, cardFoodJourney, cardDragDrop, cardSimulator;
    Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_menu);

        cardOrganExplorer = findViewById(R.id.cardOrganExplorer);
        cardFoodJourney = findViewById(R.id.cardFoodJourney);
        cardDragDrop = findViewById(R.id.cardDragDrop);
        cardSimulator = findViewById(R.id.cardSimulator);
        btnBackHome = findViewById(R.id.btnBackHome);

        cardOrganExplorer.setOnClickListener(v -> {
            startActivity(new Intent(ActivitiesMenuActivity.this, OrganExploreActivity.class));
        });

        cardFoodJourney.setOnClickListener(v -> {
            startActivity(new Intent(ActivitiesMenuActivity.this, FoodJourneyActivity.class));
        });

        cardDragDrop.setOnClickListener(v -> {
            startActivity(new Intent(ActivitiesMenuActivity.this, DragDropActivity.class));
        });

        cardSimulator.setOnClickListener(v -> {
            startActivity(new Intent(ActivitiesMenuActivity.this, SimulatorActivity.class));
        });

        btnBackHome.setOnClickListener(v -> finish());
    }
}