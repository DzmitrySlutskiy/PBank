package com.citi.piggybank.ui.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * BaseFragment
 * Version info
 * 25.03.2015
 * Created by Dzmitry_Slutski.
 */
public abstract class BaseFragment extends Fragment {


    protected abstract boolean showActionBar();

    protected abstract String getActionBarTitle();

    @Override
    public void onStart() {
        super.onStart();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            if (showActionBar()) {
                actionBar.show();
            } else {
                actionBar.hide();
            }
            actionBar.setTitle(getActionBarTitle());
        }
    }

    protected ActionBar getActionBar() {
        Activity activity = getActivity();
        if (activity == null) {
            return null;
        }
        return ((ActionBarActivity) activity).getSupportActionBar();
    }
}
