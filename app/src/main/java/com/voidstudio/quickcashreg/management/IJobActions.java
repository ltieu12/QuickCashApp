package com.voidstudio.quickcashreg.management;

import com.voidstudio.quickcashreg.firebase.Firebase;

public interface IJobActions {

  Firebase firebase = Firebase.getInstance();
  public JobContract execute(JobContract contract);

}
