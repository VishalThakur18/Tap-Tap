package com.app.tap.tap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.app.tap.tap.Fragments.IntroFragment1;
import com.app.tap.tap.Fragments.IntroFragment2;
import com.app.tap.tap.Fragments.IntroFragment3;
import com.app.tap.tap.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageView nextButton;
    private LinearLayout indicatorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.viewPager);
        nextButton = findViewById(R.id.next_button);
        indicatorContainer = findViewById(R.id.indicatorsContainer);

        viewPager.setAdapter(new IntroPagerAdapter(this));
        setupIndicators();
        setCurrentIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentIndicator(position);
            }
        });

        nextButton.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem + 1 < 3) {
                viewPager.setCurrentItem(currentItem + 1);
            } else {
                startActivity(new Intent(IntroActivity.this, Login.class));
                finish();
            }
        });
    }

    private void setupIndicators() {
        int itemCount = 3;
        ImageView[] indicators = new ImageView[itemCount];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.gs_indicator_inactive_bg
            ));
            indicators[i].setLayoutParams(layoutParams);
            indicatorContainer.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int position) {
        int childCount = indicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicatorContainer.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.gs_indicator_active_bg
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.gs_indicator_inactive_bg
                ));
            }
        }
    }

    private class IntroPagerAdapter extends FragmentStateAdapter {
        public IntroPagerAdapter(IntroActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new IntroFragment1();
                case 1:
                    return new IntroFragment2();
                case 2:
                    return new IntroFragment3();
                default:
                    return new IntroFragment1(); // fallback
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
