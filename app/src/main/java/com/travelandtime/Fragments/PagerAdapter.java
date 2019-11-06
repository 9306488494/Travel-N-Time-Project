package com.travelandtime.Fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int totalTabs;
    public PagerAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs=totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Tab1();

            case 1:
                return new Tab2();
            case 2:
                return new Tab3();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
