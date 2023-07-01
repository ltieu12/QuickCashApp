package com.voidstudio.quickcashreg.management;

public class Complete implements IJobActions{

  @Override
  public JobContract execute(JobContract jc) {
    jc.setCompleted(true);
    jc.setInProgress(false);
    jc.setAvailable(false);
    return jc;
  }
}
