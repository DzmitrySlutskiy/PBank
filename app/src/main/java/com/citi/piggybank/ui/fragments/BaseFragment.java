package com.citi.piggybank.ui.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.citi.piggybank.bo.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseFragment
 * Version info
 * 25.03.2015
 * Created by Dzmitry_Slutski.
 */
public abstract class BaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static List<Items> ITEMS = new ArrayList<>();

    public static final int COUNT = 4;

    static {
        ITEMS.add(new Items(COUNT, ITEMS.size(), 150000, "FERRARI GT F150", 23500));
        ITEMS.add(new Items(COUNT, ITEMS.size(), 1000, "iPhone 6+", 890));
        ITEMS.add(new Items(COUNT, ITEMS.size(), 17000, "Apple Watch Gold Edition", 17000));
        ITEMS.add(new Items(COUNT, ITEMS.size(), 430, "Canon PowerShot SX520", 20));
    }

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


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    protected Bundle getLoaderBundle() {
        return new Bundle();
    }

    protected void initLoader() {
        getLoaderManager().initLoader(0, getLoaderBundle(), this);
    }
}
