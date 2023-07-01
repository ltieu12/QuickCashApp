package users;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;

public class Employer extends User {
  private static Firebase firebase;
  private static Employer employer;
  public ArrayList<Job> myJobs;
  public ArrayList<Employee> observerList;
  private double balance;

  protected static Employee employee;
  private static String username;
  public Employer(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
    firebase = Firebase.getInstance();
    observerList = new ArrayList<>();
    myJobs = firebase.getJobsFromUser(username);
    balance = 2000;
  }

  protected Task<Void> search(){
    return null;
  }

  protected boolean validate(){
    return false;
  }


  public void setJob(String jobName, String jobWage, String jobDuration, String jobTag){
    Job job = new Job(jobName, jobWage, jobDuration, jobTag, username);
    if(locate.getMyLocation()!=null){
      double latitude = locate.getLatLong()[0];
      double longitude = locate.getLatLong()[1];
      firebase.setJobCoordinates(jobName,latitude,longitude);
    }
    myJobs.add(job);
    firebase.addJob(job);
  }
  public static Employer getInstance(String u){
    if(employer == null){
      firebase = Firebase.getInstance();
      employer = new Employer(u, firebase.getEmailAddress(u), firebase.getPassword(u));
    }
    return employer;
  }

  public ArrayList<Job> getMyJobs(){
    return myJobs;
  }
  @Override
  public void setOrderFinished(int orderFinished){
    Log.d("N/A", UserConstants.NOT_APPLICABLE);
  }
  @Override
  public int getOrderFinished(){
    Log.d("N/A", UserConstants.NOT_APPLICABLE);
    return 0;
  }
  @Override
  public void setMinimumSalaryAccepted(double minimumSalaryAccepted){
    Log.d("N/A", UserConstants.NOT_APPLICABLE);
  }
  @Override
  public double getMinimumSalaryAccepted(){
    Log.d("N/A", UserConstants.NOT_APPLICABLE);
    return 0.0;
  }

  public double getBalance() {
    return this.balance;
  }
  public double makePayment(double amount) {
    balance = balance - amount;
    if (balance < 0) {
      balance = 0;
    }
    return balance;
  }

}


