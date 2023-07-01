package com.voidstudio.quickcashreg.Register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.LogInActivity;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.TextReader;

/**
 * Main Activity Class is the Register Page,
 * This is where the user registers for our app
 * The user can choose between being an employee or employer.
 * The user must input
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner roleList;
    private String selectedRole;
    private Register register = new Register();
    private final TextReader textReader = new TextReader();

    private static Firebase firebase;


    public static final String EMPLOYEE_USERTYPE = "Employee";
    public static final String EMPLOYER_USERTYPE = "Employer";
    public static final String NULL_USERTYPE = "-";

    public static final String CONFIRM_PASSWORD_MESSAGE = "Two passwords are the same";
    public static final String VALID_MESSAGE_EMPTY_STRING = "";



    /**
     * On Create initializes all buttons text views and event listeners. Also chooses layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button registerButton = findViewById(R.id.buttonreg);
        Button loginButton = findViewById(R.id.loginButton);

        TextView email = (TextView) findViewById(R.id.eMail);
        TextView password = (TextView) findViewById(R.id.password);
        TextView passwordConfirm = (TextView) findViewById(R.id.passwordConfirm);


        TextView hintForPassWord = (TextView) findViewById(R.id.hintForPassword);
        TextView hintForPassWordConfirm = (TextView) findViewById(R.id.hintForPasswordConfirm);
        TextView hintForEmail = (TextView) findViewById(R.id.hintForEmail);

        firebase = Firebase.getInstance();

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        setUpRoleListSpinner();
        roleListSpinnerListener();


        // check e-mail form
        emailFormatChecker(email, hintForEmail);

        // check confirm password is same or not
        confirmPasswordChecker(password, passwordConfirm, hintForPassWordConfirm);

        // check password
        passwordChecker(password,hintForPassWord);
    }

    private void roleListSpinnerListener() {
        roleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedRole = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUpRoleListSpinner() {
        roleList = findViewById(R.id.roleList);
        String[] roles = new String[]{NULL_USERTYPE, EMPLOYEE_USERTYPE, EMPLOYER_USERTYPE};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        roleList.setAdapter(adapter);
    }

    private void emailFormatChecker(TextView eMail, TextView hintForEmail) {
        eMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String emailString = eMail.getText().toString();

                if (!register.isValidEmailAddress(emailString)) {
                    hintForEmail.setText(register.EMAIL_ERROR_MESSAGE);
                    hintForEmail.setTextColor(Color.RED);
                } else {
                    hintForEmail.setText(VALID_MESSAGE_EMPTY_STRING);
                }
            }
        });
    }

    private void passwordChecker(TextView password, TextView hintForPassWord) {
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordString  = password.getText().toString();

                if (!register.isValidPassword(passwordString)) {
                    hintForPassWord.setText(register.PASSWORD_ERROR_MESSAGE);
                    hintForPassWord.setTextColor(Color.RED);
                }
                else {
                    hintForPassWord.setText(VALID_MESSAGE_EMPTY_STRING);
                }
            }
        });
    }

    private void confirmPasswordChecker(TextView password, TextView passwordConfirm, TextView hintForPassWord) {
        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordString  = password.getText().toString();
                String passwordStringConfirm  = passwordConfirm.getText().toString();

                if (!register.isValidConfirmPassword(passwordString,passwordStringConfirm)) {
                    hintForPassWord.setText(register.CONFIRM_PASSWORD_ERROR_MESSAGE);
                    hintForPassWord.setTextColor(Color.RED);
                } else {
                    hintForPassWord.setText(CONFIRM_PASSWORD_MESSAGE);
                    hintForPassWord.setTextColor(Color.GREEN);
                }
            }
        });
    }


    /**
        Getter method to get user name
    **/
    protected String getUserName(){
        EditText userName = findViewById(R.id.userName);
        return textReader.getFromEditText(userName);
    }

    /**
        Getter method to get email address
    **/
    protected String getEmail(){
        EditText emailAddress = findViewById(R.id.eMail);
        return textReader.getFromEditText(emailAddress);
    }

    /**
        Getter method to get password
    **/
    protected String getPassword(){
        EditText password = findViewById(R.id.password);
        return textReader.getFromEditText(password);
    }

    /**
        Getter method to get confirm password
     **/
    protected String getConfirmPassword(){
        EditText confirmPassword = findViewById(R.id.passwordConfirm);
        return textReader.getFromEditText(confirmPassword);
    }

    /**
     Getter method to get confirm password
     **/
    protected String getMinimumSalary(){
        EditText confirmPassword = findViewById(R.id.minimumSalary);
        return textReader.getFromEditText(confirmPassword);
    }

    /**
     * Switches to log in window
     */
    public void switchToLogInWindow(){
        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
        startActivity(intent);
    }

    /**
     * Sets a status message
     * @param message the message to set as status message.
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    /**
     * On click method, validates entered credentials and moves to desired page
     * @param view The button pressed
     */
    public void onClick(View view){
        String userName = getUserName();
        String email = getEmail();
        String password = getPassword();
        String confirmPassword = getConfirmPassword();
        String minimumSalary = getMinimumSalary();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        if(view.getId() == R.id.buttonreg) {
            String message = register.registerUser(userName, email, password, confirmPassword, selectedRole, minimumSalary);
            if(message.equals(register.SUCCESS_MESSAGE)){
                switchToLogInWindow();
            }
            setStatusMessage(message);
            alertBuilder.setMessage(message);
            alertBuilder.setPositiveButton("OK", null);
            alertBuilder.create();
            alertBuilder.show();
        }
        else if(view.getId() == R.id.loginButton){
            switchToLogInWindow();
        }
    }

}