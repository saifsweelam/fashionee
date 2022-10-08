package com.saifsweelam.fashionee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private ArrayList<OnboardingItem> onboardingItems;

    private MaterialButton onboardingActionButton;
    private LinearLayout onboardingLayoutIndicators;
    private ImageView[] indicators;
    private ViewPager2 onboardingViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        onboardingActionButton = (MaterialButton) findViewById(R.id.onboardingActionButton);
        onboardingLayoutIndicators = (LinearLayout) findViewById(R.id.onboardingLayoutIndicators);
        onboardingViewPager = (ViewPager2) findViewById(R.id.onboardingViewPager);

        onboardingItems = new ArrayList<OnboardingItem>();

        onboardingItems.add(new OnboardingItem(
                getResources().getString(R.string.onboarding1_heading),
                getResources().getString(R.string.onboarding1_description),
                R.drawable.discount
        ));
        onboardingItems.add(new OnboardingItem(
                getResources().getString(R.string.onboarding2_heading),
                getResources().getString(R.string.onboarding2_description),
                R.drawable.fashion1
        ));
        onboardingItems.add(new OnboardingItem(
                getResources().getString(R.string.onboarding3_heading),
                getResources().getString(R.string.onboarding3_description),
                R.drawable.fashion2
        ));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

        onboardingViewPager.setAdapter(onboardingAdapter);

        setOnboardingLayoutIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        onboardingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void setOnboardingLayoutIndicators() {
        indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMarginEnd(16);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageResource(R.drawable.onboarding_indicator_inactive);
            indicators[i].setLayoutParams(layoutParams);
            indicators[i].setColorFilter(typedValue.data);
            onboardingLayoutIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index) {
        for (int i = 0; i < indicators.length; i++) {
            ImageView imageView = indicators[i];
            if (i == index) {
                imageView.setImageResource(R.drawable.onboarding_indicator_active);
                imageView.setImageAlpha(255);
            } else {
                imageView.setImageResource(R.drawable.onboarding_indicator_inactive);
                imageView.setImageAlpha(155);
            }
        }
    }
}