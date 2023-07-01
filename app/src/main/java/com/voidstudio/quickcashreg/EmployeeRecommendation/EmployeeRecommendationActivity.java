package com.voidstudio.quickcashreg.EmployeeRecommendation;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.InAppActivityEmployer;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;

import users.Employee;

public class EmployeeRecommendationActivity extends AppCompatActivity {
    ListView recommendEmployeeList;
    SeekBar seekBarForDistance;
    TextView distanceInput;
    EmployeeRecommendation employeeRecommendation = new EmployeeRecommendation();
    Firebase firebase;
    Job job;
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_recommendation);
        firebase = employeeRecommendation.getFirebase();

        Bundle input = getIntent().getExtras();

        String name = input.getString("Name");
        String tag = input.getString("Tag");
        String wage = input.getString("Wage");
        String duration = input.getString("Duration");
        String title = input.getString("Title");

        job = new Job(title, wage, duration, tag, name);
        Location location = new Location("gps");
        job.getLocation();
        double[] coords = job.jobLocation.getLatLong();
        if(coords != null) {
            location.setLatitude(coords[0]);
            location.setLongitude(coords[1]);
            job.setLocation(location);
        }
        recommendEmployeeList = findViewById(R.id.recommendationEmployeeList);
        seekBarForDistance = (SeekBar)findViewById(R.id.seekBarForDistance);
        distanceInput = (TextView)findViewById(R.id.distanceInput);

        setupSeekBarListener();
        firebase.listenerForUser_Ref();
        skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(this::onClick);
    }

    public void setupSeekBarListener() {
        seekBarForDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distanceInput.setText(String.valueOf(getMaxDistanceInKM()));
                getRecommendInfo(getMaxDistanceInKM());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
    }

    private void getRecommendInfo(double maxDistance) {
        ArrayList<Employee> employees= firebase.getRecommendList();

        ArrayList<Employee> recommendList = employeeRecommendation.getRecommendation(job, employees, maxDistance);
        String[] recommendInfoList = new String[recommendList.size()];

        for (int i = 0; i < recommendList.size(); i++) {
            recommendInfoList[i] = recommendList.get(i).recommendInfo();
        }
        setList(recommendInfoList);
    }

    private void setList(String[] recommendInfoList) {
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, recommendInfoList);
        recommendEmployeeList.setAdapter(arr);
        int b = 0;
    }

    public int getSeekBarForDistanceValue() {
        int progress = seekBarForDistance.getProgress();
        return progress;
    }

    public int getMaxDistanceInKM() {
        return getSeekBarForDistanceValue() * 3;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.skip) {
            Intent postedSwitch = new Intent(EmployeeRecommendationActivity.this, InAppActivityEmployer.class);
            startActivity(postedSwitch);
        }
    }
}





























