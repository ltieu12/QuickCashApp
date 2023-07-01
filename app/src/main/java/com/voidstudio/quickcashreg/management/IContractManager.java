package com.voidstudio.quickcashreg.management;

import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;
import java.util.List;

public interface IContractManager {

  List<JobContract> inProgressContracts = new ArrayList<>();
  List<JobContract> completedContracts = new ArrayList<>();


  void acceptContract(Job j);

  void setContractStatus(JobContract jc, String status);

  ArrayList<JobContract> getCompletedContracts();

  ArrayList<JobContract> getIncompletedContracts();

  ArrayList<String> getPaymentList();





}
