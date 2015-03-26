package com.citi.piggybank.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citi.piggybank.R;
import com.citi.piggybank.ui.adapters.PiggyPagerAdapter;

/**
 * PiggyBanksFragment
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyBanksFragment extends Fragment {
    public static final String TAG = PiggyBanksFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piggy_banks, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setAdapter(new PiggyPagerAdapter(getChildFragmentManager()));

        return view;
    }


}
