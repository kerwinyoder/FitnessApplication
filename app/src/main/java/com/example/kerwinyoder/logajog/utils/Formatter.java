package com.example.kerwinyoder.logajog.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A Formatter for string values used throughout the app
 */
public class Formatter {
    /**
     * Gets a formatted date string
     *
     * @param time the time as a Unix time in milliseconds
     * @return the formatted date string
     */
    public static String getDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, M/d/yy");
        return dateFormat.format(date);
    }

    /**
     * Gets the formatted time string
     *
     * @param time the time as a Unix time in milliseconds
     * @return the formatted time string
     */
    public static String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(date);
    }

    /**
     * Gets the formatted duration string
     *
     * @param duration the duration
     * @return the formatted duration string
     */
    public static String getDuration(int duration) {
        return String.format("%02d:%02d:%02d", duration / 3600, duration / 60, duration % 60);
    }


    /**
     * Gets the formatted distance string
     *
     * @param distance the distance to format
     * @return the formmated distance string
     */
    public static String getDistance(float distance) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.## m");
        return formatter.format(distance);
    }

    /**
     * Gets the formatted speed string
     * @param speed the speed
     * @return the formatted speed string
     */
    public static String getSpeed(float speed) {
        return String.format("%.2f m/s", speed);
    }
}
