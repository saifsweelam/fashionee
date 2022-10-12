package com.saifsweelam.fashionee;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {
    private ArrayList<TabInfo> tabs = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setTabs(ArrayList<TabInfo> tabs) {
        this.tabs = tabs;
    }

    public void addTab(TabInfo tab) {
        tabs.add(tab);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return tabs.get(position).getFragment();
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }


}
