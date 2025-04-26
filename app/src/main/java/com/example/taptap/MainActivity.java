package com.example.taptap;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    LinearLayout homeBtnContainer, referBtnContainer, walletBtnContainer;
    ImageView homeIcon, referIcon, walletIcon;
    TextView homeText, referText, walletText;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Containers
        homeBtnContainer = findViewById(R.id.homeBtnContainer);
        referBtnContainer = findViewById(R.id.referBtnContainer);
        walletBtnContainer = findViewById(R.id.walletBtnContainer);

        // Icons
        homeIcon = findViewById(R.id.homeIcon);
        referIcon = findViewById(R.id.referIcon);
        walletIcon = findViewById(R.id.walletIcon);

        // Texts
        homeText = findViewById(R.id.homeText);
        referText = findViewById(R.id.referText);
        walletText = findViewById(R.id.walletText);

        // ViewPager
        viewPager = findViewById(R.id.viewpagernotif);
        viewPager.setAdapter(new FragmentAdapter(this));
        viewPager.setUserInputEnabled(false); // Disable swipe

        homeBtnContainer.setOnClickListener(v -> {
            viewPager.setCurrentItem(0);
            highlightButton(0);
        });

        referBtnContainer.setOnClickListener(v -> {
            viewPager.setCurrentItem(1);
            highlightButton(1);
        });

        walletBtnContainer.setOnClickListener(v -> {
            viewPager.setCurrentItem(2);
            highlightButton(2);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                highlightButton(position);
            }
        });

        // Set default
        viewPager.setCurrentItem(0);
        highlightButton(0);
    }

    private void highlightButton(int index) {
        resetButtonStyles();

        switch (index) {
            case 0:
                homeBtnContainer.setBackgroundResource(R.drawable.bg_selected_button);
                homeText.setTextColor(Color.WHITE);
                homeIcon.setImageResource(R.drawable.ic_home_white);
                break;
            case 1:
                referBtnContainer.setBackgroundResource(R.drawable.bg_selected_button);
                referText.setTextColor(Color.WHITE);
                referIcon.setImageResource(R.drawable.ic_refer_white);
                break;
            case 2:
                walletBtnContainer.setBackgroundResource(R.drawable.bg_selected_button);
                walletText.setTextColor(Color.WHITE);
                walletIcon.setImageResource(R.drawable.ic_wallet_white);
                break;
        }
    }

    private void resetButtonStyles() {
        // Reset Home
        homeBtnContainer.setBackgroundResource(R.drawable.bg_unselected_button);
        homeText.setTextColor(Color.BLACK);
        homeIcon.setImageResource(R.drawable.ic_home_black);

        // Reset Refer
        referBtnContainer.setBackgroundResource(R.drawable.bg_unselected_button);
        referText.setTextColor(Color.BLACK);
        referIcon.setImageResource(R.drawable.ic_refer_black);

        // Reset Wallet
        walletBtnContainer.setBackgroundResource(R.drawable.bg_unselected_button);
        walletText.setTextColor(Color.BLACK);
        walletIcon.setImageResource(R.drawable.ic_wallet_black);
    }
}
