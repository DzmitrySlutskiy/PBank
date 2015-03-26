package com.citi.piggybank.utils;

import android.database.Cursor;

/**
 * CursorUtils
 * Version information
 * 08.01.2015
 * Created by Dzmitry Slutskiy.
 */
public class CursorUtils {

    private CursorUtils() {/*   code    */}

    public static int getInt(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return 0;
        }
        return cursor.getInt(columnIndex);
    }

    public static long getLong(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return 0l;
        }
        return cursor.getLong(columnIndex);
    }

    public static String getString(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return "";
        }
        String result = cursor.getString(columnIndex);
        return result == null ? "" : result;
    }

    public static void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public static boolean isEmpty(Cursor cursor) {
        return (cursor == null || cursor.getCount() == 0);
    }

    /**
     * Used for load icon for user from cursor
     *
     * @param cursor  cursor for searching column
     * @param columns array for loading
     * @return string
     */
    public static String getString(Cursor cursor, String... columns) {
        String result = "";
        for (String columnName : columns) {
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex < 0) {
                continue;
            }
            result = cursor.getString(columnIndex);
        }
        return result;
    }
}
