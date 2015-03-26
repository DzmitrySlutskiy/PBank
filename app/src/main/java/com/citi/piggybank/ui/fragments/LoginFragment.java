package com.citi.piggybank.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.citi.piggybank.R;
import com.citi.piggybank.loaders.PiggyBankLoader;
import com.citi.piggybank.ui.activities.BaseActivity;
import com.citi.piggybank.utils.Constants;
import com.citi.piggybank.utils.CursorUtils;
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

    private boolean mHasBanks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLoader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        View btnSignIn = view.findViewById(R.id.sign_in);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.putString(getActivity(), Constants.PREF_USER_NAME, mUserName.getText().toString());
                Fragment fragment;
                BaseActivity activity = (BaseActivity) getActivity();
                if (mHasBanks) {
                    fragment = new CreatePiggyBankFragment();
                } else {
                    fragment = new PiggyBanksFragment();
                }
                activity.replaceFragment(R.id.container, fragment, "", false);
            }
        });

        mLastUserId = PrefUtils.getString(getActivity(), Constants.PREF_USER_NAME, "");
        mUserName = (EditText) view.findViewById(R.id.login);

        if (!TextUtils.isEmpty(mLastUserId)) {
            mUserName.setText(mLastUserId);
        }

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new PiggyBankLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mHasBanks = CursorUtils.isEmpty(cursor);
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
