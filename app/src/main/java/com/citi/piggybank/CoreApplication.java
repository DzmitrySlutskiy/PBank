package com.citi.piggybank;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * CoreApplication
 * Version information
 * Date: 24.03.2015
 * Time: 13:34
 * Created by Dzmitry Slutskiy.
 */
public class CoreApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Arial Rounded MT Bold Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
