package com.voidstudio.quickcashreg.management;

import com.voidstudio.quickcashreg.jobpost.Job;

import users.Employee;

/**
 * Job Contract requests Contract Manager
 */
public class JobContract {
  private Job job;
  private String jobName;
  private String employeeName;
  private String employerName;
  private String wage;

  private boolean inProgress;
  private boolean completed;
  private boolean available;
  private boolean isPaid;




  /**
   * Contructor to be used by manager
   * @param job
   * @param e
   */
  public JobContract(Job job, Employee e){
    this.job = job;
   this.jobName = job.getJobName();
   this.employerName = job.getUser();
   this.employeeName = e.getUsername();
   this.wage = job.getWage();
   this.available = false;
   this.inProgress = true;
   this.completed = false;
   this.isPaid = false;
  }


  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getEmployerName() {
    return employerName;
  }

  public void setEmployerName(String employerName) {
    this.employerName = employerName;
  }

  public String getWage() {
    return wage;
  }

  public void setWage(String wage) {
    this.wage = wage;
  }

  public boolean isInProgress() {
    return inProgress;
  }

  public void setInProgress(boolean inProgress) {
    this.inProgress = inProgress;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public boolean getIsPaid(){
    return isPaid;
  }
  public void setIsPaid(boolean paid){
    this.isPaid = paid;
  }
  public Job getJob(){return this.job;}
}
