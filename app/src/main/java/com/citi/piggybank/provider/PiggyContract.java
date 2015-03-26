package com.citi.piggybank.provider;

import android.net.Uri;

import com.citi.piggybank.annotations.dbInteger;
import com.citi.piggybank.annotations.dbString;

/**
 * PiggyContract
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyContract extends BaseContract {

    public static final String PATH = "PiggyBanks";

    public static final String PREF = "piggy_";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);

    @dbString
    public static final String COLUMN_NAME = PREF + "name";

    @dbInteger
    public static final String COLUMN_GOAL = PREF + "goal";

    @dbInteger
    public static final String COLUMN_AMOUNT = PREF + "amount";

    public static final String[] COLUMNS = buildAvailableColumns(PiggyContract.class);

    public static final String CREATE = buildCreateSQL(PiggyContract.class, PATH);


}
