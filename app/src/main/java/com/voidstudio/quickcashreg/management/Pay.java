package com.voidstudio.quickcashreg.management;

import users.Employee;
import users.Employer;
import users.User;

public class Pay implements IJobActions{
  @Override
  public JobContract execute(JobContract jc) {
    jc.setIsPaid(true);
    pay(jc);
    return jc;
  }
  private void pay(JobContract contract){
    String employerName = contract.getEmployerName();
    String employeeName = contract.getEmployeeName();
    String amountOwed = contract.getWage();
    Employer employer = (Employer)User.getUser(employerName);
    Employee employee = (Employee)User.getUser(employeeName);
    assert employer != null;
    assert employee != null;//Sonar lint told me to do it
    employer.setBalance(Double.parseDouble(amountOwed)*-1);
    employee.setBalance(Double.parseDouble(amountOwed));
  }



}
