package com.example.taptap;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    Button homeBtn, referBtn, walletBtn;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        homeBtn = findViewById(R.id.homeBtn);
        referBtn = findViewById(R.id.referBtn);
        walletBtn = findViewById(R.id.walletBtn);
        viewPager = findViewById(R.id.viewpagernotif);

        viewPager.setAdapter(new FragmentAdapter(this));
        viewPager.setUserInputEnabled(false); // disable swipe

        homeBtn.setOnClickListener(v -> {
            viewPager.setCurrentItem(0);
            highlightButton(0);
        });

        referBtn.setOnClickListener(v -> {
            viewPager.setCurrentItem(1);
            highlightButton(1);
        });

        walletBtn.setOnClickListener(v -> {
            viewPager.setCurrentItem(2);
            highlightButton(2);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                highlightButton(position);
            }
        });

        // Set default to Home
        viewPager.setCurrentItem(0);
        highlightButton(0);
    }

    private void highlightButton(int index) {
        resetButtonStyles();

        switch (index) {
            case 0:
                styleActiveButton(homeBtn);
                break;
            case 1:
                styleActiveButton(referBtn);
                break;
            case 2:
                styleActiveButton(walletBtn);
                break;
        }
    }

    private void resetButtonStyles() {
        homeBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        homeBtn.setTextColor(Color.BLACK);
        referBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        referBtn.setTextColor(Color.BLACK);
        walletBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        walletBtn.setTextColor(Color.BLACK);
    }

    private void styleActiveButton(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        button.setTextColor(Color.WHITE);
    }
}
