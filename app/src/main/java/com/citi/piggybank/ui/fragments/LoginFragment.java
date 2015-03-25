package com.citi.piggybank.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.citi.piggybank.R;
import com.citi.piggybank.ui.activities.BaseActivity;
import com.citi.piggybank.utils.Constants;
import com.citi.piggybank.utils.PrefUtils;

/**
 * LoginFragment
 * Version info
 * 25.03.2015
 * Created by Dzmitry_Slutski.
 */
public class LoginFragment extends BaseFragment {

    private String mLastUserId;
    private EditText mUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        View btnSignIn = view.findViewById(R.id.sign_in);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.putString(getActivity(), Constants.PREF_USER_NAME, mUserName.getText().toString());

                BaseActivity activity = (BaseActivity) getActivity();
                activity.replaceFragment(R.id.container, new CreatePiggyBankFragment(), "", false);

            }
        });

        mLastUserId = PrefUtils.getString(getActivity(), Constants.PREF_USER_NAME, "");
        mUserName = (EditText) view.findViewById(R.id.login);

        if (! TextUtils.isEmpty(mLastUserId)) {
            mUserName.setText(mLastUserId);
        }

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
