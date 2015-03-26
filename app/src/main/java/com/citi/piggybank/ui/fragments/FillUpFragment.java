package com.citi.piggybank.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.citi.piggybank.R;

/**
 * FillUpFragment
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class FillUpFragment extends BaseFragment {

    public static final String ARG_PIGGY_BANK_INDEX = "ARG_PIGGY_BANK_INDEX";
    public static final String ARG_PIGGY_BANK_SUM = "ARG_PIGGY_BANK_SUM";
    public static final String ARG_PIGGY_BANK_AMOUNT = "ARG_PIGGY_BANK_AMOUNT";

    private int mIndex;
    private int mAmount;
    private int mMaxAmount;
    private int mSumAmount;
    private EditText mSum;
    private View mDecButton;
    private View mIncButton;
    private View mFillUp;

    private OnFillUpListener mOnFillUpListener;

    public static Fragment newInstance(int index, int sum, int amount) {
        Bundle args = new Bundle();
        args.putInt(ARG_PIGGY_BANK_INDEX, index);
        args.putInt(ARG_PIGGY_BANK_SUM, sum);
        args.putInt(ARG_PIGGY_BANK_AMOUNT, amount);

        Fragment fragment = new FillUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAmount = 100;
        Bundle arguments = getArguments();
        mIndex = arguments.getInt(ARG_PIGGY_BANK_INDEX);
        mSumAmount = arguments.getInt(ARG_PIGGY_BANK_SUM);
        mMaxAmount = arguments.getInt(ARG_PIGGY_BANK_AMOUNT);
        if (mMaxAmount - mSumAmount < mAmount) {
            mAmount = mMaxAmount - mSumAmount;
        }
    }

    @Override
    protected boolean showActionBar() {
        return true;
    }

    @Override
    protected String getActionBarTitle() {
        return "FILL UP PIGGY BANK";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_up, container, false);

        mSum = (EditText) view.findViewById(R.id.fill_up_sum);
        mSum.setText(Integer.toString(mAmount));
        mSum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                Integer entered = Integer.valueOf(s1);
                if (mMaxAmount - mSumAmount < entered) {
                    mAmount = mMaxAmount - mSumAmount;
                    mSum.setText(Integer.toString(mAmount));
                }
            }
        });
        mDecButton = view.findViewById(R.id.dec_button);
        mIncButton = view.findViewById(R.id.inc_button);

        mDecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSum(-1);
            }
        });
        mIncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSum(1);
            }
        });
        mFillUp = view.findViewById(R.id.confirm);

        mFillUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnFillUpListener != null) {
                    mOnFillUpListener.onFillUp(mIndex, mSumAmount + mAmount);
                }

            }
        });

        return view;
    }

    private void changeSum(int changeValue) {
        if (mAmount + changeValue > mMaxAmount - mSumAmount) {
            return;
        }
        if (mAmount + changeValue > 0) {
            mAmount += changeValue;
            mSum.setText(Integer.toString(mAmount));
        }
        if (mAmount + changeValue == 0) {
            mDecButton.setEnabled(false);
        } else {
            mDecButton.setEnabled(true);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mOnFillUpListener = (OnFillUpListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnFillUpListener = null;
    }

    public interface OnFillUpListener {
        void onFillUp(int index, int amount);
    }
}
