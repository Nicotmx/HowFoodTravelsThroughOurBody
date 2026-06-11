package com.example.howfoodtravelsthroughourbody;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DragDropActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        TextView title = findViewById(R.id.txtPlaceholderTitle);
        Button btnBack = findViewById(R.id.btnBack);

        title.setText("Drag & Drop Challenge");
        btnBack.setOnClickListener(v -> finish());
    }
}