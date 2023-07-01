package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.voidstudio.quickcashreg.firebase.Firebase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class FirebaseUnitTest {

  static Firebase firebase;
  //Known database user
  private static final String username = "min";
  private static final String email = "minh@gmail.com";
  private static final String password = "123456";
  private static final String userType = "Employee";

  @BeforeClass
  public static void setup(){
      firebase = Mockito.mock(Firebase.class);
  }

  @AfterClass
  public static void tearDown(){
    System.gc();
  }

  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

  @Test
  public void getEmailTest(){
    Mockito.when(firebase.getEmailAddress(username)).thenReturn(email);
    assertTrue(firebase.getEmailAddress(username).equals(email));
  }

  @Test
  public void getUserTypeTest(){
    Mockito.when(firebase.getUserType(username)).thenReturn(userType);
    assertTrue(firebase.getUserType(username).equals(userType));
  }

  @Test
  public void getPasswordTest(){
    Mockito.when(firebase.getPassword(username)).thenReturn(password);
    assertTrue(firebase.getPassword(username).equals(password));
  }

}
