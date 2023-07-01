package com.voidstudio.quickcashreg.Location;


import android.location.Location;

import com.voidstudio.quickcashreg.firebase.Firebase;

/**
 * Interface for location, job and user location will implement
 */
public interface ILocation {

  Firebase firebase = Firebase.getInstance();
  Location getMyLocation();
  void setLocation(Location location);
  double[] getLatLong();
  double calculateDistance(Location l1, Location l2);
  boolean hasLocationAccessPermission();



}
