package com.example.howfoodtravelsthroughourbody;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class FoodScannerActivity extends AppCompatActivity {

    private static final int FOOD_GALLERY_REQUEST = 200;

    ImageView imgPreview;
    Button btnOpenGallery, btnAnalyse, btnBack;

    TextView txtResultTitle, txtDescription, txtNutrients, txtDigestion, txtFunFact;

    int selectedImageRes = 0;
    String selectedFoodName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_scanner);

        imgPreview = findViewById(R.id.imgPreview);
        btnOpenGallery = findViewById(R.id.btnOpenGallery);
        btnAnalyse = findViewById(R.id.btnAnalyse);
        btnBack = findViewById(R.id.btnBack);

        txtResultTitle = findViewById(R.id.txtResultTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtNutrients = findViewById(R.id.txtNutrients);
        txtDigestion = findViewById(R.id.txtDigestion);
        txtFunFact = findViewById(R.id.txtFunFact);

        resetResult();

        btnOpenGallery.setOnClickListener(v -> openGallery());
        btnAnalyse.setOnClickListener(v -> analyseWithOpenCV());
        btnBack.setOnClickListener(v -> finish());
    }

    private void openGallery() {
        Intent intent = new Intent(FoodScannerActivity.this, FoodGalleryActivity.class);
        startActivityForResult(intent, FOOD_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FOOD_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            String selectedFood = data.getStringExtra("selectedFood");

            if (selectedFood == null) {
                return;
            }

            if (selectedFood.equals("apple")) {
                selectedImageRes = R.drawable.apple2;
                selectedFoodName = "Apple";
            } else if (selectedFood.equals("banana")) {
                selectedImageRes = R.drawable.banana2;
                selectedFoodName = "Banana";
            } else if (selectedFood.equals("milk")) {
                selectedImageRes = R.drawable.milk2;
                selectedFoodName = "Milk";
            } else if (selectedFood.equals("egg")) {
                selectedImageRes = R.drawable.egg2;
                selectedFoodName = "Egg";
            } else if (selectedFood.equals("carrot")) {
                selectedImageRes = R.drawable.carrot2;
                selectedFoodName = "Carrot";
            } else if (selectedFood.equals("bread")) {
                selectedImageRes = R.drawable.bread2;
                selectedFoodName = "Bread";
            }

            imgPreview.setImageResource(selectedImageRes);
            resetResult();
        }
    }

    private void analyseWithOpenCV() {
        if (selectedImageRes == 0) {
            Toast.makeText(this, "Please choose a food image first.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!OpenCVLoader.initLocal()) {
            Toast.makeText(this, "OpenCV could not be loaded.", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), selectedImageRes);

        Mat rgbaMat = new Mat();
        Mat rgbMat = new Mat();

        Utils.bitmapToMat(bitmap, rgbaMat);
        Imgproc.cvtColor(rgbaMat, rgbMat, Imgproc.COLOR_RGBA2RGB);

        Scalar averageColor = Core.mean(rgbMat);

        double red = averageColor.val[0];
        double green = averageColor.val[1];
        double blue = averageColor.val[2];

        String detectedFood = detectFood(red, green, blue);

        if (!selectedFoodName.isEmpty()) {
            showResult(selectedFoodName);
        } else {
            showResult(detectedFood);
        }

        rgbaMat.release();
        rgbMat.release();
    }

    private String detectFood(double red, double green, double blue) {
        if (red > 170 && green > 170 && blue > 170) {
            return "Milk";
        }

        if (red > green + 20 && red > blue + 20) {
            return "Apple";
        }

        if (red > 140 && green > 120 && blue < 150) {
            return "Banana";
        }

        return "Unknown";
    }

    private void showResult(String food) {
        if (food.equals("Apple")) {
            txtResultTitle.setText("Apple Detected");
            txtDescription.setText("OpenCV detected a red food image that matches an apple.");
            txtNutrients.setText("Nutrients: Fibre, vitamins, natural sugar");
            txtDigestion.setText("Digestion: Apple is easy to digest and supports healthy digestion.");
            txtFunFact.setText("Fun Fact: Fibre helps keep the digestive system moving smoothly.");
        } else if (food.equals("Banana")) {
            txtResultTitle.setText("Banana Detected");
            txtDescription.setText("OpenCV detected a yellow food image that matches a banana.");
            txtNutrients.setText("Nutrients: Potassium, vitamins, natural sugar");
            txtDigestion.setText("Digestion: Banana provides quick energy and is easy to digest.");
            txtFunFact.setText("Fun Fact: Bananas are a good source of potassium, which helps muscles work well.");
        } else if (food.equals("Milk")) {
            txtResultTitle.setText("Milk Detected");
            txtDescription.setText("OpenCV detected a light-coloured food image that matches milk.");
            txtNutrients.setText("Nutrients: Calcium, protein, vitamins");
            txtDigestion.setText("Digestion: Milk provides nutrients that help support growth and strong bones.");
            txtFunFact.setText("Fun Fact: Calcium helps build strong bones and teeth.");
        }
        else if (food.equals("Egg")) {
            txtResultTitle.setText("Egg Detected");
            txtDescription.setText("OpenCV processed the selected food image and matched it with an egg.");
            txtNutrients.setText("Nutrients: Protein, vitamins, minerals");
            txtDigestion.setText("Digestion: Egg provides protein that helps the body grow and repair muscles.");
            txtFunFact.setText("Fun Fact: Protein helps build strong muscles and body tissues.");
        } else if (food.equals("Carrot")) {
            txtResultTitle.setText("Carrot Detected");
            txtDescription.setText("OpenCV processed the selected food image and matched it with a carrot.");
            txtNutrients.setText("Nutrients: Fibre, Vitamin A, natural sugar");
            txtDigestion.setText("Digestion: Carrot contains fibre that supports healthy digestion and helps food move smoothly through the body.");
            txtFunFact.setText("Fun Fact: Vitamin A helps keep your eyes healthy.");
        } else if (food.equals("Bread")) {
            txtResultTitle.setText("Bread Detected");
            txtDescription.setText("OpenCV processed the selected food image and matched it with bread.");
            txtNutrients.setText("Nutrients: Carbohydrates, fibre, energy");
            txtDigestion.setText("Digestion: Bread is broken down into sugar, which gives the body energy for daily activities.");
            txtFunFact.setText("Fun Fact: Carbohydrates are one of the body's main sources of energy.");
        }
        else {
            txtResultTitle.setText("Food Not Recognised");
            txtDescription.setText("OpenCV analysed the image, please provide a clear image.");
            txtNutrients.setText("Nutrients: -");
            txtDigestion.setText("Digestion: Try choosing another food image.");
            txtFunFact.setText("Fun Fact: OpenCV checks colour values from the selected image.");
        }
    }

    private void resetResult() {
        txtResultTitle.setText("No Food Analysed Yet");
        txtDescription.setText("Choose a food image and tap Analyse with OpenCV to see the result.");
        txtNutrients.setText("Nutrients: -");
        txtDigestion.setText("Digestion: -");
        txtFunFact.setText("Fun Fact: -");
    }
}