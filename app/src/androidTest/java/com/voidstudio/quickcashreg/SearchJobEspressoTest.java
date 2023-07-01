package com.voidstudio.quickcashreg;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.voidstudio.quickcashreg.SearchJob.JobPostingActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class SearchJobEspressoTest {
    @Rule
    public ActivityScenarioRule<JobPostingActivity> myRule = new ActivityScenarioRule<JobPostingActivity>(JobPostingActivity.class);

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
        assertEquals("com.voidstudio.quickcashreg", appContext.getPackageName());
    }

    // test if job page is visible (including job list and search bar)
    // before searching (which means all jobs are visible)
    @Test
    public void jobPostingIsVisible() {
        onView(withId(R.id.jobListView)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
    }

    // check if job list will appear if choosing tag
    @Test
    public void searchWithTagFilter(){
        onView(withId(R.id.jobTagList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Tag1"))).perform(click());
        onView(allOf(withId(R.id.job_posting), withText("Tag1"), isDisplayed()));
    }

    //check if job list will appear if choosing filters
    @Test
    public void searchWithOtherFilters(){
        onView(withId(R.id.distanceList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("5 km"))).perform(click());
        onView(withId(R.id.durationList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("1 hour"))).perform(click());
        onView(withId(R.id.dayPostedList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Last 1 day"))).perform(click());
        onView(allOf(withId(R.id.job_posting), withText("5 km"), withText("1 hour"), withText("Last 1 day"), isDisplayed()));
    }

    //test if show the correct list for input
    @Test
    public void correctList(){
        onView(withId(R.id.search_bar)).perform(typeText("pirate"));
        onView(allOf(withId(R.id.job_posting), withText("pirate"), isDisplayed()));
    }
}
