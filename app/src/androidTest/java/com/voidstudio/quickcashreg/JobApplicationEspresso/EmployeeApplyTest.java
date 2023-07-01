package com.voidstudio.quickcashreg.JobApplicationEspresso;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.voidstudio.quickcashreg.jobpost.JobDetailsActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeApplyTest {

  public ActivityScenarioRule<JobDetailsActivity> actvity =
          new ActivityScenarioRule<JobDetailsActivity>(JobDetailsActivity.class);

  @BeforeClass
  public static void setup(){
    Intents.init();
  }

  @AfterClass
  public static void tearDown(){
    System.gc();
  }

  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assertEquals("com.voidstudio.quickcashreg.jobpost", appContext.getPackageName());
  }





}
