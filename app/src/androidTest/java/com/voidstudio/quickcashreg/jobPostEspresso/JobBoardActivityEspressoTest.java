package com.voidstudio.quickcashreg.jobPostEspresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.jobpost.EmployerJobBoardActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JobBoardActivityEspressoTest {

  ActivityScenarioRule<EmployerJobBoardActivity> employerJobBoardActivityActivityScenarioRule =
          new ActivityScenarioRule<EmployerJobBoardActivity>(EmployerJobBoardActivity.class);


  @BeforeClass
  public static void setup(){
    Intents.init();
  }

  @AfterClass
  public static void tearDown(){
    System.gc();
  }

  @Test
  public void jobBoardIsVisible(){
    onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
  }


}
