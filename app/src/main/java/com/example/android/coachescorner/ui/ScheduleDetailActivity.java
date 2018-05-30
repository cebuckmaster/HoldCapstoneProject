package com.example.android.coachescorner.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.coachescorner.R;
import com.example.android.coachescorner.ui.fragments.ScheduleDetailFragment;

public class ScheduleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ScheduleDetailFragment scheduleDetailFragment = new ScheduleDetailFragment();
        fragmentManager.beginTransaction()
                .add(R.id.schedule_detail_container, scheduleDetailFragment)
                .commit();


    }
}
