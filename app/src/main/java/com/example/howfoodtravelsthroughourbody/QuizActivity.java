package com.example.howfoodtravelsthroughourbody;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    TextView txtProgress, txtQuestion, txtFeedback, txtScore;
    Button btnOption1, btnOption2, btnOption3, btnOption4, btnNext, btnBackHome;

    int currentQuestion = 0;
    int score = 0;
    boolean answered = false;
    ImageView imgQuestion;

    String[] questions = {
            "Where does digestion begin?",
            "Which organ moves food from the mouth to the stomach?",
            "What does the stomach do to food?",
            "Which organ absorbs most nutrients from food?",
            "What does the large intestine absorb?"
    };

    int[] questionImages = {
            R.drawable.question1,
            R.drawable.question2,
            R.drawable.question3,
            R.drawable.question4,
            R.drawable.question5
    };

    String[][] options = {
            {"Stomach", "Mouth", "Small Intestine", "Large Intestine"},
            {"Esophagus", "Small Intestine", "Large Intestine", "Teeth"},
            {"Absorbs water", "Mixes food with digestive juices", "Pumps blood", "Helps us breathe"},
            {"Mouth", "Esophagus", "Small Intestine", "Large Intestine"},
            {"Water", "Oxygen", "Sunlight", "Saliva"}
    };

    String[] correctAnswers = {
            "Mouth",
            "Esophagus",
            "Mixes food with digestive juices",
            "Small Intestine",
            "Water"
    };

    String[] correctFeedback = {
            "Correct! Digestion begins in the mouth where teeth break food into smaller pieces.",
            "Correct! The esophagus is a tube that carries food from the mouth to the stomach.",
            "Correct! The stomach mixes food with digestive juices to break it down.",
            "Correct! The small intestine absorbs nutrients that help the body grow and stay healthy.",
            "Correct! The large intestine absorbs water from leftover food."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtProgress = findViewById(R.id.txtProgress);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtFeedback = findViewById(R.id.txtFeedback);
        txtScore = findViewById(R.id.txtScore);
        imgQuestion = findViewById(R.id.imgQuestion);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        btnNext = findViewById(R.id.btnNext);
        btnBackHome = findViewById(R.id.btnBackHome);

        btnOption1.setOnClickListener(v -> checkAnswer(btnOption1));
        btnOption2.setOnClickListener(v -> checkAnswer(btnOption2));
        btnOption3.setOnClickListener(v -> checkAnswer(btnOption3));
        btnOption4.setOnClickListener(v -> checkAnswer(btnOption4));

        btnNext.setOnClickListener(v -> nextQuestion());

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        loadQuestion();
    }

    private void loadQuestion() {
        answered = false;

        txtProgress.setText("Question " + (currentQuestion + 1) + " / " + questions.length);
        txtQuestion.setText(questions[currentQuestion]);
        imgQuestion.setImageResource(questionImages[currentQuestion]);
        txtFeedback.setText("");
        txtScore.setText("Score: " + score);

        btnOption1.setText(options[currentQuestion][0]);
        btnOption2.setText(options[currentQuestion][1]);
        btnOption3.setText(options[currentQuestion][2]);
        btnOption4.setText(options[currentQuestion][3]);

        resetButton(btnOption1);
        resetButton(btnOption2);
        resetButton(btnOption3);
        resetButton(btnOption4);

        setOptionsEnabled(true);
        imgQuestion.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
        btnBackHome.setVisibility(View.GONE);
    }

    private void checkAnswer(Button selectedButton) {
        if (answered) {
            return;
        }

        answered = true;
        String selectedAnswer = selectedButton.getText().toString();
        String correctAnswer = correctAnswers[currentQuestion];

        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            selectedButton.setBackgroundColor(Color.parseColor("#4CAF50"));
            txtFeedback.setText(correctFeedback[currentQuestion]);
            txtFeedback.setTextColor(Color.parseColor("#2E7D32"));
        } else {
            selectedButton.setBackgroundColor(Color.parseColor("#F44336"));
            txtFeedback.setText("Not quite! Try to remember the food journey through the digestive system.");
            txtFeedback.setTextColor(Color.parseColor("#D32F2F"));
            highlightCorrectAnswer();
        }

        txtScore.setText("Score: " + score);
        setOptionsEnabled(false);
        btnNext.setVisibility(View.VISIBLE);
    }

    private void highlightCorrectAnswer() {
        Button[] buttons = {btnOption1, btnOption2, btnOption3, btnOption4};

        for (Button button : buttons) {
            if (button.getText().toString().equals(correctAnswers[currentQuestion])) {
                button.setBackgroundColor(Color.parseColor("#4CAF50"));
            }
        }
    }

    private void nextQuestion() {
        currentQuestion++;

        if (currentQuestion < questions.length) {
            loadQuestion();
        } else {
            showFinalResult();
        }
    }

    private void showFinalResult() {
        txtProgress.setText("Quiz Completed!");
        txtQuestion.setText("Your Score: " + score + " / " + questions.length);

        if (score == 5) {
            txtFeedback.setText("Excellent! You are a Digestive System Expert!");
        } else if (score >= 3) {
            txtFeedback.setText("Great job! You understand the digestive system well.");
        } else {
            txtFeedback.setText("Good try! Review the Learn section and try again.");
        }

        txtFeedback.setTextColor(Color.parseColor("#FF7A00"));
        txtScore.setText("");

        btnOption1.setVisibility(View.GONE);
        btnOption2.setVisibility(View.GONE);
        btnOption3.setVisibility(View.GONE);
        btnOption4.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnBackHome.setVisibility(View.VISIBLE);
        imgQuestion.setVisibility(View.GONE);
    }

    private void resetButton(Button button) {
        button.setBackgroundColor(Color.parseColor("#FF9800"));
        button.setTextColor(Color.WHITE);
        button.setEnabled(true);
        button.setVisibility(View.VISIBLE);
    }

    private void setOptionsEnabled(boolean enabled) {
        btnOption1.setEnabled(enabled);
        btnOption2.setEnabled(enabled);
        btnOption3.setEnabled(enabled);
        btnOption4.setEnabled(enabled);
    }
}