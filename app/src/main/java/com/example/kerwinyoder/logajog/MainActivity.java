package com.example.kerwinyoder.logajog;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private boolean started = false;
    private long startTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void activityBtnClick(View view) {
        Chronometer duration = (Chronometer) findViewById(R.id.duration);
        Button activityBtn = (Button) findViewById(R.id.activityBtn);
        if(started) {
            duration.stop();
        }
        else {
            started = true;
            duration.setBase(SystemClock.elapsedRealtime());
            duration.start();
            activityBtn.setText("Stop Activity");
        }
    }
}
