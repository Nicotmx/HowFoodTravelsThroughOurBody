package com.example.howfoodtravelsthroughourbody;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SimulatorActivity extends AppCompatActivity {

    ImageView imgApple, imgBurger, imgMilk, imgBoy;
    TextView txtResultTitle, txtDescription, txtNutrients, txtSpeed, txtFunFact;
    Button btnReset, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        imgApple = findViewById(R.id.imgApple);
        imgBurger = findViewById(R.id.imgBurger);
        imgMilk = findViewById(R.id.imgMilk);
        imgBoy = findViewById(R.id.imgBoy);

        txtResultTitle = findViewById(R.id.txtResultTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtNutrients = findViewById(R.id.txtNutrients);
        txtSpeed = findViewById(R.id.txtSpeed);
        txtFunFact = findViewById(R.id.txtFunFact);

        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);

        setupDrag(imgApple, "apple");
        setupDrag(imgBurger, "burger");
        setupDrag(imgMilk, "milk");

        setupBoyDropTarget();

        btnReset.setOnClickListener(v -> resetSimulation());
        btnBack.setOnClickListener(v -> finish());

        resetSimulation();
    }

    private void setupDrag(ImageView foodImage, String foodName) {
        foodImage.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("food", foodName);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            return true;
        });
    }

    private void setupBoyDropTarget() {
        imgBoy.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    String foodName = event.getClipData().getItemAt(0).getText().toString();
                    showFoodResult(foodName);
                    Toast.makeText(this, foodName + " dropped! Starting digestion simulation...", Toast.LENGTH_SHORT).show();
                    return true;

                default:
                    return true;
            }
        });
    }

    private void showFoodResult(String foodName) {
        if (foodName.equals("apple")) {
            txtResultTitle.setText("Apple Digestion");
            txtDescription.setText("The apple enters the mouth first. Teeth break it into small pieces, then it travels to the stomach. In the small intestine, the body absorbs vitamins and fibre from the apple.");
            txtNutrients.setText("Nutrients: Fibre, vitamins, natural sugar");
            txtSpeed.setText("Digestion Speed: Easy to digest");
            txtFunFact.setText("Fun Fact: Fibre helps keep your digestive system healthy.");
        } else if (foodName.equals("burger")) {
            txtResultTitle.setText("Burger Digestion");
            txtDescription.setText("The burger is chewed in the mouth and moves to the stomach. The stomach works harder to break down protein and fat. Fatty food usually takes longer to digest.");
            txtNutrients.setText("Nutrients: Protein, fat, carbohydrates");
            txtSpeed.setText("Digestion Speed: Slower to digest");
            txtFunFact.setText("Fun Fact: Your stomach may take more time to digest food that contains more fat.");
        } else if (foodName.equals("milk")) {
            txtResultTitle.setText("Milk Digestion");
            txtDescription.setText("Milk travels through the digestive system after being swallowed. The body absorbs calcium and protein, which help support growth and strong bones.");
            txtNutrients.setText("Nutrients: Calcium, protein, vitamins");
            txtSpeed.setText("Digestion Speed: Moderate");
            txtFunFact.setText("Fun Fact: Calcium helps build strong bones and teeth.");
        }
    }

    private void resetSimulation() {
        txtResultTitle.setText("No Food Selected");
        txtDescription.setText("Drag a food item to the boy to begin the digestion simulation.");
        txtNutrients.setText("Nutrients: -");
        txtSpeed.setText("Digestion Speed: -");
        txtFunFact.setText("Fun Fact: -");

        imgApple.setVisibility(View.VISIBLE);
        imgBurger.setVisibility(View.VISIBLE);
        imgMilk.setVisibility(View.VISIBLE);
    }
}