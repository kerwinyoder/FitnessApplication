package com.example.kerwinyoder.logajog.database;

import android.content.Context;

import com.example.kerwinyoder.logajog.MainActivity;
import com.example.kerwinyoder.logajog.database.model.Activity;
import com.example.kerwinyoder.logajog.database.model.ActivityDataPoint;

/**
 * A Seed which provides a utility method for seeding the database
 */
public class Seed {
    public static void seed(Context context) {
        long id;
        ActivityDataSource activityDataSource = new ActivityDataSource(context);
        activityDataSource.open();

        Activity activity = new Activity(1470744442234L, 60, 2.5F, 150);
        activityDataSource.create(activity);
        activity = new Activity(1470744442334L, 360, 1.25F, 450);
        activityDataSource.create(activity);
        activity = new Activity(1470744442434L, 1060, 3.5F, 3710);
        activityDataSource.create(activity);
        activityDataSource.close();

        ActivityDataPointDataSource activityDataPointDataSource = new ActivityDataPointDataSource(context);
        activityDataPointDataSource.open();
        ActivityDataPoint activityDataPoint = new ActivityDataPoint(1, 2.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(1, 2.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(1, 2.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(1, 2.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(1, 2.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(1, 2.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(1, 2.5F);
        activityDataPointDataSource.create(activityDataPoint);

        activityDataPoint = new ActivityDataPoint(2, 1.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(2, 1.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(2, 1.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(2, 1.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(2, 1.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(2, 1.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(2, 1.5F);
        activityDataPointDataSource.create(activityDataPoint);

        activityDataPoint = new ActivityDataPoint(3, 3.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(3, 3.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(3, 3.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(3, 3.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(3, 3.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(3, 3.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPoint = new ActivityDataPoint(3, 3.5F);
        activityDataPointDataSource.create(activityDataPoint);
        activityDataPointDataSource.close();
    }
}
