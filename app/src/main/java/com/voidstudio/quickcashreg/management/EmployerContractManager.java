package com.voidstudio.quickcashreg.management;

import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;

import users.Employee;
import users.Employer;

public class EmployerContractManager implements IContractManager{



  private Employer employer;

  private ArrayList<String> paymentList;

  static EmployerContractManager m;

  public EmployerContractManager(Employer e){
    this.employer = e;
    createPaymentList();
  }

  public static EmployerContractManager getInstance(Employer e){
    if(m==null){
      m = new EmployerContractManager(e);
    }
    return m;
  }


  @Override
  public void acceptContract(Job j) {
    //TODO: Next Iteration, the employer will be able to accept job contracts.
  }

  @Override
  public void setContractStatus(JobContract jc, String status) {
    if(status.equals(ManagementConstants.PAY)){
      IJobActions action = new Pay();
      JobContract paidContract = action.execute(jc);
      completedContracts.remove(paidContract);
    }
  }

  @Override
  public ArrayList<JobContract> getCompletedContracts(){
    return (ArrayList<JobContract>) completedContracts;
  }

  @Override
  public ArrayList<JobContract> getIncompletedContracts() {
    return (ArrayList<JobContract>) inProgressContracts;
  }

  private void createPaymentList(){
    if(paymentList != null){
      paymentList.clear();
    }else{
      paymentList = new ArrayList<>();
    }
    if(completedContracts.isEmpty()){
      completedContracts.add(new JobContract(new Job("Fake","100","Tag1","callum"), Employee.getInstance("workerbee")));
    }
    for(JobContract jc : completedContracts){
      paymentList.add(jc.getEmployeeName()+" " +jc.getWage());
    }
  }

  @Override
  public ArrayList<String> getPaymentList(){

    if(paymentList!= null) return paymentList;
    else {
      createPaymentList();
    }
    return paymentList;
  }
}
