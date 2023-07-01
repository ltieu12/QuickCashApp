package users;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;


public class Employee extends User {
    protected int orderFinished;
    protected double minimumSalaryAccepted;

        // For observing employer
    public boolean newJobAlert;
    public boolean newJobSeen;

    public static Firebase firebase;
    private static Employee employee;
    private String preference;
    private ArrayList<Job> allJob;

    private String username;
    private String password;
    private String email;

    public Employee(String username, String email, int orderFinished, double minimumSalaryAccepted, Location location){
        this.username = username;
        this.email = email;
        this.orderFinished = orderFinished;
        this.minimumSalaryAccepted = minimumSalaryAccepted;
        startLocating(location);
        setLocation(location);
        firebase = Firebase.getInstance();
    }


    public void setMinimumSalaryAccepted(double minimumSalaryAccepted) {
        this.minimumSalaryAccepted = minimumSalaryAccepted;
    }

    public void setOrderFinished(int orderFinished) {
        this.orderFinished = orderFinished;
    }

    public double getMinimumSalaryAccepted() {
        return minimumSalaryAccepted;
    }

    public int getOrderFinished() {
        return orderFinished;
    }

    protected boolean validate(){
        return false;

    }

    public static Employee getInstance(String username){
        firebase = Firebase.getInstance();
        if(employee == null){
            employee = new Employee(username, firebase.getEmailAddress(username),
                    firebase.getPassword(username));
        }
        return employee;
    }


    public Employee(String username, String email, String password){
        firebase = Firebase.getInstance();
        this.username = username;
        this.email = email;
        this.password = password;
        allJob = (ArrayList<Job>)firebase.getAllJobs();
    }

    public Employee(String username, String password) {
        firebase = Firebase.getInstance();
        this.username = username;
        this.password = password;
        allJob = (ArrayList<Job>)firebase.getAllJobs();
    }

    public Employee(String username, String email, String userType, String password){
        firebase = Firebase.getInstance();
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
        allJob = (ArrayList<Job>)firebase.getAllJobs();
    }

    protected Task<Void> search(){
        return null;
    }

    public void setJob(String jobName, String jobWage, String duration, String jobTag){
        // Employee cannot set a job but employer can.
        Log.d("N/A", "Operation not applicable to this user type");
    }
    @Override
    public void setEmail(String email){
        this.email = email;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getPreference() {
        return preference;
    }

    public ArrayList<Job> getAllJobs() {
        firebase = Firebase.getInstance();
        if(allJob == null){
            allJob = (ArrayList<Job>) firebase.getAllJobs();
        }
        return allJob;
    }

    @Override
    public String getUsername(){
        return this.username;
    }

    public String recommendInfo() {
        return "Name: " + this.username + " Email: " + this.email;
    }

    public Location getLocationFromDatabase(){
        double[] coords = firebase.getUserCoordinates(username);
        if(coords!= null) {
            Location location = new Location(" ");
            location.setLongitude(coords[0]);
            location.setLatitude(coords[1]);
            return location;
        }
        else return null;
    }


}


