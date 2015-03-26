package com.citi.piggybank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citi.piggybank.R;

/**
 * PiggyItemFragment
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyItemFragment extends BaseFragment {

    public static final String ARG_ITEM_COUNT = "ARG_ITEM_COUNT";
    public static final String ARG_ITEM_INDEX = "ARG_ITEM_INDEX";
    public static final String ARG_ITEM_NAME = "ARG_ITEM_NAME";
    public static final String ARG_ITEM_GOAL = "ARG_ITEM_GOAL";
    public static final String ARG_ITEM_PERCENT = "ARG_ITEM_PERCENT";

    private int mCount;
    private int mIndex;
    private int mPercent;
    private String mGoal;
    private String mItemName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        mCount = arguments.getInt(ARG_ITEM_COUNT);
        mIndex = arguments.getInt(ARG_ITEM_INDEX);
        mPercent = arguments.getInt(ARG_ITEM_PERCENT);
        mItemName = arguments.getString(ARG_ITEM_NAME);
        mGoal = arguments.getString(ARG_ITEM_GOAL);

    }

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
        View view = inflater.inflate(R.layout.fragment_piggy_item, container, false);
        TextView counter = (TextView) view.findViewById(R.id.item_counter);
        counter.setText(Integer.toString(mIndex) + "/" + mCount);

        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        itemName.setText(mItemName);

        TextView itemGoal = (TextView) view.findViewById(R.id.item_goal);
        itemGoal.setText("GOAL: $ " + mGoal);

        TextView percent = (TextView) view.findViewById(R.id.percent);
        percent.setText("" + mPercent + "%");

        View congratulations = view.findViewById(R.id.congratulation);
        congratulations.setVisibility(mPercent == 100 ? View.VISIBLE : View.INVISIBLE);

        view.findViewById(R.id.arrow_right).setVisibility(mCount == mIndex ? View.INVISIBLE : View.VISIBLE);
        view.findViewById(R.id.arrow_left).setVisibility(mIndex == 1 ? View.INVISIBLE : View.VISIBLE);

        view.findViewById(R.id.fill_up).setBackgroundResource(mPercent == 100 ? R.drawable.btn_withdraw : R.drawable.btn_fill_up);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
    }
}
