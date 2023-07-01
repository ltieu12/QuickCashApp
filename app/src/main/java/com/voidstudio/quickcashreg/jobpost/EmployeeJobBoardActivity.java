package com.voidstudio.quickcashreg.jobpost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voidstudio.quickcashreg.InAppActivityEmployee;
import com.voidstudio.quickcashreg.R;

import java.util.ArrayList;

import users.Employee;

public class EmployeeJobBoardActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {

  public static final String MY_PREFS = "MY_PREFS";
  private static final String JOB_NAME = "job name";
  private static final String JOB_DURATION = "job duration";
  private static final String JOB_DATE_POSTED = "job date posted";
  private static final String JOB_TAG = "job tag";
  private static final String JOB_EMPLOYER = "job employer";
  private static final String JOB_WAGE = "job wage";
  RecyclerAdapter adapter;
  static String extractedJob;
  static String extractedWage;
  static String extractedTag;
  private Employee employee;
  private Job job;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employee_job_board);
    employee = InAppActivityEmployee.employee;
    this.loadSmallTasks();
  }

  protected void storeTasksAndLocation2SharedPrefs(ArrayList<String> tasks) {
    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    for (int i = 0; i < tasks.size(); i++) {
      if (i % 2 == 0) {
        //Replace with location feature
        editor.putString(tasks.get(i), "Halifax");
      } else {
        editor.putString(tasks.get(i), "Dhaka");
      }
    }
    //also add the lat long (Replace with location feature)
    editor.putString("JobCity", "44.65,-63.58");
    editor.putString("Dhaka", "23.81,90.41");
    editor.apply();
  }

  protected void loadSmallTasks() {
    ArrayList<String> tasks = new ArrayList<>();
    ArrayList<Job> jobList = (ArrayList<Job>) employee.getAllJobs();
    String preference = employee.getPreference();

    if (preference != null && !preference.equals("")) {
      for (Job j: jobList) {
        if (j.getTag().equals(employee.getPreference())) {
          Toast.makeText(this, j.getTag(), Toast.LENGTH_SHORT).show();
          tasks.add(j.toString());
        }
      }
    }
   else {
      //Firebase firebase = Firebase.getInstance();
      //jobList = firebase.getAllJobs();
      for (Job j: jobList) {
        tasks.add(j.toString());
      }
    }
//    if(employer != null) {
//      ArrayList<Job> jobList = employer.getMyJobs();
//      if(job != null) jobList.add(job);
//      if(employer.getMyJobs().isEmpty()) tasks.add("NO JOBS");
//      for(Job job:jobList){
//        tasks.add(job.toString());
//      }
//    }
    this.storeTasksAndLocation2SharedPrefs(tasks);

    RecyclerView recyclerView = findViewById(R.id.employeeJobs);
    LinearLayoutManager loManager = new LinearLayoutManager(this);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
            loManager.getOrientation());
    recyclerView.setLayoutManager(loManager);
    recyclerView.addItemDecoration(dividerItemDecoration);
    this.adapter = new RecyclerAdapter(this, tasks);
    this.adapter.setItemClickListener(this);
    recyclerView.setAdapter(adapter);
  }

  protected void showDetails(String selectedTask) {
    Intent intent = new Intent(this, JobDetailsActivity.class);
    Job selectJob = new Job();
    for(Job j : employee.getAllJobs()){
      if(j.getJobName().equals(selectedTask.split(",")[0])){
          selectJob = j;
      }
    }
    intent.putExtra(JobPostActivity.USERNAME,employee.getUsername());
    if(selectJob.getJobName()!=null) {
      intent.putExtra(JOB_NAME, selectJob.getJobName());
      intent.putExtra(JOB_DATE_POSTED, selectJob.getDatePosted());
      intent.putExtra(JOB_DURATION, selectJob.getDuration());
      intent.putExtra(JOB_WAGE, selectJob.getWage());
      intent.putExtra(JOB_EMPLOYER, selectJob.getUser());
      intent.putExtra(JOB_TAG, selectJob.getTag());
    }
    startActivity(intent);
  }

 // protected void showOnMap(String selectedTask) {
 //   Intent intent = new Intent(this, GoogleMapsActivity.class);
 //   intent.putExtra("taskLocation", "-44,63");
 //   startActivity(intent);
 // }

  @Override
  public void onItemClick(View view, int position) {
    //Toast.makeText(this, this.adapter.getItem(position), Toast.LENGTH_LONG).show();
    showDetails(this.adapter.getItem(position));
    //showOnMap(this.adapter.getItem(position));
  }



}
