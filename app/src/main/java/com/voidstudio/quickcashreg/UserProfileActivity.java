package com.voidstudio.quickcashreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.voidstudio.quickcashreg.firebase.Firebase;

import users.Employee;

public class UserProfileActivity extends AppCompatActivity {
    private Firebase firebase;
    String username;

    private SharedPreferences sp;
    public static Employee employee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        firebase = Firebase.getInstance();
        sp = getSharedPreferences("login", MODE_PRIVATE);
//        employee = InAppActivityEmployee.employee;
        username = sp.getString("Username", "");
        displayUserName();
        displayUserEmail();
        displayUserType();
        displayUserLocation();
    }

    protected void displayUserName() {
        TextView userName = findViewById(R.id.user_name_textView);
        userName.setText(username);
    }


    protected void displayUserType() {
        TextView userType = findViewById(R.id.user_type_textView);
        userType.setText(firebase.getUserType(username));
    }


    protected void displayUserEmail() {
        TextView userEmail = findViewById(R.id.email_textView);
        userEmail.setText(firebase.getEmailAddress(username));
    }


    //TODO: WILL IMPLEMENT THIS AFTER MERGE
    protected void displayUserLocation() {
        TextView location = findViewById(R.id.location_textView);
        location.setText("Halifax");
    }

}