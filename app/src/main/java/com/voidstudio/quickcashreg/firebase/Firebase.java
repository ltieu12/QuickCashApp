package com.voidstudio.quickcashreg.firebase;

import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.CONTRACTS;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.EMAIL;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.JOB_CONTRACTS_COMPLETED;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.JOB_CONTRACTS_IN_PROGRESS;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.JOB_LOCATION;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.LATITUDE;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.LOCATION;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.LONGITUDE;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.PASSWORD;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.TYPE;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.USERNAME;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.USERS;

import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.voidstudio.quickcashreg.jobpost.Job;
import com.voidstudio.quickcashreg.management.JobContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import users.Employee;
import users.UserConstants;

public class Firebase {
  static final String FIREBASE_URL =
          "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
  protected static FirebaseDatabase firebaseDB;
  protected static Firebase firebase;
  protected static DatabaseReference firebaseDBReference;
  protected final String JOBS = "jobs";

  protected static DatabaseReference users_ref;
  protected static DatabaseReference jobs_ref;

  public ArrayList<Employee> recommendList = new ArrayList<>();

  public String firebaseString;
  public String pass;
  public double[] coordinates;

  private boolean exists = false;
  public boolean employee = false;
  public Firebase() {

    firebaseDB = FirebaseDatabase.getInstance();
    firebaseDB.setPersistenceEnabled(false);
    firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);

    jobs_ref = firebaseDB.getReference("jobs");
    users_ref = firebaseDB.getReference("users");

  }

  /**
   * Singleton design pattern. Ensures only one instance of firebase across project
   * @return
   */
  public static Firebase getInstance() {
    if(firebase == null) {
      firebase = new Firebase();

    }
    return firebase;
  }

  public ArrayList<Employee> getRecommendList() {
    return recommendList;
  }

  public void setRecommendList(ArrayList<Employee> recommendList) {
    this.recommendList = recommendList;
  }

  public void initializeDatabase(){
    firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);
    firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
  }

  public String getEmailAddress(String username) {
    String email = getValueFromUser(username, EMAIL);

    return email;
  }

  public String getUserType(String username){
    return getValueFromUser(username, TYPE);
  }

  public String getPassword(String username){
    return getValueFromUser(username, PASSWORD);
  }



  /**
   * Save email address into database
   * Note: We use username as an unique ID for a user
   * @param email entered email
   * @param userName user associated with the entered email
   */
  public Task<Void> setEmailAddress(String email, String userName) {
    firebaseDBReference.child(USERS).child(userName).child(EMAIL).setValue(email);
    return null;
  }


  /**
   * Save user type into database
   * Note: We use username as an unique ID for a user
   * @param userType type of user(employee or employer) to save to DB
   * @param userName associated user
   */
  public Task<Void> setUserType(String userType, String userName) {
    firebaseDBReference.child(USERS).child(userName).child(TYPE).setValue(userType);
    return null;
  }


  /**
   * Save password into database
   * Note: We use username as an unique ID for a user
   * @param password password to save to DB
   * @param userName associated user
   */
  public Task<Void> setPassword(String password, String userName) {
    firebaseDBReference.child(USERS).child(userName).child(PASSWORD).setValue(password);
    return null;
  }
  public Task<Void> setJobCoordinates(String jobName, double latitude, double longitude){
    firebaseDBReference.child(JOB_LOCATION).child(jobName).child(LOCATION).
            child(LONGITUDE).setValue(latitude);
    firebaseDBReference.child(JOB_LOCATION).child(jobName).child(LOCATION).
            child(LATITUDE).setValue(longitude);
    return null;
  }

  public double[] getJobCoordinates(String jobName){
    Query queer = firebaseDBReference.child(JOB_LOCATION).child(jobName).child(LOCATION);
    queer.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.child(LATITUDE).getValue()!=null&&snapshot.child(LONGITUDE).getValue()!= null) {
          double longitude = (double)snapshot.child(LATITUDE).getValue();
          double latitude = (double)snapshot.child(LONGITUDE).getValue();
          coordinates = new double[2];
          coordinates[0] = longitude;
          coordinates[1] = latitude;
        }

      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return coordinates;
  }

  public void setUserCoordinates(String username, Location location){
    double[] coords = {location.getLatitude(), location.getLongitude()};
    firebaseDBReference.child(USERS).child(username).child(LONGITUDE).setValue(coords[0]);
    firebaseDBReference.child(USERS).child(username).child(LATITUDE).setValue(coords[1]);
  }

  public double[] getUserCoordinates(String username){
    Query queer = firebaseDBReference.child(USERS).child(username).child(LOCATION);
    queer.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.child(LATITUDE).getValue()!=null&&snapshot.child(LONGITUDE).getValue()!= null) {
           double longitude = (double)snapshot.child(LATITUDE).getValue();
           double latitude = (double)snapshot.child(LONGITUDE).getValue();
           coordinates = new double[2];
           coordinates[0] = longitude;
           coordinates[1] = latitude;
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return coordinates;
  }




  private void existingUserHelper(String username){
    final Query users = firebaseDBReference.child(USERS);
    users.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.hasChild(username)){
          exists = true;
        }
        else{
          exists = false;
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
    });
  }



  public boolean existingUser(String username){
    existingUserHelper(username);

    return exists;
  }

  private void getValueHelper(String username, String value){
    Query user = firebaseDB.getReference().child(USERS).child(username).child(value);
    user.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()) {
          Object sc = snapshot.getValue();
          if(sc != null) {
            firebaseString = sc.toString();
            if(firebaseString.equals(UserConstants.EMPLOYEE)) employee = true;
            pass = firebaseString;
          }
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }




  /**
   * Gets the value as string
   * @param username the user
   * @param value the corresponding value
   * @return the value stored in firebase
   */
  private String getValueFromUser(String username, String value){
    getValueHelper(username,value);
    if(firebaseString != null){
      return firebaseString;
    }
    else return "";
  }

  //Both of these should be in log in(THIS IS FOR DEBUGGING)
  public boolean checkIfPasswordMatches(String username, String password){
    return getPassword(username).equals(password);
  }

  public void addUser(String username, String password, String email, String type, String minimumSalary){
    Map<String, Object> map = new HashMap<>();
    map.put(USERNAME, username);
    map.put(PASSWORD, password);
    map.put("minimumSalary", minimumSalary);
    map.put("orderFinished", "0");
    map.put(EMAIL, email);
    map.put(TYPE, type);
    firebaseDBReference.child(USERS).child(username).updateChildren(map);
  }

  public void addJob(Job job){
    Map<String, Object> map = new HashMap<>();
    map.put(job.getJobName(), job);
    firebaseDBReference.child(JOBS).updateChildren(map);
    firebaseDBReference.child(USERS).child(job.getUser()).child(JOBS).updateChildren(map);
  }

  public ArrayList<Job> getJobsFromUser(String username){
    ArrayList<Job> arrJob = new ArrayList<>();
    if(username == null){
      return arrJob;
    }
    Query query = firebaseDBReference.child(USERS).child(username).child(JOBS);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        //final String jobName;
        //final String jobWage;
        //final String jobTag;
        for(DataSnapshot sc : snapshot.getChildren()){
          Job job;
          if(sc.exists() && sc.getChildrenCount()>0) {
            job = sc.getValue(Job.class);
            if(job!=null){
              arrJob.add(job);
            }
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return arrJob;
  }

  public ArrayList<Job> getAllJobs(){
    ArrayList<Job> arrJob = new ArrayList<>();
    Query query = firebaseDBReference.child(JOBS);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot sc : snapshot.getChildren()){
          Job job;
          if(sc.exists() && sc.getChildrenCount()>0) {
            job = sc.getValue(Job.class);
            if(job!=null){
              arrJob.add(job);
            }
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return arrJob;
  }
 
  public void listenerForUser_Ref() {
    users_ref.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        recommendList = new ArrayList<>();
        for (DataSnapshot ds: snapshot.getChildren()) {
          String type = ds.child(TYPE).getValue(String.class);
          if (type == null || !type.equals(UserConstants.EMPLOYEE)) {
            continue;
          }
          String name = ds.child(USERNAME).getValue(String.class);
          String email = ds.child(EMAIL).getValue(String.class);
          String miniSalary = ds.child("minimumSalary").getValue(String.class);
          String orderFinished = ds.child("orderFinished").getValue(String.class);
          String password = ds.child(PASSWORD).getValue(String.class);
          double latitude =0.0;
          double longitude=0.0;
          if(ds.hasChild(LATITUDE)&& ds.hasChild(LONGITUDE)) {
             latitude = ds.child(LATITUDE).getValue(Double.class);
             longitude = ds.child(LONGITUDE).getValue(Double.class);
          }
          if((latitude == 0.0 )|| longitude == 0.0){
            Employee e = new Employee(name,email,password);
            recommendList.add(e);
          }
          else {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLongitude(longitude);
            location.setLatitude(latitude);
            Employee e = new Employee(name, email, Integer.parseInt(orderFinished),
                    Double.parseDouble(miniSalary), location);
            e.setLocation(location);
            recommendList.add(e);
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
    });
  }

  public Map<String,String> getUserInfo(String username){
    Map<String, String> map = new HashMap<>();
    map.put("name", username);
    map.put("password", getPassword(username));
    map.put("email", getEmailAddress(username));
    map.put("type", getUserType(username));
    return map;
  }






}
