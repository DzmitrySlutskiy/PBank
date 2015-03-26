package com.citi.piggybank.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * DBHelper
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class DBHelper extends SQLiteOpenHelper {

    /* ---------------- Constants -------------- */
    public static final String KEY = DBHelper.class.getSimpleName();

    private static final String DEFAULT_DB_NAME = "piggybank.db";
    private static final int DB_VERSION = 1;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    /* ---------------- public -------------- */

    public DBHelper(Context context) {
        super(context, DEFAULT_DB_NAME, null, DB_VERSION);

    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PiggyContract.CREATE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(KEY, "onUpgrade database from ver: " + oldVersion + " to ver: " + newVersion + "\nDelete all tables,");
        db.execSQL(DROP_TABLE + PiggyContract.PATH);
        onCreate(db);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        SQLiteDatabase readable = getReadableDatabase();
        return readable.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    /**
     * Convenience method for updating rows in the database.
     *
     * @param table       the table to update in
     * @param values      a map from column names to new column values. null is a
     *                    valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *                    Passing null will update all rows.
     * @param whereArgs   You may include ?s in the where clause, which
     *                    will be replaced by the values from whereArgs. The values
     *                    will be bound as Strings.
     * @return the number of rows affected
     */
    public long update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        beginTransactionNonExclusive(writableDatabase);
        try {
            long result = writableDatabase.updateWithOnConflict(table, values,
                    whereClause, whereArgs, SQLiteDatabase.CONFLICT_REPLACE);
            setTransactionSuccessful(writableDatabase);
            return result;
        } finally {
            endTransaction(writableDatabase);
        }
    }

    public long updateNonTransaction(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return (long) writableDatabase.updateWithOnConflict(table, values,
                whereClause, whereArgs, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param table       the table to delete from
     * @param whereClause the optional WHERE clause to apply when deleting.
     *                    Passing null will delete all rows.
     * @param whereArgs   You may include ?s in the where clause, which
     *                    will be replaced by the values from whereArgs. The values
     *                    will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     */
    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.delete(table, whereClause, whereArgs);
    }

    /**
     * Convenience method for inserting a row/rows into the database.
     *
     * @param table          the table to insert the row into
     * @param nullColumnHack optional; may be <code>null</code>.
     *                       SQL doesn't allow inserting a completely empty row without
     *                       naming at least one column name.  If your provided <code>values</code> is
     *                       empty, no column names are known and an empty row can't be inserted.
     *                       If not set to null, the <code>nullColumnHack</code> parameter
     *                       provides the name of nullable column name to explicitly insert a NULL into
     *                       in the case where your <code>values</code> is empty.
     * @param values         this map contains the initial column values for the
     *                       row. The keys should be the column names and the values the
     *                       column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long insert(String table, String nullColumnHack, ContentValues... values) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        long result = - 1;
        long rowsInserted = 0;
        beginTransactionNonExclusive(writableDatabase);
        try {
            for (ContentValues value : values) {     //more than one used for bulkInsert
                result = writableDatabase.insertWithOnConflict(table,
                        nullColumnHack, value, SQLiteDatabase.CONFLICT_REPLACE);

                if (values.length > 1 && result != - 1) {   //calc count rows inserted
                    rowsInserted++;
                }
            }
            setTransactionSuccessful(writableDatabase);
            return (values.length > 1 ? rowsInserted : result);
        } finally {
            endTransaction(writableDatabase);
        }
    }

    public long insertNonTransaction(String table, ContentValues value) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.insert(table, null, value);
    }

    //      TRANSACTIONS SUPPORT BLOCK
    private void beginTransactionNonExclusive(SQLiteDatabase writableDatabase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            writableDatabase.beginTransactionNonExclusive();
        } else {
            writableDatabase.beginTransaction();
        }
    }

    private void setTransactionSuccessful(SQLiteDatabase writableDatabase) {
        writableDatabase.setTransactionSuccessful();
    }

    private void endTransaction(SQLiteDatabase writableDatabase) {
        writableDatabase.endTransaction();
    }

    public void beginTransactionNonExclusive() {
        beginTransactionNonExclusive(getWritableDatabase());
    }

    public void setTransactionSuccessful() {
        setTransactionSuccessful(getWritableDatabase());
    }

    public void endTransaction() {
        endTransaction(getWritableDatabase());
    }
}
