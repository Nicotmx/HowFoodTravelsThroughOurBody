package com.example.howfoodtravelsthroughourbody;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class OrganExploreActivity extends AppCompatActivity {

    CardView cardMouth, cardEsophagus, cardStomach, cardSmallIntestine, cardLargeIntestine;
    TextView txtOrganTitle, txtOrganDescription, txtProgress, txtSuccess;
    Button btnBack;

    boolean mouthDiscovered = false;
    boolean esophagusDiscovered = false;
    boolean stomachDiscovered = false;
    boolean smallIntestineDiscovered = false;
    boolean largeIntestineDiscovered = false;

    int discoveredCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organ_explore);

        cardMouth = findViewById(R.id.cardMouth);
        cardEsophagus = findViewById(R.id.cardEsophagus);
        cardStomach = findViewById(R.id.cardStomach);
        cardSmallIntestine = findViewById(R.id.cardSmallIntestine);
        cardLargeIntestine = findViewById(R.id.cardLargeIntestine);

        txtOrganTitle = findViewById(R.id.txtOrganTitle);
        txtOrganDescription = findViewById(R.id.txtOrganDescription);
        txtProgress = findViewById(R.id.txtProgress);
        txtSuccess = findViewById(R.id.txtSuccess);
        btnBack = findViewById(R.id.btnBack);

        cardMouth.setOnClickListener(v -> discoverOrgan(
                "Mouth Found!",
                "Your mouth is the first stop in the food journey. Your teeth crush food into tiny pieces, and saliva helps make it soft and easy to swallow.",
                "mouth"
        ));

        cardEsophagus.setOnClickListener(v -> discoverOrgan(
                "Esophagus Found!",
                "The esophagus is like a food slide. It pushes food from your mouth down to your stomach.",
                "esophagus"
        ));

        cardStomach.setOnClickListener(v -> discoverOrgan(
                "Stomach Found!",
                "The stomach is like a food mixer. It squeezes and mixes food with digestive juices to break it down.",
                "stomach"
        ));

        cardSmallIntestine.setOnClickListener(v -> discoverOrgan(
                "Small Intestine Found!",
                "The small intestine is where your body collects nutrients from food. These nutrients give you energy and help you grow.",
                "small_intestine"
        ));

        cardLargeIntestine.setOnClickListener(v -> discoverOrgan(
                "Large Intestine Found!",
                "The large intestine absorbs water from leftover food. Then it prepares the waste to leave the body.",
                "large_intestine"
        ));

        btnBack.setOnClickListener(v -> finish());
    }

    private void discoverOrgan(String title, String description, String organ) {
        txtOrganTitle.setText(title);
        txtOrganDescription.setText(description);

        if (organ.equals("mouth") && !mouthDiscovered) {
            mouthDiscovered = true;
            discoveredCount++;
            cardMouth.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (organ.equals("esophagus") && !esophagusDiscovered) {
            esophagusDiscovered = true;
            discoveredCount++;
            cardEsophagus.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (organ.equals("stomach") && !stomachDiscovered) {
            stomachDiscovered = true;
            discoveredCount++;
            cardStomach.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (organ.equals("small_intestine") && !smallIntestineDiscovered) {
            smallIntestineDiscovered = true;
            discoveredCount++;
            cardSmallIntestine.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (organ.equals("large_intestine") && !largeIntestineDiscovered) {
            largeIntestineDiscovered = true;
            discoveredCount++;
            cardLargeIntestine.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }

        txtProgress.setText("Discovered: " + discoveredCount + "/5");

        if (discoveredCount == 5) {
            txtSuccess.setText("Great job! You discovered all digestive organs!");
        }
    }
}