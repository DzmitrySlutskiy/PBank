package com.citi.piggybank.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * PrefUtils
 * Version information
 * Date: 25.03.2015
 * Time: 15:08
 * Created by Dzmitry Slutskiy.
 */
public class PrefUtils {

    private PrefUtils() {/*   code    */}

    /*  preferences params  */
    private static SharedPreferences mSharedPref = null;


    @SuppressWarnings("UnusedDeclaration")
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPref(context).getBoolean(key, defaultValue);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static String getString(Context context, String key, String defaultValue) {
        return getPref(context).getString(key, defaultValue);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static long getLong(Context context, String key, long defaultValue) {
        return getPref(context).getLong(key, defaultValue);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static int getInt(Context context, String key, int defaultValue) {
        return getPref(context).getInt(key, defaultValue);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static SharedPreferences getPref(Context context) {
        if (mSharedPref == null) {
            mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return mSharedPref;
    }

    public static void registerListener(Context context,
                                        SharedPreferences.OnSharedPreferenceChangeListener listener) {
        getPref(context).registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterListener(Context context,
                                          SharedPreferences.OnSharedPreferenceChangeListener listener) {
        getPref(context).unregisterOnSharedPreferenceChangeListener(listener);
    }


}
