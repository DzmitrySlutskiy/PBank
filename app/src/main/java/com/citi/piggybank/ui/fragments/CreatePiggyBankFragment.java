package com.citi.piggybank.ui.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citi.piggybank.R;
import com.citi.piggybank.bo.Items;
import com.citi.piggybank.provider.PiggyContract;
import com.citi.piggybank.ui.activities.BaseActivity;

/**
 * CreatePiggyBankFragment
 * Version info
 * 25.03.2015
 * Created by Dzmitry_Slutski.
 */
public class CreatePiggyBankFragment extends BaseFragment {

    private static int ITEM_INDEX = 0;
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
        view.findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                Items items = ITEMS.get(ITEM_INDEX++);

                values.put(PiggyContract.COLUMN_NAME,items.name);
                values.put(PiggyContract.COLUMN_GOAL,items.goal);
                values.put(PiggyContract.COLUMN_AMOUNT,items.amount);

                getActivity().getContentResolver().insert(PiggyContract.CONTENT_URI,values);

                BaseActivity activity = (BaseActivity) getActivity();
                activity.replaceFragment(R.id.container, new AmazonItemFragment(), "", false);
            }
        });
        return view;
    }
}
