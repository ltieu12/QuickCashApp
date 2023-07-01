package com.voidstudio.quickcashreg.jobpost;

import static com.voidstudio.quickcashreg.jobpost.JobPostActivity.USERNAME;
import static com.voidstudio.quickcashreg.management.ManagementConstants.COMPLETED;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.UserApplicationActivity;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.management.EmployeeContractManager;
import com.voidstudio.quickcashreg.management.IContractManager;
import com.voidstudio.quickcashreg.management.JobContract;

import java.util.ArrayList;

import users.Employee;
import users.User;

public class JobDetailsActivity extends AppCompatActivity implements View.OnClickListener {

  private Firebase firebase;
  private static final String JOB_NAME = "job name";
  private static final String JOB_DURATION = "job duration";
  private static final String JOB_DATE_POSTED = "job date posted";
  private static final String JOB_TAG = "job tag";
  private static final String JOB_EMPLOYER = "job employer";
  private static final String JOB_WAGE = "job wage";

  private  String extractedJobName;
  private  String extractedWage;
  private  String extractedTag;
  private  String extractedDuration;
  private  String extractedDatePosted;
  private  String extractedJobEmployer;
  private String username;
  private IContractManager manager;
  private User u;
  private Employee e;
  private Job job;

  //private static final User user;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_details);
    firebase = Firebase.getInstance();
    Intent sentItem = getIntent();
    Bundle bundle = getIntent().getExtras();
    if(bundle != null) {
      username = bundle.getString(USERNAME);
      extractedJobName = bundle.getString(JOB_NAME);
      extractedWage = bundle.getString(JOB_WAGE);
      extractedTag = bundle.getString(JOB_TAG);
      extractedDuration = bundle.getString(JOB_DURATION);
      extractedDatePosted = bundle.getString(JOB_DATE_POSTED);
      extractedJobEmployer = bundle.getString(JOB_EMPLOYER);
      job = new Job(extractedJobName,extractedWage,extractedTag,extractedJobEmployer);
    }
    if(username != null){
        e = Employee.getInstance(username);
    }

    TextView jobName = findViewById(R.id.job_name_textView);
    jobName.setText(extractedJobName);
    TextView jobWage = findViewById(R.id.jobWage_textView);
    jobWage.setText(extractedWage);
    TextView jobDuration = findViewById(R.id.jobDuration_textView);
    jobDuration.setText(extractedDuration);
    TextView jobEmployer = findViewById(R.id.jobEmployer_textView);
    jobEmployer.setText(extractedJobEmployer);
    TextView jobDatePosted = findViewById(R.id.jobDate_textView);
    jobDatePosted.setText(extractedDatePosted);
    TextView jobTag = findViewById(R.id.jobTag_textView);
    jobTag.setText(extractedTag);
    Button applyButton = findViewById(R.id.applyJob);
    applyButton.setOnClickListener(JobDetailsActivity.this);
    Button completeButton = findViewById(R.id.Complete);
    completeButton.setOnClickListener(JobDetailsActivity.this);



  }



  public void onClick(View view) {
    if(view.getId() == R.id.applyJob){

      //TODO: Implement the application process
      if(e != null){
        IContractManager employeeContractManager = EmployeeContractManager.getInstance(e);
        employeeContractManager.acceptContract(job);
        Toast.makeText(this, "The application is accepted", Toast.LENGTH_LONG).show();
      }
      else{
        Toast.makeText(this,"You cannot apply as this user type",Toast.LENGTH_LONG).show();
      }
      Intent employment = new Intent(JobDetailsActivity.this, UserApplicationActivity.class);
      startActivity(employment);
    }
    if(view.getId() == R.id.Complete){
      if(e != null){
        IContractManager employeeContractManager = EmployeeContractManager.getInstance(e);
        ArrayList<JobContract> incompletedContracts = employeeContractManager.getIncompletedContracts();
        for(JobContract jc : incompletedContracts){
          if(jc.getJobName().equals(job.getJobName())){
            employeeContractManager.setContractStatus(jc,COMPLETED);
          }
        }
        Toast.makeText(this, "The job is now completed", Toast.LENGTH_LONG).show();
      }
      else{
        Toast.makeText(this,"You cannot complete jobs as this user type",Toast.LENGTH_LONG).show();
      }
      Intent employment = new Intent(JobDetailsActivity.this, UserApplicationActivity.class);
      startActivity(employment);
    }
  }

}
