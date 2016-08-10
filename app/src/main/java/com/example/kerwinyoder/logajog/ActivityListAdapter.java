package com.example.kerwinyoder.logajog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kerwinyoder.logajog.database.model.Activity;
import com.example.kerwinyoder.logajog.utils.Formatter;

import java.util.Date;
import java.util.List;

/**
 * A customized ArrayAdapter for displaying a list of Activities
 */
public class ActivityListAdapter extends ArrayAdapter<Activity> {

    List<Activity> activities;

    public ActivityListAdapter(Context context, int resource, List<Activity> objects) {
        super(context, resource, objects);
        activities = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //if the view does not already exist, create a new one;
        //if an available one does exist, it will be recycled by ListView
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_activities_item, parent, false);
        }

        Activity activity = activities.get(position);

        //Update the UI date
        TextView textView = (TextView) convertView.findViewById(R.id.itemDate);
        textView.setText(Formatter.getDate(activity.getStartTime()));

        //Update the UI duration
        textView = (TextView) convertView.findViewById(R.id.itemDistance);
        textView.setText(Formatter.getDuration(activity.getDuration()));

        //Update the UI distance
        textView = (TextView) convertView.findViewById(R.id.itemDuration);
        textView.setText(Formatter.getDistance(activity.getDistance()));

        return convertView;
    }
}
