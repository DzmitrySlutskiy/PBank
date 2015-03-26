package com.citi.piggybank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citi.piggybank.R;
import com.citi.piggybank.ui.activities.BaseActivity;

/**
 * AmazonItemFragment
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class AmazonItemFragment extends BaseFragment {

    @Override
    protected boolean showActionBar() {
        return true;
    }

    @Override
    protected String getActionBarTitle() {
        return "PIGGY BANK";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amazon_item, container, false);
        view.findViewById(R.id.buy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity activity = (BaseActivity) getActivity();
                activity.replaceFragment(R.id.container, new PiggyBanksFragment(), "", false);
            }
        });
        return view;
    }
}
