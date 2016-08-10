package com.example.kerwinyoder.logajog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kerwinyoder.logajog.database.model.Activity;
import com.example.kerwinyoder.logajog.database.model.ActivityDataPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * A data source for the activity table
 */
public class ActivityDataSource {

    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase database;

    public ActivityDataSource(Context context) {
        databaseHelper = new LogAJogDbHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Activity create(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(LogAJogContract.Activity.COLUMN_NAME_START_TIME, activity.getStartTime());
        values.put(LogAJogContract.Activity.COLUMN_NAME_DURATION, activity.getDuration());
        values.put(LogAJogContract.Activity.COLUMN_NAME_AVG_SPEED, activity.getAvgSpeed());
        values.put(LogAJogContract.Activity.COLUMN_NAME_DISTANCE, activity.getDistance());
        long id = database.insert(LogAJogContract.Activity.TABLE_NAME, null, values);
        activity.setId(id);
        return activity;
    }

    /**
     * Gets a list of all of the activities sorted from most recent to least recent
     *
     * @return
     */
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();
        Cursor cursor = database.query(LogAJogContract.Activity.TABLE_NAME, LogAJogContract.Activity.ALL_COLUMNS, null, null, null, null, LogAJogContract.Activity.COLUMN_NAME_START_TIME + " DESC");
        int count = cursor.getCount();
        Log.i("LogAJog", "Returned " + count + " records");

        if (count > 0) {
            while (cursor.moveToNext()) {
                Activity activity = new Activity();
                activity.setId(cursor.getLong(cursor.getColumnIndex(LogAJogContract.Activity.COLUMN_ID)));
                activity.setStartTime(cursor.getLong(cursor.getColumnIndex(LogAJogContract.Activity.COLUMN_NAME_START_TIME)));
                activity.setDuration(cursor.getInt(cursor.getColumnIndex(LogAJogContract.Activity.COLUMN_NAME_DURATION)));
                activity.setAvgSpeed(cursor.getFloat(cursor.getColumnIndex(LogAJogContract.Activity.COLUMN_NAME_AVG_SPEED)));
                activity.setDistance(cursor.getFloat(cursor.getColumnIndex(LogAJogContract.Activity.COLUMN_NAME_DISTANCE)));
                activities.add(activity);
            }
        }
        return activities;
    }
}
