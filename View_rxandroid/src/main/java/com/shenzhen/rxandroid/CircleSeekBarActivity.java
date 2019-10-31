package com.shenzhen.rxandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shenzhen.rxandroid.view.CircleSeekBar;
import com.shenzhen.rxandroid.view.FrequencyView;

public class CircleSeekBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_seek_bar);

       CircleSeekBar circless =  findViewById(R.id.view_seek_bar);
       circless.setMax(100);
       circless.setProgress(90);
       circless.setProgressWidth(6);

        FrequencyView view_frequency = findViewById(R.id.view_frequency);
        view_frequency.setmFreqCount(6);
        view_frequency.setmFreqOffset(10);//间隔
        view_frequency.setmFreqColor(Color.RED);
        view_frequency.setPlaying(true);
    }
}
