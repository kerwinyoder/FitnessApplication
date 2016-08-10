package com.example.kerwinyoder.logajog;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kerwinyoder.logajog.database.ActivityDataPointDataSource;
import com.example.kerwinyoder.logajog.database.ActivityDataSource;
import com.example.kerwinyoder.logajog.database.model.Activity;
import com.example.kerwinyoder.logajog.database.model.ActivityDataPoint;
import com.example.kerwinyoder.logajog.utils.Formatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class CompletedActivity extends AppCompatActivity {
    Activity activity;
    ArrayList<ActivityDataPoint> dataPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);
        Bundle bundle = getIntent().getExtras();
        activity = bundle.getParcelable(".database.model.Activity");
        dataPoints = bundle.getParcelableArrayList(".database.model.ActivityDataPoint");
        updateUI();
    }

    private void updateUI() {
        TextView textView = (TextView) findViewById(R.id.completedDate);
        textView.setText(Formatter.getDate(activity.getStartTime()));

        textView = (TextView) findViewById(R.id.completedStartTime);
        textView.setText(Formatter.getTime(activity.getStartTime()));

        textView = (TextView) findViewById(R.id.completedDuration);
        textView.setText(Formatter.getDuration(activity.getDuration()));

        textView = (TextView) findViewById(R.id.completedAvgSpeed);
        textView.setText(Formatter.getSpeed(activity.getAvgSpeed()));

        textView = (TextView) findViewById(R.id.completedDistance);
        textView.setText(Formatter.getDistance(activity.getDistance()));

        //Set up the line chart
        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = getSpeedData();
        LineDataSet dataSet = new LineDataSet(entries, "Speed");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.setDescription(null);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineWidth(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinValue(0f);
        YAxis left = chart.getAxisLeft();
        left.setDrawGridLines(false);
        left.setAxisMinValue(0f);
        left.setDrawAxisLine(true);
        left.setAxisLineWidth(1f);
        YAxis right = chart.getAxisRight();
        right.setDrawGridLines(false);
        right.setAxisMinValue(0f);
        right.setAxisLineWidth(1f);
        chart.invalidate(); // refresh
    }

    public void discardButtonClick(View view) {
        getDiscardMessageDialog().show();
    }

    public void saveButtonClick(View view) {
        //save the activity in the database
        ActivityDataSource activityDataSource = new ActivityDataSource(this);
        activityDataSource.open();
        activity = activityDataSource.create(activity);
        activityDataSource.close();

        //save the activity's data points in the database
        ActivityDataPointDataSource dataPointDataSource = new ActivityDataPointDataSource(this);
        dataPointDataSource.open();
        long activityId = activity.getId();
        Log.i("LogAJog", String.format("activityId = %d", activityId));
        for (ActivityDataPoint dataPoint : dataPoints) {
            dataPoint.setActivityId(activityId);
            dataPointDataSource.create(dataPoint);
            Log.i("LogAJog", String.format("saveButtonClick activityId = %d", dataPoint.getActivityId()));
        }
        dataPointDataSource.close();

        //return to the Main Activity
        Intent intent = new Intent(CompletedActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private ArrayList<Entry> getSpeedData() {
        ArrayList<Entry> entries = new ArrayList<>();
        int secondCount = 0;
        for (ActivityDataPoint dataPoint : dataPoints) {
            entries.add(new Entry((float) secondCount, (float) dataPoint.getSpeed()));
            secondCount += 10;
        }
        return entries;
    }

    /*
     * Gets the discard activity alert dialog builder
     * @return the AlertDialog.Builder initialized for the discard activity alert dialog
     */
    @NonNull
    private AlertDialog.Builder getDiscardMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CompletedActivity.this);
        builder.setTitle(R.string.discard_activity_title);
        builder.setMessage(R.string.discard_activity_message);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(CompletedActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int paramInt) {
                dialogInterface.cancel();
            }
        });
        return builder;
    }
}
