package com.example.kerwinyoder.logajog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kerwinyoder.logajog.database.ActivityDataSource;
import com.example.kerwinyoder.logajog.database.model.Activity;

import java.util.List;

public class ActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        //get the list of activities and pass them to the ListView
        ActivityDataSource activityDataSource = new ActivityDataSource(this);
        activityDataSource.open();
        final List<Activity> activities = activityDataSource.findAll();
        activityDataSource.close();
        ActivityListAdapter adapter = new ActivityListAdapter(this, R.layout.activity_activities_item, activities);
        ListView listView = (ListView) findViewById(R.id.ActivitiesListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivitiesActivity.this, DetailActivity.class);
                Activity activity = activities.get(i);
                intent.putExtra(".database.model.Activity", activity);
                startActivity(intent);
            }
        });
    }
}
