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
 * A data source for the activity_data_point table
 */
public class ActivityDataPointDataSource {
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase database;

    public ActivityDataPointDataSource(Context context) {
        databaseHelper = new LogAJogDbHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public ActivityDataPoint create(ActivityDataPoint dataPoint) {
        ContentValues values = new ContentValues();
        values.put(LogAJogContract.ActivityDataPoint.COLUMN_NAME_ACTIVITY_ID, dataPoint.getActivityId());
        values.put(LogAJogContract.ActivityDataPoint.COLUMN_NAME_SPEED, dataPoint.getSpeed());
        long id = database.insert(LogAJogContract.ActivityDataPoint.TABLE_NAME, null, values);
        dataPoint.setId(id);
        Log.i("LogAJog", "ActivityDataPoint created with id = " + id);
        return dataPoint;
    }

    public List<ActivityDataPoint> findAll() {
        List<ActivityDataPoint> dataPoints = new ArrayList<>();
        Cursor cursor = database.query(LogAJogContract.ActivityDataPoint.TABLE_NAME, LogAJogContract.ActivityDataPoint.ALL_COLUMNS, null, null, null, null, null);

        int count = cursor.getCount();
        if(count > 0) {
            while(cursor.moveToNext()) {
                ActivityDataPoint dataPoint = new ActivityDataPoint();
                dataPoint.setId(cursor.getLong(cursor.getColumnIndex(LogAJogContract.Activity.COLUMN_ID)));
                dataPoint.setActivityId(cursor.getInt(cursor.getColumnIndex(LogAJogContract.ActivityDataPoint.COLUMN_NAME_ACTIVITY_ID)));
                dataPoint.setSpeed(cursor.getFloat(cursor.getColumnIndex(LogAJogContract.ActivityDataPoint.COLUMN_NAME_SPEED)));
                dataPoints.add(dataPoint);
            }
        }
        Log.i("LogAJog", "Returned " + count + " records");
        return dataPoints;
    }
}
