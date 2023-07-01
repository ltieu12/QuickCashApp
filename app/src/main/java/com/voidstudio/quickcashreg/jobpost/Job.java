package com.voidstudio.quickcashreg.jobpost;

import android.location.Location;

import com.voidstudio.quickcashreg.Location.ILocation;
import com.voidstudio.quickcashreg.Location.JobLocation;

import java.util.Date;

public class Job {
  private String jobName;
  private String wage;
  private String tag;
  private String user;
  private String duration;
  private String datePosted;
  private Location location;
  private String hardLocation;
  public ILocation jobLocation;



  public Job(){

  }

  public Job(String jobName, String wage, String tag, String user){
    this.jobName = jobName;
    this.wage = wage;
    this.tag = tag;
    this.user = user;
    this.datePosted = new Date().toString();
    jobLocation = new JobLocation(jobName);
  }

  public Job(String jobName, String wage, String duration, String tag, String user){
    this.jobName = jobName;
    this.wage = wage;
    this.duration = duration;
    this.tag = tag;
    this.datePosted = new Date().toString();
    this.user = user;
    jobLocation = new JobLocation(jobName);
  }

  public Job(String jobName, String wage, String duration, String tag, String user, String datePosted) {
    this.jobName = jobName;
    this.wage = wage;
    this.duration = duration;
    this.tag = tag;
    this.datePosted = datePosted;
    this.user = user;
    jobLocation = new JobLocation(jobName);
  }


  public void setLocation(Location location) {
    jobLocation.setLocation(location);
  }

  public Location getLocation() {
    if(jobLocation != null) {
      return jobLocation.getMyLocation();
    }
    else{
      return null;
    }
  }



  public String getUser() {
    return user;
  }

  public void setUser(String username){
    this.user = username;
  }

  public String getTag() {
    return tag;
  }

  public void setWage(String wage){
    this.wage = wage;
  }

  public String getWage() {
    return wage;
  }



  public String getDuration() {
    return duration;
  }

  public String getDatePosted() {
    return datePosted;
  }

  public String getJobName() {
    return jobName;
  }

  @Override
  public String toString(){
    return jobName+",Wage:"+wage+" Duration:"+ duration + " Tag:" + tag+"  Posted by:" + user + " on " + datePosted;
  }
}
