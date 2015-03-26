package com.citi.piggybank.ui.adapters;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.citi.piggybank.provider.PiggyContract;
import com.citi.piggybank.ui.fragments.PiggyBanksFragment;
import com.citi.piggybank.ui.fragments.PiggyItemFragment;
import com.citi.piggybank.utils.CursorUtils;

/**
 * PiggyPagerAdapter
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyPagerAdapter extends FragmentStatePagerAdapter implements PiggyBanksFragment.OnWithdrawListener {

    private Cursor mCursor;

    public PiggyPagerAdapter(FragmentManager fragmentManager, Cursor cursor) {
        super(fragmentManager);

        mCursor = cursor;
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public Fragment getItem(int i) {
        Bundle fragmentArgs = new Bundle();

        mCursor.moveToPosition(i);
        fragmentArgs.putInt(PiggyItemFragment.ARG_ITEM_INDEX, i);
        fragmentArgs.putInt(PiggyItemFragment.ARG_ITEM_COUNT, getCount());
        fragmentArgs.putInt(PiggyItemFragment.ARG_ITEM_ID, CursorUtils.getInt(mCursor, PiggyContract.COLUMN_ID));
        Fragment fragment = new PiggyItemFragment();

        fragment.setArguments(fragmentArgs);

        return fragment;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return "PIGGY BANK";
    }

    @Override
    public void onWithdrawClick(int index) {

        /*mTabCount--;
        mItems.remove(index);
        for (Items items : mItems) {
            items.count = mTabCount;
        }
        notifyDataSetChanged();*/
    }


}