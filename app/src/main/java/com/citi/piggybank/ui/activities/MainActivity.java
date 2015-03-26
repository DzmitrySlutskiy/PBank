package com.citi.piggybank.ui.activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.citi.piggybank.R;
import com.citi.piggybank.provider.PiggyContract;
import com.citi.piggybank.ui.fragments.FillUpFragment;
import com.citi.piggybank.ui.fragments.LoginFragment;


public class MainActivity extends BaseActivity implements FillUpFragment.OnFillUpListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        LoginFragment fragment = new LoginFragment();
        replaceFragment(R.id.container, fragment, "", false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFillUp(int index, int amount) {
        getSupportFragmentManager().popBackStack();
        ContentValues values = new ContentValues();
        values.put(PiggyContract.COLUMN_AMOUNT, amount);

        getContentResolver().update(PiggyContract.CONTENT_URI, values,
                PiggyContract.COLUMN_ID + " = ?", new String[]{Integer.toString(index)});
    }
}
