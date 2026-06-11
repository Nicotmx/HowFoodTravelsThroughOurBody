package com.example.howfoodtravelsthroughourbody;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;

public class DragDropActivity extends AppCompatActivity {

    android.widget.GridLayout organContainer;
    LinearLayout slot1, slot2, slot3, slot4, slot5;
    TextView txtMessage;
    Button btnCheck, btnReset, btnBack;

    String[] correctOrder = {
            "Mouth",
            "Esophagus",
            "Stomach",
            "Small Intestine",
            "Large Intestine"
    };

    ArrayList<OrganItem> organs = new ArrayList<>();

    class OrganItem {
        String name;
        int image;

        OrganItem(String name, int image) {
            this.name = name;
            this.image = image;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);

        organContainer = findViewById(R.id.organContainer);
        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        slot5 = findViewById(R.id.slot5);

        txtMessage = findViewById(R.id.txtMessage);
        btnCheck = findViewById(R.id.btnCheck);
        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);

        organs.add(new OrganItem("Mouth", R.drawable.mouth_icon));
        organs.add(new OrganItem("Esophagus", R.drawable.esophagus_icon));
        organs.add(new OrganItem("Stomach", R.drawable.stomach_icon));
        organs.add(new OrganItem("Small Intestine", R.drawable.small_intestine_icon));
        organs.add(new OrganItem("Large Intestine", R.drawable.large_intestine_icon));

        setDropListener(organContainer);
        setDropListener(slot1);
        setDropListener(slot2);
        setDropListener(slot3);
        setDropListener(slot4);
        setDropListener(slot5);

        loadShuffledOrgans();

        btnCheck.setOnClickListener(v -> checkAnswer());
        btnReset.setOnClickListener(v -> resetGame());
        btnBack.setOnClickListener(v -> finish());
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
    private void setCardGridSize(View card) {
        android.widget.GridLayout.LayoutParams params = new android.widget.GridLayout.LayoutParams();
        params.width = 0;
        params.height = dp(95);
        params.columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNDEFINED, 1f);
        params.setMargins(dp(4), dp(4), dp(4), dp(4));
        card.setLayoutParams(params);
    }

    private void setCardSlotSize(View card) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(82)
        );
        params.setMargins(0, dp(8), 0, 0);
        card.setLayoutParams(params);
    }

    private void loadShuffledOrgans() {
        organContainer.removeAllViews();
        clearSlots();

        Collections.shuffle(organs);

        for (OrganItem organ : organs) {
            organContainer.addView(createOrganCard(organ));
        }

        txtMessage.setText("Arrange all 5 organs to complete the challenge!");
        txtMessage.setTextColor(Color.parseColor("#555555"));
    }

    private CardView createOrganCard(OrganItem organ) {
        CardView card = new CardView(this);
        card.setTag(organ.name);
        card.setCardBackgroundColor(Color.parseColor("#FF9800"));
        card.setRadius(dp(16));
        card.setCardElevation(dp(5));
        card.setUseCompatPadding(true);

        setCardGridSize(card);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setPadding(dp(10), dp(8), dp(10), dp(8));

        ImageView icon = new ImageView(this);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dp(38), dp(38));
        icon.setLayoutParams(iconParams);
        icon.setImageResource(organ.image);

        TextView name = new TextView(this);
        name.setText(organ.name);
        name.setTextColor(Color.WHITE);
        name.setTextSize(20);
        name.setTypeface(null, android.graphics.Typeface.BOLD);
        name.setGravity(Gravity.CENTER_VERTICAL);
        name.setPadding(dp(8), 0, 0, 0);
        name.setSingleLine(false);

        row.addView(icon);
        row.addView(name);

        card.removeAllViews();
        card.addView(row);

        card.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("organ", organ.name);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            return true;
        });

        return card;
    }

    private void setDropListener(ViewGroup target) {
        target.setOnDragListener((v, event) -> {
            View draggedView = (View) event.getLocalState();

            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    ViewGroup oldParent = (ViewGroup) draggedView.getParent();

                    if (oldParent != null) {
                        oldParent.removeView(draggedView);
                    }

                    if (target != organContainer && target.getChildCount() > 1) {
                        View existingCard = target.getChildAt(1);
                        target.removeView(existingCard);
                        setCardGridSize(existingCard);
                        organContainer.addView(existingCard);
                    }

                    if (target == organContainer) {
                        setCardGridSize(draggedView);
                    } else {
                        setCardSlotSize(draggedView);
                    }

                    target.addView(draggedView);
                    return true;

                default:
                    return true;
            }
        });
    }

    private void checkAnswer() {
        LinearLayout[] slots = {slot1, slot2, slot3, slot4, slot5};

        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getChildCount() < 2) {
                txtMessage.setText("Arrange all 5 organs to complete the challenge!");
                txtMessage.setTextColor(Color.parseColor("#FF7A00"));
                return;
            }

            View card = slots[i].getChildAt(1);
            String organName = card.getTag().toString();

            if (!organName.equals(correctOrder[i])) {
                txtMessage.setText("Not Quite Right!\n\nTry again and think about where food travels first.");
                txtMessage.setTextColor(Color.parseColor("#D32F2F"));
                return;
            }
        }

        txtMessage.setText("Digestive Master!\n\nYou successfully completed the digestive system challenge!");
        txtMessage.setTextColor(Color.parseColor("#2E7D32"));
    }

    private void resetGame() {
        loadShuffledOrgans();
    }

    private void clearSlots() {
        clearSlot(slot1);
        clearSlot(slot2);
        clearSlot(slot3);
        clearSlot(slot4);
        clearSlot(slot5);
    }

    private void clearSlot(LinearLayout slot) {
        while (slot.getChildCount() > 1) {
            slot.removeViewAt(1);
        }
    }
}