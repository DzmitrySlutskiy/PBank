package com.citi.piggybank.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.citi.piggybank.R;
import com.citi.piggybank.loaders.PiggyBankLoader;
import com.citi.piggybank.ui.activities.BaseActivity;
import com.citi.piggybank.ui.adapters.PiggyPagerAdapter;

/**
 * PiggyBanksFragment
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyBanksFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = PiggyBanksFragment.class.getSimpleName();
    private ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_piggy_bank) {

            BaseActivity activity = (BaseActivity) getActivity();
            activity.replaceFragment(R.id.container, new CreatePiggyBankFragment(), "", false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piggy_banks, container, false);

        mPager = (ViewPager) view.findViewById(R.id.pager);

        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new PiggyBankLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        PiggyPagerAdapter adapter = (PiggyPagerAdapter) mPager.getAdapter();
        if (adapter == null) {
            adapter = new PiggyPagerAdapter(getChildFragmentManager(), cursor);
            mPager.setAdapter(adapter);
        } else {
            adapter.swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface OnWithdrawListener {
        void onWithdrawClick(int index);
    }
}
