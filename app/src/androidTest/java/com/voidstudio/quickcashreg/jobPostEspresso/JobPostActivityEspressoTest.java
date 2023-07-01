package com.voidstudio.quickcashreg.jobPostEspresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.voidstudio.quickcashreg.InAppActivityEmployer;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.jobpost.EmployerJobBoardActivity;
import com.voidstudio.quickcashreg.jobpost.Job;
import com.voidstudio.quickcashreg.jobpost.JobPostActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import users.Employer;
import users.User;

public class JobPostActivityEspressoTest {

  private static final String JOB_NAME = "Job";

  private static final String JOB_WAGE = "Wage";

  private static final String JOB_TAG = "Tag";

  private static final String EMPLOYER = "Scumbag";
  static User employer;
  static Job job;

  @Rule
  public ActivityScenarioRule<JobPostActivity> jobPostActivityActivityScenarioRule =
          new ActivityScenarioRule<JobPostActivity>(JobPostActivity.class);

  @BeforeClass
  public static void setup(){
    employer = new Employer("Username","Email","Password");
    job = new Job(JOB_NAME,JOB_WAGE,JOB_TAG,EMPLOYER);
    Intents.init();
  }

  @AfterClass
  public static void tearDown(){
    System.gc();
  }

  @Test
  public void checkIfJobPostActivityIsVisible(){
    onView(withId(R.id.wageEdit)).check(matches(isDisplayed()));
  }

  @Test
  public void switchToJobBoardActivityTest(){
    InAppActivityEmployer.employer = (Employer)employer;
    ((Employer) employer).myJobs.add(job);

    onView(withId(R.id.jobTitle)).perform(typeText(JOB_NAME));
    onView(withId(R.id.wageEdit)).perform(typeText(JOB_WAGE));
    onView(withId(R.id.wageEdit)).perform(closeSoftKeyboard());
    onView(withId(R.id.myJobsButton)).perform(click());
    Espresso.onIdle();
    intended(hasComponent(EmployerJobBoardActivity.class.getName()));
  }







}
