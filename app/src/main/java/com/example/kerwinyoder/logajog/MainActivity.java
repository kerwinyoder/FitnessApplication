package com.example.kerwinyoder.logajog;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.kerwinyoder.logajog.database.ActivityDataSource;
import com.example.kerwinyoder.logajog.database.LogAJogContract;
import com.example.kerwinyoder.logajog.database.LogAJogDbHelper;
import com.example.kerwinyoder.logajog.database.Seed;
import com.example.kerwinyoder.logajog.database.model.Activity;
import com.example.kerwinyoder.logajog.database.model.ActivityDataPoint;
import com.example.kerwinyoder.logajog.utils.Formatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int DATA_POINT_RATE = 10; //the number of seconds between saved data points
    private boolean started = false;
    private long startTime; //the start time in milliseconds since the epoch
    private double speedSum = 0; //the sum of all the speeds; used for calculating the average speed
    private int speeds = 0; //the number of speeds used; used for calculating the average speed
    private float distance = 0; //the total distance traveled in meters
    private Location previousLocation; //the location from the previous location update
    private ArrayList<ActivityDataPoint> dataPoints = new ArrayList<>(500);
    private int updateCount;
    LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityDataSource activityDataSource = new ActivityDataSource(this);
        activityDataSource.open();
        activityDataSource.close();
//        Seed.seed(this);
    }

    /**
     * Handles the onclick event for the start/stop activity button
     *
     * @param view The start/stop activity button
     */
    public void activityBtnClick(View view) {
        if (started) {
            stopActivity();
        } else {
            startActivity();
        }
    }

    /*
     * Starts tha activity; Ensures that the GPS is enabled and the app has permissions or requests permissions;
     * Registers a location listener for location updates and starts the duration counter.
     */
    private void startActivity() {
        boolean gpsEnabled = false;
        Chronometer duration = (Chronometer) findViewById(R.id.duration);
        Button activityBtn = (Button) findViewById(R.id.activityBtn);
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //ensure that the GPS is enabled
        gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            AlertDialog.Builder builder = getGPSDisabledDialog();
            builder.show();
        }

        if (gpsEnabled) {
            //Check for runtime permission in API 23 and above; All lower API levels request permissions at installation
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1024);
                    return;
                }
            }
            //Register a location listener for location updates.
            listener = getLocationListener();
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);
            listener.onLocationChanged(null);
            startTime = System.currentTimeMillis();
            started = true;
            //start the duration counter
            duration.setBase(SystemClock.elapsedRealtime());
            duration.start();
            activityBtn.setText("Stop Activity");
        }
    }

    /*
     * Stops the activity by stopping the clock and removing the location listener updates
     */
    private void stopActivity() {
        //freeze the counter and stop receiving updates
        Chronometer duration = (Chronometer) findViewById(R.id.duration);
        duration.stop();
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.removeUpdates(listener);

        //Create an Activity with the data
        int totalDuration = (int) (SystemClock.elapsedRealtime() - duration.getBase()) / 1000; //convert from milliseconds to seconds
        float avgSpeed = (float) (speedSum / speeds);
        Activity activity = new Activity(startTime, totalDuration, avgSpeed, distance);

        Log.i("LogAJog", String.valueOf(dataPoints.size()));

        Intent intent = new Intent(this, CompletedActivity.class);
        intent.putExtra(".database.model.Activity", activity);
        intent.putExtra(".database.model.ActivityDataPoint", dataPoints);
        startActivity(intent);
    }

    /**
     * Handles the results of a permissions request in API 23 and above
     *
     * @param requestCode  the code provided in the original request for communication purposes
     * @param permissions  the permissions requested
     * @param grantResults the results of the permissions request
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1024 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity();
        }
    }

    /*
     * Gets a LocationListener with overridden methods to handle location updates.
     * @return the LocationListener
     */
    private LocationListener getLocationListener() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                TextView speedView = (TextView) findViewById(R.id.speed);
                TextView avgSpeedView = (TextView) findViewById(R.id.avgSpeed);
                TextView distanceView = (TextView) findViewById(R.id.distance);
                if (location == null) {
                    speedView.setText("-.- m/s");
                    avgSpeedView.setText("-.- m/s");
                    distanceView.setText("-.- m");
                } else {
                    float currentSpeed = location.getSpeed();
                    speedView.setText(Formatter.getSpeed(currentSpeed));
                    speedSum += currentSpeed;
                    ++speeds;
                    avgSpeedView.setText(Formatter.getSpeed((float) speedSum / speeds));
                    if (previousLocation == null) {
                        previousLocation = location;
                    }
                    distance += location.distanceTo(previousLocation);
                    distanceView.setText(Formatter.getDistance(distance));
                    previousLocation = location;
                    if (updateCount++ % DATA_POINT_RATE == 0) {
                        dataPoints.add(new ActivityDataPoint(currentSpeed));
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    /*
     * Gets the GPS disabled alert dialog builder
     * @return the AlertDialog.Builder initialized for the GPS disabled alert dialog
     */
    @NonNull
    private AlertDialog.Builder getGPSDisabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.gps_disabled_title);
        builder.setMessage(R.string.gps_disabled_message);

        builder.setPositiveButton(R.string.activate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int paramInt) {
                dialogInterface.cancel();
            }
        });
        return builder;
    }

    /**
     * Handles the onclick event for the reset button; resets the view
     *
     * @param view The reset button
     */
    public void resetBtnClick(View view) {
        //stop the activity
        Chronometer duration = (Chronometer) findViewById(R.id.duration);
        duration.stop();
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (listener != null) {
            manager.removeUpdates(listener);
        }
        //reset all variables
        started = false;
        startTime = 0;
        speedSum = 0;
        speeds = 0;
        distance = 0;
        previousLocation = null;
        duration.setBase(SystemClock.elapsedRealtime());

        //reset the UI
        TextView speed = (TextView) findViewById(R.id.speed);
        speed.setText(R.string.initial_speed);
        TextView avgSpeed = (TextView) findViewById(R.id.avgSpeed);
        avgSpeed.setText(R.string.initial_avg_speed);
        TextView distance = (TextView) findViewById(R.id.distance);
        distance.setText(R.string.initial_distance);
        Button activityBtn = (Button) findViewById(R.id.activityBtn);
        activityBtn.setText("Start Activity");
    }

    public void btnClick(View view) {
        Intent intent = new Intent(this, ActivitiesActivity.class);
        startActivity(intent);
    }
}
