package com.citi.piggybank.loaders;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.citi.piggybank.provider.PiggyContract;

/**
 * PiggyBankLoader
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyBankLoader extends CursorLoader {

    public PiggyBankLoader(Context context) {
        super(context, PiggyContract.CONTENT_URI, PiggyContract.COLUMNS, null, null, null);
    }

    public PiggyBankLoader(Context context, int bankId) {
        super(context, PiggyContract.CONTENT_URI, PiggyContract.COLUMNS, PiggyContract._ID + " = ?", new String[]{Integer.toString(bankId)}, null);
    }
}
