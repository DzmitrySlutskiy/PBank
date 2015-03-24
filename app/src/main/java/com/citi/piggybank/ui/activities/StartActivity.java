package com.citi.piggybank.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.citi.piggybank.R;

import java.util.concurrent.TimeUnit;

/**
 * StartActivity
 * Version information
 * Date: 24.03.2015
 * Time: 13:35
 * Created by Dzmitry Slutskiy.
 */
public class StartActivity extends ActionBarActivity {

    public static final int SPLASH_TIME_OUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(SPLASH_TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                }
            }
        }).start();
    }
}
