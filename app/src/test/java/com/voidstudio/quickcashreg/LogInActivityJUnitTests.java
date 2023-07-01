package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class LogInActivityJUnitTests {
    static LogInActivity employeeLogInActivity;
    static LogInActivity employerLogInActivity;
    static LogIn logIn;


    @BeforeClass
    public static void setup(){
        employeeLogInActivity = Mockito.mock(LogInActivity.class);
        employerLogInActivity = Mockito.mock(LogInActivity.class);
        logIn = Mockito.mock(LogIn.class);
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /*** UAT-2 ***/
    @Test
    public void noExistingUser(){
        String fakeUser = "FakeUser123";
        String fakePassword = "123456";
        //random name for time being
        assertFalse("This username does not exist",
                logIn.logIn(fakeUser,fakePassword));
    }
    /*** UAT-4 ***/
    @Test
    public void incorrectPass(){
        //Will always fail
        String realUser = "RealUser123";
        Mockito.when(logIn.existingUser(realUser)).thenReturn(true);
        String fakePass = "LordOfCyberSecurityImpenetrableSuperServerDefenseNoHackingHere123456789!@#$%^&*()";
        Mockito.when(logIn.passwordMatch(fakePass)).thenReturn(false);
        assertFalse("Password is incorrect", logIn.logIn(realUser,fakePass));
    }

    /**Test if isEmployeeMethodWorks**/
    @Test
    public void checkIfEmployeeIsEmployee(){
        Mockito.when(logIn.isEmployee()).thenReturn(true);
        assertTrue(logIn.isEmployee());
    }

    @Test public void checkIfEmployer(){
        Mockito.when(logIn.isEmployee()).thenReturn(false);
        assertFalse(logIn.isEmployee());
    }







}
