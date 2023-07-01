package com.voidstudio.quickcashreg.management;

public class Work implements IJobActions{



  @Override
  public JobContract execute(JobContract jc) {
    jc.setAvailable(false);
    jc.setInProgress(true);
    jc.setCompleted(false);
    return jc;
  }




}
