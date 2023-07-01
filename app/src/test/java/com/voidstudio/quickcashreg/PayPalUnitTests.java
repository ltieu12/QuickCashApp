package com.voidstudio.quickcashreg;

import static org.junit.Assert.*;
import android.location.Location;

import com.voidstudio.quickcashreg.Location.JobLocation;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import users.Employee;
import users.Employer;
import users.User;

public class PayPalUnitTests {
    static Location location1;
    static Location location2;
    static Firebase firebase;
    static Employee employee;
    static Employer employer;
    static Job job;
    static JobLocation jobLocation;
    static User u;

    public static final double SALARY1 = 10.50;
    public static final double SALARY2 = 21.25;
    public static final String USERNAME = "Rick";
    public static final String EMAIL = "rick@roller.gotcha";
    public static final String PASSWORD = "password1";
    public static final int ORDER_FINISHED_1 = 10;
    public static final int ORDER_FINISHED_2 = -5;
    public static final String PREFERENCE = "Short";

    public static final Map<String,String> employeeInfo = new HashMap<>();
    public static final Map<String,String> employerInfo = new HashMap<>();

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
        employee = new Employee(USERNAME,PASSWORD);
        employer = new Employer(USERNAME,EMAIL,PASSWORD);

        employee.setEmail(EMAIL);
        //employee.setLocation(location1);
    }

    @Test
    public void testEmployerBalance() {
        Employer employer = new Employer(USERNAME, EMAIL, PASSWORD);
        assertEquals(2000, employer.getBalance(), 0);
    }

    @Test
    public void testEmployerMakePayment() {
        Employer employer = new Employer(USERNAME, EMAIL, PASSWORD);
        employer.makePayment(1500);
        assertEquals(500, employer.getBalance(), 0D);
    }
}

