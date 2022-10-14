package com.saifsweelam.fashionee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.saifsweelam.fashionee.fragments.main.CartFragment;
import com.saifsweelam.fashionee.fragments.main.HomeFragment;
import com.saifsweelam.fashionee.fragments.main.NotificationsFragment;
import com.saifsweelam.fashionee.fragments.main.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    PagerAdapter mainPagerAdapter;

    ViewPager2 mainViewPager;
    TabLayout mainTabLayout;

    ImageView avatarView;
    MaterialButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentication auth = new Authentication(this);

        if (!auth.isLoggedIn()) auth.logout();

        mainViewPager = findViewById(R.id.mainViewPager);
        mainTabLayout = findViewById(R.id.mainTabLayout);
        avatarView = findViewById(R.id.avatarView);
        menuButton = findViewById(R.id.menuButton);

        Glide.with(this)
                .load(auth.getCurrentUserAvatar())
                .centerCrop()
                .into(avatarView);

        mainPagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
    }

    @Override
    protected void onResume() {
        ArrayList<TabInfo> tabs = new ArrayList<>();

        tabs.add(new TabInfo(
                getResources().getString(R.string.home),
                R.drawable.ic_baseline_home_24,
                new HomeFragment()
        ));

        tabs.add(new TabInfo(
                getResources().getString(R.string.cart),
                R.drawable.ic_baseline_shopping_cart_24,
                new CartFragment()
        ));

        tabs.add(new TabInfo(
                getResources().getString(R.string.notifications),
                R.drawable.ic_baseline_notifications_24,
                new NotificationsFragment()
        ));

        tabs.add(new TabInfo(
                getResources().getString(R.string.profile),
                R.drawable.ic_baseline_account_circle_24,
                new ProfileFragment()
        ));

        mainPagerAdapter.setTabs(tabs);

        mainViewPager.setAdapter(mainPagerAdapter);

        mainViewPager.setUserInputEnabled(false);

        new TabLayoutMediator(mainTabLayout, mainViewPager, (tab, position) -> {
            tab.setIcon(tabs.get(position).getIcon());
            tab.setText(position == 0 ? tabs.get(position).getTitle() : "");
        }).attach();

        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setText(tabs.get(mainTabLayout.getSelectedTabPosition()).getTitle());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        super.onResume();
    }
}