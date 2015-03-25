package com.citi.piggybank.ui.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citi.piggybank.R;
import com.citi.piggybank.ui.activities.BaseActivity;

/**
 * LoginFragment
 * Version info
 * 25.03.2015
 * Created by Dzmitry_Slutski.
 */
public class LoginFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        View btnSignIn = view.findViewById(R.id.sign_in);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity activity = (BaseActivity) getActivity();
                activity.replaceFragment(R.id.container, new CreatePiggyBankFragment(), "", false);
            }
        });

        TextView viewById = (TextView) view.findViewById(R.id.term2);

//        viewById.setText(Html.fromHtml(getString(R.string.term_of_use2)));

        return view;
    }


    @Override
    protected boolean showActionBar() {
        return false;
    }

    @Override
    protected String getActionBarTitle() {
        return "";
    }
}
