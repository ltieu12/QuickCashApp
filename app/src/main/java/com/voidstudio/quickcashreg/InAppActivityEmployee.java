package com.voidstudio.quickcashreg;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.voidstudio.quickcashreg.SearchJob.JobPostingActivity;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.EmployeeJobBoardActivity;
import com.voidstudio.quickcashreg.jobpost.SavePreferenceActivity;
import com.voidstudio.quickcashreg.management.EmployeeContractManager;

import users.Employee;
import users.UserConstants;

public class InAppActivityEmployee extends AppCompatActivity implements View.OnClickListener {
  private static String username;
  private static String password;
  private static String email;
  private static String userType = UserConstants.EMPLOYEE;

  public static final String USERNAME = "Username";
  public static final String PASSWORD = "Password";
  private SharedPreferences sp;

  private static Firebase firebase;

  String channelID = "Notification";
  int notificationID = 0;
  //private Employee employee;
  public static Employee employee;
  public static EmployeeContractManager manager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.in_app_activity_employee);
    firebase= Firebase.getInstance();
    // switch to this activity when log in successfully
    Intent thisIntent = getIntent();
    String welcomeMessage = thisIntent.getStringExtra(LogInActivity.WELCOME);
    TextView message = findViewById(R.id.Employee);
    message.setText(welcomeMessage);

    sp = getSharedPreferences("login", MODE_PRIVATE);
    username = sp.getString(USERNAME,"");
    password = sp.getString(PASSWORD,"");
    email = firebase.getEmailAddress(username);


    //TODO: REFACTOR SO THAT SHARED PREF CAN STORE USERTYPE
    employee = new Employee(username,email,userType,password);
    if(employee.startLocating(this)) {
      employee.startLocating(this);
      employee.setLocation(employee.locate.getMyLocation());
      firebase.setUserCoordinates(username, employee.locate.getMyLocation());
      Toast.makeText(this,
              employee.getLatLong()[0] + "," + employee.getLatLong()[1], Toast.LENGTH_LONG).show();
    }
    else{
      Toast.makeText(this,"Allow location service", Toast.LENGTH_LONG).show();
    }

    Button logOutButton = findViewById(R.id.employeeLogOut);
    logOutButton.setOnClickListener(InAppActivityEmployee.this);

    Button userProfileButton = findViewById(R.id.user_profile);
    userProfileButton.setOnClickListener(InAppActivityEmployee.this);

    Button userApplicationButton = findViewById(R.id.user_application);
    userApplicationButton.setOnClickListener(InAppActivityEmployee.this);

    Button jobPostingButton = findViewById(R.id.job_posting);
    jobPostingButton.setOnClickListener(InAppActivityEmployee.this);

    Button jobBoard = findViewById(R.id.employeeJobBoard);
    jobBoard.setOnClickListener(InAppActivityEmployee.this);

    Button savePreference = findViewById(R.id.setPreference);
    savePreference.setOnClickListener(InAppActivityEmployee.this);



    // Check if the employee has seen the new job posting or not, if not, pop up the notification
    SharedPreferences jobPostNoti = getSharedPreferences("jobPost", MODE_PRIVATE);
    employee.newJobAlert = jobPostNoti.getBoolean("newJobAlert", true);
    employee.newJobSeen = jobPostNoti.getBoolean("newJobSeen", true);

    SharedPreferences.Editor editor = jobPostNoti.edit();
    if (employee.newJobAlert && !employee.newJobSeen) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        jobNotification();
        editor.putBoolean("newJobSeen", true).commit();
      }
    }
    firebase = Firebase.getInstance();
  }

  //This method could be in its own class, many different activities will need it
  private void resetLogInStatus() {
    SharedPreferences sharedPrefs = getSharedPreferences(LogInActivity.PREFERENCES, Context.MODE_PRIVATE); //
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putBoolean(LogInActivity.ISLOGGED, false);
    editor.commit();

    sharedPrefs = getSharedPreferences("jobPost", MODE_PRIVATE);
    sharedPrefs.edit().clear().commit();
  }

  /**
   * Reference from Android cookbook from tutorial
   */
  @RequiresApi(api = Build.VERSION_CODES.O)
  protected void jobNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID);
    builder.setSmallIcon(R.drawable.information);
    builder.setContentTitle("New Job Posting Alert!");
    builder.setContentText("New Job is Posted!!");
    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

    Intent jobBoardIntent = new Intent(this, EmployeeJobBoardActivity.class);
    PendingIntent pIntent = PendingIntent.getActivity(this, 0, jobBoardIntent, PendingIntent.FLAG_IMMUTABLE);
    builder.setContentIntent(pIntent);

    NotificationManager manager = getSystemService(NotificationManager.class);
    manager.createNotificationChannel(new NotificationChannel(channelID, "custom", NotificationManager.IMPORTANCE_HIGH));
    manager.notify(notificationID, builder.build());

  }

  @Override
  public void onClick(View view){
    if(view.getId() == R.id.employeeLogOut){
      resetLogInStatus();
      Intent logOutIntent = new Intent(InAppActivityEmployee.this, LogInActivity.class);
      startActivity(logOutIntent);
    }

    if(view.getId() == R.id.employeeJobBoard){
      Intent jobBoardIntent = new Intent(InAppActivityEmployee.this, EmployeeJobBoardActivity.class);
      jobBoardIntent.putExtra("USERNAME", username);
      jobBoardIntent.putExtra("PASSWORD", password);
      jobBoardIntent.putExtra("EMAIL",email);
      startActivity(jobBoardIntent);
    }

    if (view.getId() == R.id.setPreference) {
      Intent savePreference = new Intent(InAppActivityEmployee.this, SavePreferenceActivity.class);
      startActivity(savePreference);
    }
    if(view.getId() == R.id.user_profile){
      Intent userProfileIntent = new Intent(InAppActivityEmployee.this, UserProfileActivity.class);
      userProfileIntent.putExtra(USERNAME, username);
      userProfileIntent.putExtra(PASSWORD, password);
//      userProfileIntent.putExtra("EMAIL",email);
      startActivity(userProfileIntent);
    }
    if(view.getId() == R.id.job_posting){
      Intent jobPostingIntent = new Intent(InAppActivityEmployee.this, JobPostingActivity.class);
      startActivity(jobPostingIntent);
    }
    if(view.getId() == R.id.user_application){
      Intent userApplicationIntent = new Intent(InAppActivityEmployee.this, UserApplicationActivity.class);
      startActivity(userApplicationIntent);
    }

  }

}
