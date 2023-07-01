package com.voidstudio.quickcashreg.Location;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.voidstudio.quickcashreg.R;

import users.User;


public class GPSActivity extends AppCompatActivity {

  private static final int REQUEST_CODE_PERMISSION = 2;
  String fPermission = Manifest.permission.ACCESS_FINE_LOCATION;
  String cPermission = Manifest.permission.ACCESS_COARSE_LOCATION;
  User user;
  String username = "Not yet";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gps_layout);
    Button locationButton = (Button) findViewById(R.id.getLocation);
    locationButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        user = User.getUser(username);
        if(user == null) return;
        if(user.startLocating(GPSActivity.this)){
          double[] coords = user.locate.getLatLong();
          double latitude = coords[0];
          double longitude = coords[1];
          Toast.makeText(getBaseContext(), "Current location:(" + latitude + "," + longitude + ")",
                  Toast.LENGTH_LONG).show();
        }
      }
    });

    Button calculateDistButton = (Button) findViewById(R.id.calculateDist);
    calculateDistButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        double distance = calculateDistance();
        Toast.makeText(getBaseContext(), "Distance: " + distance + " KM", Toast.LENGTH_LONG).show();
      }
    });
  }

  protected double[] getLongLat(String locationValue) {
    String[] parts = locationValue.split(",");
    double longitude = Double.parseDouble(parts[0]);
    double latitude = Double.parseDouble(parts[1]);
    return new double[]{longitude, latitude};
  }

  protected Location getSourceLocation() {
    EditText sourceLoc = findViewById(R.id.sourceLoc);
    String source = sourceLoc.getText().toString();
    double[] coord = getLongLat(source);
    Location location = new Location(LocationManager.GPS_PROVIDER);
    location.setLongitude(coord[0]);
    location.setLatitude(coord[1]);
    return location;
  }

  protected Location getDestLocation() {
    EditText destLoc = findViewById(R.id.destLoc);
    String destination = destLoc.getText().toString();
    double[] coord = getLongLat(destination);
    Location location = new Location(LocationManager.GPS_PROVIDER);
    location.setLongitude(coord[0]);
    location.setLatitude(coord[1]);
    return location;
  }

  protected double calculateDistance() {
    Location sourceLocation = getSourceLocation();
    Location destLocation = getDestLocation();
    return sourceLocation.distanceTo(destLocation) / 1000;
  }


  protected boolean hasLocationAccessPermission() {
    try {
      ActivityCompat.requestPermissions(this, new String[]{fPermission}, REQUEST_CODE_PERMISSION);
      ActivityCompat.requestPermissions(this, new String[]{cPermission}, REQUEST_CODE_PERMISSION);
      return true;
    } catch (Exception exc) {
      return false;
    }
  }
}

