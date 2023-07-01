package com.voidstudio.quickcashreg.jobpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.InAppActivityEmployee;
import com.voidstudio.quickcashreg.R;

import users.Employee;

public class SavePreferenceActivity extends AppCompatActivity implements View.OnClickListener {
    protected static String preference;
    protected Firebase firebase;
    private SharedPreferences sp;

    private static String username;
    private static String password;
    private static String email;
    protected static Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_preference_activity);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(SavePreferenceActivity.this);

        firebase = Firebase.getInstance();
        employee = InAppActivityEmployee.employee;

    }

    @Override
    public void onClick(View view) {
        EditText editPreference = findViewById(R.id.editPreference);
        preference = editPreference.getText().toString();

        if (!preference.equals("")) {
            employee.setPreference(preference);
        }
        Intent inAppEmployee = new Intent(SavePreferenceActivity.this, InAppActivityEmployee.class);
        startActivity(inAppEmployee);
    }
}
