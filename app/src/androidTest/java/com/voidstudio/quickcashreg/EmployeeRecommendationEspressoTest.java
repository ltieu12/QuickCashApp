package com.voidstudio.quickcashreg;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.voidstudio.quickcashreg.EmployeeRecommendation.EmployeeRecommendationActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class EmployeeRecommendationEspressoTest {
    @Rule
    public ActivityScenarioRule<EmployeeRecommendationActivity> myRule = new ActivityScenarioRule<EmployeeRecommendationActivity>(EmployeeRecommendationActivity.class);



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

    //check if seek bar is visible for choosing max distance
    @Test
    public void seekbarIsVisible(){
        onView(withId(R.id.seekBarForDistance)).check(matches(isDisplayed()));
    }

    //check if recommendation list will display after progressing the seek bar
    @Test
    public void recommendationListIsVisible(){
        onView(withId(R.id.seekBarForDistance)).check(matches(isDisplayed()));
        onView(withId(R.id.seekBarForDistance)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER_RIGHT, Press.FINGER));
        onView(withId(R.id.recommendationEmployeeList)).check(matches(isDisplayed()));
    }



}
