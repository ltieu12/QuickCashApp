package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;

import com.voidstudio.quickcashreg.Location.GPS;
import com.voidstudio.quickcashreg.Location.GPSActivity;
import com.voidstudio.quickcashreg.Location.ILocation;
import com.voidstudio.quickcashreg.Location.UserLocation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class UserLocationUnitTest {

  public static ILocation locate;
  public static GPSActivity gpsAct;
  public static GPS gps;
  private static Location location;
  private static final double longitude = 0.0;
  private static final double latitude = 0.0;
  private static final double[] latlng = {longitude,latitude};

  private static Context mockContext;

  private static Resources mockContextResources;

  private static Activity mockActivity;

  @BeforeClass
  public static void setup(){
    gpsAct = Mockito.mock(GPSActivity.class);
    gps = Mockito.mock(GPS.class);
    location = Mockito.mock(Location.class);
    when(gps.canGetLocation()).thenReturn(true);
    when(gps.getLocation()).thenReturn(location);



    mockContext = Mockito.mock(Context.class);
    mockContextResources = Mockito.mock(Resources.class);
    mockActivity = Mockito.mock(Activity.class);
    when(mockContextResources.getString(anyInt())).thenReturn("mocked string");
    when(mockContextResources.getStringArray(anyInt())).thenReturn(new String[]{"mocked string 1", "mocked string 2"});
    when(mockContextResources.getColor(anyInt())).thenReturn(Color.BLACK);
    when(mockContextResources.getBoolean(anyInt())).thenReturn(false);
    when(mockContextResources.getDimension(anyInt())).thenReturn(100f);
    when(mockContextResources.getIntArray(anyInt())).thenReturn(new int[]{1,2,3});
    when(mockContext.getResources()).thenReturn(mockContextResources);
    when(mockContext.getApplicationContext()).thenReturn(mockContext);



    locate = new UserLocation(mockContext);
  }
  @AfterClass
  public static void tearDown(){System.gc();}

  @Test
  public void getMyLocationTest(){
    location.setLongitude(longitude);
    location.setLatitude(latitude);
    when(gps.getLocation()).thenReturn(location);
    when(gps.getLatitude()).thenReturn(latitude);
    when(gps.getLongitude()).thenReturn(longitude);
    locate = new UserLocation(gps.getBaseContext());
    assertEquals(latitude, locate.getLatLong()[0], 0.0);
    assertEquals(longitude, locate.getLatLong()[1], 0.0);
  }


}
