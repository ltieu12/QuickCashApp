package com.voidstudio.quickcashreg.userstest;

import static org.junit.Assert.assertTrue;

import android.location.Location;

import com.voidstudio.quickcashreg.Location.JobLocation;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import users.Employee;

public class EmployeeJUnitTests {


  static Location location1;
  static Location location2;
  static Firebase firebase;
  static Employee employee;
  static Job job;
  static JobLocation jobLocation;

  public static final double SALARY1 = 10.50;
  public static final double SALARY2 = 21.25;
  public static final String USERNAME = "Rick";
  public static final String EMAIL = "rick@roller.gotcha";
  public static final String PASSWORD = "password1";
  public static final int ORDER_FINISHED_1 = 10;
  public static final int ORDER_FINISHED_2 = -5;
  public static final String PREFERENCE = "Short";


  @BeforeClass
  public static void setup(){
    location1 = Mockito.mock(Location.class);
    location2 = Mockito.mock(Location.class);
    firebase = Mockito.mock(Firebase.class);
    //This way of mocking firebase getInstance works I adapted from
    //https://stackoverflow.com/questions/38914433/mocking-a-singleton-with-mockito
    try{
      Field instance = Firebase.class.getDeclaredField("firebase");
      instance.setAccessible(true);
      instance.set(instance,firebase);
    }catch(NoSuchFieldException | IllegalAccessException e){
      e.printStackTrace();
    }
    job = Mockito.mock(Job.class);
    jobLocation = Mockito.mock(JobLocation.class);
    Mockito.when(location1.getLatitude()).thenReturn(0.0);
    Mockito.when(location1.getLongitude()).thenReturn(0.0);
    Mockito.when(location2.getLatitude()).thenReturn(0.0);
    Mockito.when(location2.getLongitude()).thenReturn(0.0);
    Mockito.when(location2.distanceTo(location1)).thenReturn((float)30.0);
    employee = new Employee(USERNAME,EMAIL,PASSWORD);
    employee.setEmail(EMAIL);

    //employee.setLocation(location1);

  }

  @Test
  public void setOrderFinishedTest(){
    employee.setOrderFinished(ORDER_FINISHED_1);
    String orderFinished = Integer.toString(employee.getOrderFinished());
    assertTrue(orderFinished,employee.getOrderFinished() == ORDER_FINISHED_1);
  }

  @Test
  public void setPreferenceTest(){
    employee.setPreference(PREFERENCE);
    assertTrue(employee.getPreference().equals(PREFERENCE));
  }

  @Test
  public void testRecommendedInfoStringCorrect(){
    employee.setEmail(EMAIL);
    String res = employee.recommendInfo();
    assertTrue(res.equals("Name: " + USERNAME + " Email: " + EMAIL));
  }




}
