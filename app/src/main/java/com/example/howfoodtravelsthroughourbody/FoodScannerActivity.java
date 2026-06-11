package com.example.howfoodtravelsthroughourbody;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodScannerActivity extends AppCompatActivity {

    TextView title, description;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        title = findViewById(R.id.txtPlaceholderTitle);
        description = findViewById(R.id.txtPlaceholderDescription);
        btnBack = findViewById(R.id.btnBack);

        title.setText("Food Scanner");
        description.setText("OpenCV food scanning feature will be implemented later.");

        btnBack.setOnClickListener(v -> finish());
    }
}