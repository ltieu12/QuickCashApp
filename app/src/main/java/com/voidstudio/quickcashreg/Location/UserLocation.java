package com.voidstudio.quickcashreg.Location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import androidx.core.app.ActivityCompat;

public class UserLocation implements ILocation{

  private GPS gps;
  Location location;
  Context context;
  public UserLocation(Context context){
    this.context = context;
    if(hasLocationAccessPermission()) {
      this.gps = new GPS(context);
      if (this.gps.canGetLocation()) {
        this.location = this.gps.getLocation();
      }
    }
  }
  public UserLocation(Location l){
    this.location = l;
  }

  @Override
  public Location getMyLocation() {
    return location;
  }

  @Override
  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public double[] getLatLong(){
    this.gps = new GPS(context);
    double latitude = this.gps.getLatitude();
    double longitude = this.gps.getLongitude();
    double[] latlng = new double[2];
    latlng[0] = latitude;
    latlng[1] = longitude;
    return latlng;
  }

  @Override
  public double calculateDistance(Location l1, Location l2) {
    return l1.distanceTo(l2)/1000;
  }

  @Override
  public boolean hasLocationAccessPermission() {
    if(!(context instanceof Activity)) return false;
    try {
      ActivityCompat.requestPermissions((Activity)context, new String[]{LocationConstants.fPermission},
              LocationConstants.REQUEST_CODE_PERMISSION);
      ActivityCompat.requestPermissions((Activity)context, new String[]{LocationConstants.cPermission},
              LocationConstants.REQUEST_CODE_PERMISSION);
      return true;
    } catch (Exception exc) {
      return false;
    }
  }

}
