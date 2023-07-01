package com.voidstudio.quickcashreg.Location;

import android.location.Location;
import android.location.LocationManager;

public class JobLocation implements ILocation{
  private String jobName;
  public JobLocation(String jobName){
    this.jobName = jobName;
  }
  @Override
  public Location getMyLocation() {
    Location jobLocation = new Location(LocationManager.GPS_PROVIDER);
    double[] coords = getLatLong();
    if(coords != null) {
      jobLocation.setLongitude(getLatLong()[0]);
      jobLocation.setLatitude(getLatLong()[1]);
      return jobLocation;
    }
    else return null;
  }

  @Override
  public void setLocation(Location location) {
      double latitude = location.getLatitude();
      double longitude = location.getLongitude();
      firebase.setJobCoordinates(jobName,latitude,longitude);
  }

  @Override
  public double[] getLatLong() {
    return firebase.getJobCoordinates(jobName);
  }

  @Override
  public double calculateDistance(Location l1, Location l2) {
    return 0;
  }

  @Override
  public boolean hasLocationAccessPermission() {
    return false;
  }
}
