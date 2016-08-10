package com.example.kerwinyoder.logajog.database.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A model of the activity table
 */
public class Activity implements Parcelable {
    private long id;
    private long startTime;
    private int duration;
    private float avgSpeed;
    private float distance;

    /**
     * Creates a new Activity with all values initialized to 0
     */
    public Activity() {
        id = 0;
        startTime = 0;
        duration = 0;
        avgSpeed = 0;
        distance = 0;
    }

    /**
     * Creates a new Activity with the given values.
     *
     * @param startTime the start time of the activity
     * @param duration  the duration of the activity
     * @param avgSpeed  the average speed of the activity
     * @param distance  the distance of the activity
     */
    public Activity(long startTime, int duration, float avgSpeed, float distance) {
        this.startTime = startTime;
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.distance = distance;
    }

    /**
     * Creates a new Activity with the given values.
     *
     * @param id        the id of the activity
     * @param startTime the start time of the activity
     * @param duration  the duration of the activity
     * @param avgSpeed  the average speed of the activity
     * @param distance  the distance of the activity
     */
    public Activity(long id, long startTime, int duration, float avgSpeed, float distance) {
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.distance = distance;
    }

    public Activity(Parcel parcel) {
        id = parcel.readLong();
        startTime = parcel.readLong();
        duration = parcel.readInt();
        avgSpeed = parcel.readFloat();
        distance = parcel.readFloat();
    }

    /**
     * Gets the id of the Activity
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the Activity
     *
     * @param id the id (primary key) of the Activity
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the start time for the activity
     *
     * @return the start time for the activity
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time for the activity
     *
     * @param startTime the start time of the activity
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the duration of the activity
     *
     * @return the duration of the activity
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the activity
     *
     * @param duration the duration of the activity
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Gets the average speed travelled during this activity
     *
     * @return the average speed travelled during this activity
     */
    public float getAvgSpeed() {
        return avgSpeed;
    }


    /**
     * Sets the average speed travelled during this activity to the given average speed
     *
     * @param avgSpeed the average speed for the activity
     */
    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    /**
     * Gets the distance travelled during this activity
     *
     * @return
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Sets the distance travelled during this activity to the given distance
     *
     * @param distance the distance travelled during the activity
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        //this class has no user-defined types, so just return 0
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(startTime);
        parcel.writeInt(duration);
        parcel.writeFloat(avgSpeed);
        parcel.writeFloat(distance);
    }

    public static final Parcelable.Creator<Activity> CREATOR = new Parcelable.Creator<Activity>(){
        @Override
        public Activity createFromParcel(Parcel parcel) {
            return new Activity(parcel);

        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
}
