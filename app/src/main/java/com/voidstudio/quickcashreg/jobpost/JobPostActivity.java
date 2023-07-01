package com.voidstudio.quickcashreg.jobpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.EmployeeRecommendation.EmployeeRecommendationActivity;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.TextReader;
import com.voidstudio.quickcashreg.firebase.Firebase;

import users.Employer;
import users.User;

public class JobPostActivity extends AppCompatActivity implements View.OnClickListener {

  public static final String JOB_TAG_1 = "Tag1";
  public static final String JOB_TAG_2= "Tag2";
  public static final String JOB_TAG_3 = "Tag3";
  public static final String JOB_TAG_4 = "Tag4";
  public static final String JOB_TAG_5 = "Tag5";



  private Spinner jobTags;
  private String tag;
  private String job;
  private String wage;
  private String username;
  private String password;
  private String email;
  public static Employer employer;
  public static final String USERNAME = "Username";
  public static final String PASSWORD = "Password";
  private SharedPreferences sp;
  private SharedPreferences jobPost;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_post);
    Firebase firebase = Firebase.getInstance();
    Intent thisIntent = getIntent();
    sp = getSharedPreferences("login", MODE_PRIVATE);
    username = sp.getString(USERNAME,"");
    employer = (Employer) User.getUser(username);
    Button postButton = findViewById(R.id.postJobButton);
    postButton.setOnClickListener(JobPostActivity.this);
    Button myJobsButton = findViewById(R.id.myJobsButton);
    myJobsButton.setOnClickListener(JobPostActivity.this);

    setUpJobTagSpinner();
    jobTagsSpinnerListener();
  }

  private void jobTagsSpinnerListener() {
    jobTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
        tag = adapterView.getItemAtPosition(pos).toString();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  private void setUpJobTagSpinner() {
    jobTags = findViewById(R.id.jobTagsSpinner);
    String[] roles = new String[]{JOB_TAG_1,JOB_TAG_2,JOB_TAG_3,JOB_TAG_4,JOB_TAG_5};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
    jobTags.setAdapter(adapter);
  }

  private String getJobTitle(){
    EditText jobTitle = findViewById(R.id.jobTitle);
    TextReader tr = new TextReader();
    return tr.getFromEditText(jobTitle);
  }

  private String getWage(){
    EditText wage = findViewById(R.id.wageEdit);
    TextReader tr = new TextReader();
    return tr.getFromEditText(wage);
  }


  private String getDuration(){
    EditText wage = findViewById(R.id.durationEdit);
    TextReader tr = new TextReader();
    return tr.getFromEditText(wage);
  }


  private void postJob(String jobName, String jobWage, String jobDuration, String jobTag){
    if(employer.locate == null) employer.startLocating(this);
    employer.setJob(jobName,jobWage,jobDuration,jobTag);
  }

  private void saveJobPostState() {
    jobPost = getSharedPreferences("jobPost", MODE_PRIVATE);
    SharedPreferences.Editor edit = jobPost.edit();
    edit.putBoolean("newJobAlert", true);
    edit.putBoolean("newJobSeen", false);
    edit.commit();
  }


  @Override
  public void onClick(View view){
    if(view.getId() == R.id.postJobButton) {
      postJob(getJobTitle(),getWage(), getDuration(),tag);
      saveJobPostState();
      Toast.makeText(JobPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();

      Intent postedSwitch = new Intent(JobPostActivity.this, EmployeeRecommendationActivity.class);
      //TODO: User should get in taxi here
      postedSwitch.putExtra("Title", getJobTitle());
      postedSwitch.putExtra("Wage", getWage());
      postedSwitch.putExtra("Duration", getDuration());
      postedSwitch.putExtra("Tag", tag);
      postedSwitch.putExtra("Name", USERNAME);

//      postJob(getJobTitle(),getWage(),tag);
      startActivity(postedSwitch);
    }
    if(view.getId() == R.id.myJobsButton){
      Intent myJobsSwitch = new Intent(JobPostActivity.this, EmployerJobBoardActivity.class);
      startActivity(myJobsSwitch);
    }

  }


}

