package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import users.Employee;

public class SavePreferenceJUnitTest {
    public static Employee mockEmployee;
    static final String PREF = "Tag 1";
    @BeforeClass
    public static void setup() {

        mockEmployee = Mockito.mock(Employee.class);
        

    }

    @AfterClass
    public static void tearDown() { System.gc();
    }

    @Test
    public void checkGetPreference() {
        Mockito.when(mockEmployee.getPreference()).thenReturn(PREF);
        assertTrue(mockEmployee.getPreference().equals(PREF));
    }
}
