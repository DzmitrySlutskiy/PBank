package com.citi.piggybank.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.citi.piggybank.CoreApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BankProvider
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class BankProvider extends ContentProvider {
    private static final String TAG = BankProvider.class.getSimpleName();

    private static final int CODE_TABLE = 1;
    private static final int CODE_ID = 2;
    private static final int CODE_ID_NEG = 3;

    private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/";
    private static final String CONTENT_DIR_TYPE = "vnd.android.cursor.dir/";

    private static final UriMatcher URI_MATCHER;
    private static final Object DB_LOCKER;
    private static final List<String> IMPLEMENTED_TABLES;

    private DBHelper mDBHelper;

    static {
        DB_LOCKER = new Object();

        //URI init block
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(PiggyContract.AUTHORITY, "*", CODE_TABLE);
        URI_MATCHER.addURI(PiggyContract.AUTHORITY, "*/#", CODE_ID);
        URI_MATCHER.addURI(PiggyContract.AUTHORITY, "*/*", CODE_ID_NEG);

        IMPLEMENTED_TABLES = new ArrayList<>();
        IMPLEMENTED_TABLES.add(PiggyContract.PATH);
    }

    public BankProvider() {/*   code    */}

    @Override
    public boolean onCreate() {
        initDBHelperInstance();
        return false;
    }

    /**
     * initialize DBHelper instance (thread safe)
     */
    private void initDBHelperInstance() {
        synchronized (DB_LOCKER) {
            if (mDBHelper == null) {
                mDBHelper = CoreApplication.get(getContext(), DBHelper.KEY);
            }
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int matchCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchCode, uri);
        try {
            Cursor cursor = mDBHelper.query(tableName,
                    projection,
                    buildSelection(matchCode, tableName, selection),
                    buildSelectionArgs(matchCode, uri, selectionArgs),
                    null, null, sortOrder);

            cursor.setNotificationUri(getContext().getContentResolver(), uri);

            return cursor;
        } catch (Exception e) {
            return getErrorMatrixCursor(e);
        }
    }

    @Override
    public String getType(Uri uri) {
        int matchCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchCode, uri);

        if (matchCode == CODE_TABLE) {
            return CONTENT_DIR_TYPE + tableName;
        } else {
            return CONTENT_ITEM_TYPE + tableName;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableNameByUriCode(matchUri(uri), uri);

        long id = mDBHelper.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.withAppendedPath(BaseContract.AUTHORITY_URI, "/" + tableName + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int matchCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchCode, uri);

        int rowsDeleted = mDBHelper.delete(tableName,
                buildSelection(matchCode, tableName, selection),
                buildSelectionArgs(matchCode, uri, selectionArgs));

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int matchedUriCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchedUriCode, uri);

        int rowsUpdated = (int) mDBHelper.update(tableName,
                values,
                buildSelection(matchedUriCode, tableName, selection),
                buildSelectionArgs(matchedUriCode, uri, selectionArgs));

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, @NonNull ContentValues[] values) {

        mDBHelper.insert(getTableNameByUriCode(matchUri(uri), uri), null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return values.length;
    }

    /**
     * if current uri use ID - add this ID to selection as COLUMN_ID = ?
     * need add this ID as selectionArgs
     *
     * @param matchedUriCode uri code
     * @param tableName      table name
     * @param selection      default selection
     * @return rebuilt selection string
     */
    private String buildSelection(int matchedUriCode, String tableName, String selection) {

        if (matchedUriCode == CODE_TABLE) {
            return selection;
        }
        String selectionWithId = tableName + "." + BaseContract.COLUMN_ID + " = ?";
        if (selection == null) {
            selection = selectionWithId;
        } else {
            selection += " AND " + selectionWithId;
        }
        return selection;
    }

    /**
     * if current uri use ID - add this id to selectionArgs
     *
     * @param matchedUriCode uri code
     * @param selectionArgs  selectionArgs
     * @return selectionArgs with added ID
     */
    private String[] buildSelectionArgs(int matchedUriCode, Uri uri, String[] selectionArgs) {
        if (matchedUriCode == CODE_TABLE) {
            return selectionArgs;
        }

        if (selectionArgs != null) {
            selectionArgs = Arrays.copyOf(selectionArgs, selectionArgs.length + 1);
        } else {
            selectionArgs = new String[1];
        }
        selectionArgs[selectionArgs.length - 1] = uri.getLastPathSegment();

        return selectionArgs;
    }

    /**
     * Match uri or throw IllegalArgumentException if URI unknown
     *
     * @param uri uri for match
     * @return matched code
     */
    private int matchUri(Uri uri) {
        int matchCode = URI_MATCHER.match(uri);

        if (matchCode == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        } else {
            return matchCode;
        }
    }

    private String getTableNameByUriCode(int matchCode, Uri uri) {
        String tableName;
        if (matchCode == CODE_TABLE) {
            tableName = uri.getLastPathSegment();
        } else {             //ID used in URI
            List<String> listSegments = uri.getPathSegments();

            //uri=content://AUTHORITY/Note/1
            //in list item[0] = Note, item[1] = 1, size=2, -2 for get TableName segment
            tableName = listSegments.get(listSegments.size() - 2);
        }

        //check availability for specified table
        if (!IMPLEMENTED_TABLES.contains(tableName)) {
            throw new IllegalArgumentException("Table \"" + tableName + "\" not exists");
        }

        return tableName;
    }

    /**
     * Build MatrixCursor with column {@code TestProviderContract.ERROR_DESCRIPTION} and save
     * in first row error description from {@code exception}
     *
     * @param exception exception for saving
     * @return MatrixCursor with error description
     */
    private MatrixCursor getErrorMatrixCursor(Exception exception) {
        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{BaseContract.ERROR_DESCRIPTION});
        matrixCursor.addRow(new String[]{exception.getMessage()});

        return matrixCursor;
    }
}
