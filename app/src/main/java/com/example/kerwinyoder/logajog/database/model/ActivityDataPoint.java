package com.example.kerwinyoder.logajog.database.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A model of the activity_data_point table
 */
public class ActivityDataPoint implements Parcelable {
    private long id;
    private long activityId;
    private float speed;

    /**
     * Creates a new ActivityDataPoint with all values initialized to 0
     */
    public ActivityDataPoint() {
        id = 0;
        activityId = 0;
        speed = 0;
    }

    /**
     * Creates a new ActivityDataPoint with the given speed. All other values are initialized to 0.
     */
    public ActivityDataPoint(float speed) {
        id = 0;
        activityId = 0;
        this.speed = speed;
    }

    /**
     * Creates a new ActivityDataPoint with the given values
     * @param activityId the activity id (foreign key) of the associated Activity
     * @param speed the speed of the ActivityDataPoint
     */
    public ActivityDataPoint(int activityId, float speed) {
        this.activityId = activityId;
        this.speed = speed;
    }

    public ActivityDataPoint(Parcel parcel) {
        id = parcel.readLong();
        activityId = parcel.readLong();
        speed= parcel.readFloat();
    }

    /**
     * Gets the id of the ActivityDataPoint
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the ActivityDataPoint
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the activityId of the ActivityDataPoint
     * @return the activityId of the ActivityDataPoint
     */
    public long getActivityId() {
        return activityId;
    }

    /**
     * Sets the activityId of the ActivityDataPoint
     * @param Id
     */
    public void setActivityId(long Id) {
        this.activityId = activityId;
    }

    /**
     * Sets the speed of the ActivityDataPoint
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Gets the speed of the ActivityDataPoint
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(activityId);
        parcel.writeFloat(speed);
    }

    public static final Parcelable.Creator<ActivityDataPoint> CREATOR = new Parcelable.Creator<ActivityDataPoint>(){
        @Override
        public ActivityDataPoint createFromParcel(Parcel parcel) {
            return new ActivityDataPoint(parcel);

        }

        @Override
        public ActivityDataPoint[] newArray(int size) {
            return new ActivityDataPoint[size];
        }
    };
}
