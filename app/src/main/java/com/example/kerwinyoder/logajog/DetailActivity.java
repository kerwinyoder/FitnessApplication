package com.example.kerwinyoder.logajog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kerwinyoder.logajog.database.model.Activity;
import com.example.kerwinyoder.logajog.database.model.ActivityDataPoint;
import com.example.kerwinyoder.logajog.utils.Formatter;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Activity activity;
    ArrayList<ActivityDataPoint> dataPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        activity = bundle.getParcelable(".database.model.Activity");
        dataPoints = bundle.getParcelableArrayList(".database.model.ActivityDataPoint");
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

    }
}
