package com.example.kerwinyoder.logajog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kerwinyoder.logajog.database.LogAJogContract;

/**
 * A LogAJogDbHelper which extends SQLiteOpenHelper and assists with database usage
 */
public class LogAJogDbHelper extends SQLiteOpenHelper {

    //If the database schema is changed, the database version must be incremented
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LogAJog.db";

    /**
     * Creates a new LogAJogDbHelper using the given Context
     *
     * @param context the current context
     */
    public LogAJogDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //Create the activity table
        database.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s REAL, %s REAL)",
                LogAJogContract.Activity.TABLE_NAME,
                LogAJogContract.Activity._ID,
                LogAJogContract.Activity.COLUMN_NAME_START_TIME,
                LogAJogContract.Activity.COLUMN_NAME_DURATION,
                LogAJogContract.Activity.COLUMN_NAME_AVG_SPEED,
                LogAJogContract.Activity.COLUMN_NAME_DISTANCE));

        //Create the activity_data_point table
        database.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s REAL)",
                LogAJogContract.ActivityDataPoint.TABLE_NAME,
                LogAJogContract.ActivityDataPoint._ID,
                LogAJogContract.ActivityDataPoint.COLUMN_NAME_ACTIVITY_ID,
                LogAJogContract.ActivityDataPoint.COLUMN_NAME_SPEED));
        Log.i("LogAJog", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //Drop the tables and recreate them since this app is never going into production anyway
        database.execSQL("DROP TABLE IF EXISTS " + LogAJogContract.Activity.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + LogAJogContract.ActivityDataPoint.TABLE_NAME);
        onCreate(database);
        Log.i("LogAJog", "Database upgraded or downgraded");
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }
}
