package com.voidstudio.quickcashreg.Location;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voidstudio.quickcashreg.R;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    String latlong = new String("44,-63");
    String city = new String("Halifax");
    public int kilometerRadius = 10;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        this.captureIntent();
    }


    protected void captureIntent() {
        try {
            Intent latlongItent = getIntent();
            if (latlongItent != null) {
                if (latlongItent.hasExtra("taskLocation"))
                    this.latlong = latlongItent.getStringExtra("taskLocation");
                if (latlongItent.hasExtra("city"))
                    this.city = latlongItent.getStringExtra("city");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        String[] parts = latlong.split(",");
        double latitude = Double.parseDouble(parts[0].trim());
        double longitude = Double.parseDouble(parts[1].trim());

        LatLng itemLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(itemLocation).title(this.city));
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(itemLocation);
        circleOptions.radius(kilometerRadius*1000);
        circleOptions.strokeColor(Color.RED);
        mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(itemLocation));
        mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(itemLocation));

    }
}
