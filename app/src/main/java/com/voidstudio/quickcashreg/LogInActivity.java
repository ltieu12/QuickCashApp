package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.Register.RegisterActivity;
import com.voidstudio.quickcashreg.firebase.Firebase;

/**
 * Activity for log in.
 * Users password and username are required to log in. Tests if user enters correct credentials
 * Logs in if credentials are correct. Implements switch to register button.
 * Is able to keep user logged in and hide/show the password they enter.
 */
public class LogInActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String WELCOME = "Welcome to In App!";
    public static final String EMPLOYER = "Employer";
    public static final String EMPLOYEE = "Employee";
    public static final String PREFERENCES = "login";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String ISLOGGED = "logged";
    public static final String EMPTYPE = "type";

    //Edit text reader helper method using delegation
    private final TextReader textReader = new TextReader();
    private Firebase firebase;
    private LogIn logIn;

    public SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button backToRegisterScreen = (Button)findViewById(R.id.logInRegisterButton);
        backToRegisterScreen.setOnClickListener(LogInActivity.this);

        Button continueButton = (Button)findViewById(R.id.continueButton);
        continueButton.setOnClickListener(LogInActivity.this);

        // Find the Show Password button and the Password Field
        Button showPassword = findViewById(R.id.showHidePassword);
        showPassword.setOnClickListener(LogInActivity.this);
        // logic for stay log in
        sp = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        if (sp.getBoolean(ISLOGGED, false)) {
            if(sp.getBoolean(EMPTYPE,false)) goToInAppActivityEmployee();
            else goToInAppActivityEmployer();
        }

        else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(ISLOGGED, false);
            editor.commit();
        }

        // Please do not move this command position, moving it to the top of onCreate will mess up login
        firebase = Firebase.getInstance();
        logIn = new LogIn(this);
    }

    /**
     * Method to change the text in the 'Show Password' button and show the password in password field
     * @param showPassword is the button
     * @param passwordText is the password field
     */
    public void showHidePassword(Button showPassword, EditText passwordText) {
        if (textReader.getFromEditText(passwordText).isEmpty()) {
            passwordText.setError("Please enter a password!");
        } else if (showPassword.getText().toString().equals("Show Password")) {
            showPassword.setText("Hide Password");
            passwordText.setTransformationMethod(null);
        } else {
            showPassword.setText("Show Password");
            passwordText.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    /**
     * On successful login from employer, this method switches activity to inAppActivityEmployer
     */
    public void goToInAppActivityEmployer() {
        Intent inAppEmployer = new Intent(this, InAppActivityEmployer.class);
        inAppEmployer.putExtra(WELCOME, "Hi Employer, you logged in");
        inAppEmployer.putExtra(USERNAME, getUserName());
        startActivity(inAppEmployer);
    }

    public void goToInAppActivityEmployee() {
        Intent inAppEmployee = new Intent(this, InAppActivityEmployee.class);
        inAppEmployee.putExtra(WELCOME, "Hi Employee, you logged in");
        inAppEmployee.putExtra("USERNAME", getUserName());
        inAppEmployee.putExtra("PASSWORD", getPassword());
        //inAppEmployee.putExtra("EMAIL",firebase.getEmailAddress(getUserName()));
        startActivity(inAppEmployee);
    }

    /**
     * If the user wants to register, this method switches the activity to register activity
     */
    protected void switchToRegisterWindow(){
        Intent registerSwitch = new Intent(LogInActivity.this, RegisterActivity.class);
        startActivity(registerSwitch);
    }

    protected String getUserName(){
        TextReader textReader = new TextReader();
        EditText  usernameBox = findViewById(R.id.logInUserName);
        return textReader.getFromEditText(usernameBox);
    }

    protected String getPassword(){
        TextReader textReader = new TextReader();
        EditText passwordBox = findViewById(R.id.textPassword);
        return textReader.getFromEditText(passwordBox);
    }


    /**
     * Reference from https://developer.android.com/training/data-storage/shared-preferences
     */
    private void stayLoggedIn() {
        sp = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp.edit();
        // Add username and password to login sharedPreferences state
        editor1.putString(USERNAME, getUserName());
        editor1.putString(PASSWORD, getPassword());
        editor1.putBoolean(EMPTYPE, logIn.employee);
        editor1.putBoolean(ISLOGGED, true);
        editor1.putString("EMAIL",firebase.getEmailAddress(getUserName()));
        editor1.commit();
    }

    /**
     * On Click method, 3 on click cases:
     * If the user clicks on the continue button, attempt log in with entered credentials,
     * When log in is successful switch to InAppActivity
     * If the user clicks register button, they are switched to register window
     * If the user clicks the show password button, the password entered password is shown
     * in plain text
     * @param view The button being pressed
     */
    @Override
    public synchronized void onClick(View view) {
        if (view.getId() == R.id.logInRegisterButton) {
            switchToRegisterWindow();
        }else if (view.getId() == R.id.showHidePassword) {
            showHidePassword(findViewById(R.id.showHidePassword), findViewById(R.id.textPassword));
        } else if(view.getId() == R.id.continueButton) {
            logIn.logIn(getUserName(), getPassword());
            if (logIn.isLogged) {
                //logIn.isEmployee();
                stayLoggedIn();
                logIn.getAlertMessage();
                if(logIn.employee){
                    goToInAppActivityEmployee();
                }
                else{
                    goToInAppActivityEmployer();
                }
            }
            else logIn.getAlertMessage();
        }
    }


}
