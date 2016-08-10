package com.example.kerwinyoder.logajog.database;

import android.provider.BaseColumns;

/**
 * The LogAJog contract which defines all of the database constant including table names and column names
 */
public final class LogAJogContract {

    //prevent the Contract class from being accidentally instantiated
    private LogAJogContract() {
    }

    /**
     * A class representing the activity table in the database
     */
    public static abstract class Activity implements BaseColumns {
        public static final String TABLE_NAME = "activity";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_AVG_SPEED = "avg_speed";
        public static final String COLUMN_NAME_DISTANCE = "distance";
        public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_NAME_START_TIME, COLUMN_NAME_DURATION, COLUMN_NAME_AVG_SPEED, COLUMN_NAME_DISTANCE};
    }

    /**
     * A class representing the activity_data_point table in the database
     */
    public static abstract class ActivityDataPoint implements BaseColumns {
        public static final String TABLE_NAME = "activity_data_point";
        public static final String COLUMN_ID = "_id";
        //foreign key to the Activity table
        public static final String COLUMN_NAME_ACTIVITY_ID = "activity_id";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_NAME_ACTIVITY_ID, COLUMN_NAME_SPEED};
    }
}
