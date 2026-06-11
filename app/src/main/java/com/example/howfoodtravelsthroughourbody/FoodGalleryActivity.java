package com.example.howfoodtravelsthroughourbody;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FoodGalleryActivity extends AppCompatActivity {

    CardView cardApple, cardBanana, cardMilk, cardEgg, cardCarrot, cardBread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_gallery);

        cardApple = findViewById(R.id.cardApple);
        cardBanana = findViewById(R.id.cardBanana);
        cardMilk = findViewById(R.id.cardMilk);
        cardEgg = findViewById(R.id.cardEgg);
        cardCarrot = findViewById(R.id.cardCarrot);
        cardBread = findViewById(R.id.cardBread);

        cardApple.setOnClickListener(v -> selectFood("apple"));
        cardBanana.setOnClickListener(v -> selectFood("banana"));
        cardMilk.setOnClickListener(v -> selectFood("milk"));
        cardEgg.setOnClickListener(v -> selectFood("egg"));
        cardCarrot.setOnClickListener(v -> selectFood("carrot"));
        cardBread.setOnClickListener(v -> selectFood("bread"));
    }

    private void selectFood(String foodName) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedFood", foodName);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}