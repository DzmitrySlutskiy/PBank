package com.citi.piggybank.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.citi.piggybank.ui.fragments.PiggyItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * PiggyPagerAdapter
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyPagerAdapter extends FragmentStatePagerAdapter {


    private List<Items> mItems = new ArrayList<>();
    private static final int FRAGMENT_TAB_COUNT = 3;

    public PiggyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        mItems.add(new Items(FRAGMENT_TAB_COUNT, mItems.size(), "150 000", "FERRARI GT F150", 12));
        mItems.add(new Items(FRAGMENT_TAB_COUNT, mItems.size(), "1 000", "iPhone 6+", 95));
        mItems.add(new Items(FRAGMENT_TAB_COUNT, mItems.size(), "17 000", "Apple Watch Gold Edition", 100));
    }

    @Override
    public int getCount() {
        return FRAGMENT_TAB_COUNT;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle fragmentArgs = new Bundle();

        Items items = mItems.get(i);
        items.saveToArgs(fragmentArgs);

        Fragment fragment = new PiggyItemFragment();
        fragment.setArguments(fragmentArgs);

        return fragment;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return "PIGGY BANK";
    }


    private static class Items {
        int count;
        int index;
        int percent;
        String goal;
        String name;

        public Items(int count, int index, String goal, String name, int percent) {
            this.count = count;
            this.index = index;
            this.goal = goal;
            this.name = name;
            this.percent = percent;
        }

        public void saveToArgs(Bundle args) {
            args.putInt(PiggyItemFragment.ARG_ITEM_COUNT, count);
            args.putInt(PiggyItemFragment.ARG_ITEM_INDEX, index + 1);
            args.putString(PiggyItemFragment.ARG_ITEM_NAME, name);
            args.putString(PiggyItemFragment.ARG_ITEM_GOAL, goal);
            args.putInt(PiggyItemFragment.ARG_ITEM_PERCENT, percent);
        }
    }
}