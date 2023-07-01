package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.location.Location;

import com.voidstudio.quickcashreg.EmployeeRecommendation.EmployeeRecommendation;
import com.voidstudio.quickcashreg.Location.JobLocation;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import users.Employee;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmployeeRecommendationUnitTest {

    static Location location1;
    static Location location2;
    static Firebase firebase;
    static Employee employee;
    static Job job;
    static JobLocation jobLocation;



    @BeforeClass
    public static void setup() {
        location1 = Mockito.mock(Location.class);
        location2 = Mockito.mock(Location.class);
        firebase = Mockito.mock(Firebase.class);
        employee = Mockito.mock(Employee.class);
        job = Mockito.mock(Job.class);
        jobLocation = Mockito.mock(JobLocation.class);
        Mockito.when(location1.getLatitude()).thenReturn(0.0);
        Mockito.when(location1.getLongitude()).thenReturn(0.0);
        Mockito.when(location2.getLatitude()).thenReturn(0.0);
        Mockito.when(location2.getLongitude()).thenReturn(0.0);
        Mockito.when(location2.distanceTo(location1)).thenReturn((float)30.0);

    }

    @AfterClass
    public static void tearDown() { System.gc();
    }

    // test if the employee is valid for recommendation
    @Test
    public void getRecommendationValidEmployee(){
        double maxDistance = 50;
        double[] mockCoords = {0.0,0.0};
        //Employee employee1 =new Employee("steven", "steven@dal.ca", 20, 20, location1);
        //Job pilot = new Job("pilot", "30", "Tag 1", "Boss");
        Mockito.when(firebase.setJobCoordinates("pilot",location2.getLatitude(),location2.getLongitude())).thenReturn(null);
        //pilot.setLocation(location2);
        Mockito.when(jobLocation.getLatLong()).thenReturn(mockCoords);
        Mockito.when(employee.getLatLong()).thenReturn(mockCoords);
        Mockito.when(firebase.setJobCoordinates("pilot",location2.getLatitude(),location2.getLongitude())).thenReturn(null);
        //pilot.setLocation(location2);
        Mockito.when(job.getLocation()).thenReturn(location2);
        Mockito.when(job.getWage()).thenReturn("60");
        Mockito.when(employee.getMinimumSalaryAccepted()).thenReturn(0.0);
        Mockito.when(employee.getOrderFinished()).thenReturn(100);
        assertTrue(EmployeeRecommendation.isValidEmployee(job, employee ,maxDistance));

    }

    //test if the employee is not valid for recommendation
    //because salary of the job and the minimum salary accepted of the employee are the same (30/hour)
    @Test
    public void getRecommendationInvalidEmployee() {
        double maxDistance = 50;
        double[] mockCoords = {0.0, 0.0};
        //Employee employee2 = new Employee("callum", "cal@dal.ca", 30, 30, location1, firebase);
        Job pilot = new Job("pilot", "30", "Tag 1", "Boss");
        Mockito.when(jobLocation.getLatLong()).thenReturn(mockCoords);
        Mockito.when(firebase.setJobCoordinates("pilot", location2.getLatitude(),location2.getLongitude())).thenReturn(null);
        //pilot.setLocation(location2);
        Mockito.when(job.getLocation()).thenReturn(location2);
        Mockito.when(job.getWage()).thenReturn("0.12");
        Mockito.when(employee.getOrderFinished()).thenReturn(0);
        assertFalse(EmployeeRecommendation.isValidEmployee(job, employee, maxDistance));
    }

    }
