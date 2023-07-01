package com.voidstudio.quickcashreg;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.voidstudio.quickcashreg.Register.RegisterActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;



public class LogInActivityEspressoTests {
    @Rule
    public ActivityScenarioRule<LogInActivity> myRule = new ActivityScenarioRule<LogInActivity>
            (LogInActivity.class);
    @BeforeClass
    public static void setup(){
       Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
       SharedPreferences sharedPrefs = context.getSharedPreferences(LogInActivity.PREFERENCES, Context.MODE_PRIVATE); //
       SharedPreferences.Editor editor = sharedPrefs.edit();
       editor.putBoolean(LogInActivity.ISLOGGED, false);
       editor.commit();
       Intents.init();

    }


    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    /**
     * Method check the input type of password
     * Reference: https://stackoverflow.com/questions/48395282/how-to-get-input-type-of-edittext-in-espresso-testing
     * @return true if password is hidden; false otherwise
     */
    private Matcher<View> isPasswordHidden() {
        return new BoundedMatcher<View, EditText>(EditText.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Password is hidden");
            }

            @Override
            public boolean matchesSafely(EditText editText) {
                //returns true if password is hidden
                return editText.getTransformationMethod() instanceof PasswordTransformationMethod;
            }
        };
    }

    @Test
    /** AT-I **/
    public void isPasswordShowed() {
        Espresso.onView(withId(R.id.textPassword)).perform(typeText("password123"));
        Espresso.onView(withId(R.id.showHidePassword)).perform(click());
        Espresso.onView(withId(R.id.textPassword)).check(matches(withText("password123")));
        Espresso.onView(withId(R.id.showHidePassword)).check(matches(withText("Hide Password")));
    }

    @Test
    /** AT-II **/
    public void isPasswordHiddenAfterClickButtonTwice() {
        Espresso.onView(withId(R.id.textPassword)).perform(typeText("password123"));
        Espresso.onView(withId(R.id.showHidePassword)).perform(click());
        Espresso.onView(withId(R.id.showHidePassword)).perform(click());
        Espresso.onView(withId(R.id.textPassword)).check(matches(isPasswordHidden()));
    }

    @Test
    /** AT-III **/
    public void isPasswordHiddenBeforeToggle() {
        Espresso.onView(withId(R.id.textPassword)).perform(typeText("password123"));
        Espresso.onView(withId(R.id.textPassword)).check(matches(isPasswordHidden()));
    }


  /*** UAT-I (LogIn)***/
  @Test
  public void checkIfMovedToLogInPage() {
    Espresso.onView(withId(R.id.logInRegisterButton)).perform(click());
    Espresso.onView(withId(R.id.loginButton)).perform(click());
    intended(hasComponent(LogInActivity.class.getName()));
  }

  /** UAT-III (LogIN) **/
  @Test
  public void checkIfMovedToRegisterPage(){
    Espresso.onView(withId(R.id.logInRegisterButton)).perform(click());
    intended(hasComponent(RegisterActivity.class.getName()));
  }

  @Test
  public void checkIfCanLogIn(){
    Espresso.onView(withId(R.id.textPassword)).perform(click()).perform(typeText("123456"));
    Espresso.closeSoftKeyboard();
    Espresso.onView(withId(R.id.logInUserName)).perform(click()).perform((typeText("callum")));
    Espresso.closeSoftKeyboard();
    Espresso.onIdle();
    Espresso.onView(withId(R.id.continueButton)).perform(click());//Incorrect password
    Espresso.onIdle();
    Espresso.onView(withId(R.id.continueButton)).perform(click());//This user does not exist
    Espresso.onIdle();
    Espresso.onView(withId(R.id.continueButton)).perform(click());//Success
    Espresso.onIdle();
    intended(hasComponent(InAppActivityEmployer.class.getName()));//Works
  }






}
