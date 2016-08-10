package com.example.kerwinyoder.logajog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kerwinyoder.logajog.database.ActivityDataPointDataSource;
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

public class DetailActivity extends AppCompatActivity {

    Activity activity;
    ArrayList<ActivityDataPoint> dataPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        activity = bundle.getParcelable(".database.model.Activity");

        updateUI();
    }

    private void updateUI() {
        TextView textView = (TextView) findViewById(R.id.detailDate);
        textView.setText(Formatter.getDate(activity.getStartTime()));

        textView = (TextView) findViewById(R.id.detailStartTime);
        textView.setText(Formatter.getTime(activity.getStartTime()));

        textView = (TextView) findViewById(R.id.detailDuration);
        textView.setText(Formatter.getDuration(activity.getDuration()));

        textView = (TextView) findViewById(R.id.detailAvgSpeed);
        textView.setText(Formatter.getSpeed(activity.getAvgSpeed()));

        textView = (TextView) findViewById(R.id.detailDistance);
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

    private ArrayList<Entry> getSpeedData() {
        long id = activity.getId();
        ActivityDataPointDataSource dataPointDataSource = new ActivityDataPointDataSource(this);
        dataPointDataSource.open();
        List<ActivityDataPoint> dataPoints = dataPointDataSource.findByActivityId(id);
        dataPointDataSource.close();
        ArrayList<Entry> entries = new ArrayList<>();
        int secondCount = 0;
        for (ActivityDataPoint dataPoint : dataPoints) {
            entries.add(new Entry((float) secondCount, (float) dataPoint.getSpeed()));
            secondCount += 10;
        }
        return entries;
    }
}
