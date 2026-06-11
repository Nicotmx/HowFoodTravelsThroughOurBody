package com.example.howfoodtravelsthroughourbody;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodJourneyActivity extends AppCompatActivity {

    FrameLayout animationArea;
    ImageView imgApple;
    TextView txtStage, txtDescription, txtProgress;
    Button btnStart, btnPause, btnReset, btnBack;

    ValueAnimator animator;

    int currentStage = 0;
    boolean isPaused = false;
    boolean journeyComplete = false;

    float currentX = 0f;
    float currentY = 0f;

    String[] stageTitles = {
            "Current Stage: Mouth",
            "Current Stage: Esophagus",
            "Current Stage: Stomach",
            "Current Stage: Small Intestine",
            "Current Stage: Large Intestine"
    };

    String[] stageDescriptions = {
            "Food enters the mouth. Teeth break it into small pieces.",
            "Food travels down the esophagus to the stomach.",
            "The stomach mixes food with digestive juices.",
            "Nutrients are absorbed into the body here.",
            "Water is absorbed and waste is prepared to leave the body."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_journey);

        animationArea = findViewById(R.id.animationArea);
        imgApple = findViewById(R.id.imgApple);

        txtStage = findViewById(R.id.txtStage);
        txtDescription = findViewById(R.id.txtDescription);
        txtProgress = findViewById(R.id.txtProgress);

        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);

        animationArea.post(this::resetJourney);

        btnStart.setOnClickListener(v -> startJourney());
        btnPause.setOnClickListener(v -> pauseOrContinueJourney());

        btnReset.setOnClickListener(v -> resetJourney());
        btnBack.setOnClickListener(v -> finish());
    }

    private void startJourney() {
        if (journeyComplete) {
            resetJourney();
        }

        if (animator != null && animator.isRunning()) {
            return;
        }

        if (isPaused) {
            return;
        }

        if (currentStage < 4) {
            animateToStage(currentStage + 1);
        }
    }


    private void pauseJourney() {
        if (animator != null && animator.isRunning()) {
            animator.pause();
            isPaused = true;
        }
    }

    private void resetJourney() {
        if (animator != null) {
            animator.cancel();
        }

        currentStage = 0;
        journeyComplete = false;
        isPaused = false;
        btnPause.setText("Pause");
        isPaused = false;

        float[] mouthPosition = getStagePosition(0);
        currentX = mouthPosition[0];
        currentY = mouthPosition[1];

        imgApple.setTranslationX(currentX);
        imgApple.setTranslationY(currentY);

        updateStageText();
    }

    private void pauseOrContinueJourney() {
        if (animator == null) {
            return;
        }

        if (isPaused) {
            animator.resume();
            isPaused = false;
            btnPause.setText("Pause");
        } else if (animator.isRunning()) {
            animator.pause();
            isPaused = true;
            btnPause.setText("Continue");
        }
    }

    private void animateToStage(int nextStage) {
        float[] nextPosition = getStagePosition(nextStage);
        float startX = currentX;
        float startY = currentY;
        float endX = nextPosition[0];
        float endY = nextPosition[1];

        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1500);

        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();

            currentX = startX + ((endX - startX) * value);
            currentY = startY + ((endY - startY) * value);

            imgApple.setTranslationX(currentX);
            imgApple.setTranslationY(currentY);
        });

        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                if (!isPaused) {
                    currentStage = nextStage;
                    updateStageText();

                    if (currentStage < 4) {
                        animateToStage(currentStage + 1);
                    } else {
                        journeyComplete = true;
                        btnPause.setText("Pause");
                        txtDescription.setText("Journey Complete! Food has travelled through the digestive system.");
                    }
                }
            }
        });

        animator.start();
    }

    private float[] getStagePosition(int stage) {
        int areaWidth = animationArea.getWidth();
        int areaHeight = animationArea.getHeight();

        float appleHalf = imgApple.getWidth() / 2f;

        float x;
        float y;

        switch (stage) {
            case 0: // Mouth
                x = areaWidth * 0.45f;
                y = areaHeight * 0.15f;
                break;
            case 1: // Esophagus
                x = areaWidth * 0.52f;
                y = areaHeight * 0.35f;
                break;
            case 2: // Stomach
                x = areaWidth * 0.60f;
                y = areaHeight * 0.52f;
                break;
            case 3: // Small Intestine
                x = areaWidth * 0.50f;
                y = areaHeight * 0.75f;
                break;
            case 4: // Large Intestine
                x = areaWidth * 0.60f;
                y = areaHeight * 0.80f;
                break;
            default:
                x = areaWidth * 0.45f;
                y = areaHeight * 0.15f;
        }

        return new float[]{x - appleHalf, y - appleHalf};
    }

    private void updateStageText() {
        txtStage.setText(stageTitles[currentStage]);
        txtDescription.setText(stageDescriptions[currentStage]);
        txtProgress.setText("Stage " + (currentStage + 1) + " / 5");
    }
}