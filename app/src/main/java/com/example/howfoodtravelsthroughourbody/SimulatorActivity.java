package com.example.howfoodtravelsthroughourbody;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SimulatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        TextView title = findViewById(R.id.txtPlaceholderTitle);
        Button btnBack = findViewById(R.id.btnBack);

        title.setText("Digestive Simulator");
        btnBack.setOnClickListener(v -> finish());
    }
}