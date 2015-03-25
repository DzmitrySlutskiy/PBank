package com.citi.piggybank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citi.piggybank.R;

/**
 * CreatePiggyBankFragment
 * Version info
 * 25.03.2015
 * Created by Dzmitry_Slutski.
 */
public class CreatePiggyBankFragment extends BaseFragment {
    @Override
    protected boolean showActionBar() {
        return true;
    }

    @Override
    protected String getActionBarTitle() {
        return "CREATE PIGGY BANK";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_piggy_bank, container, false);

        return view;
    }
}
