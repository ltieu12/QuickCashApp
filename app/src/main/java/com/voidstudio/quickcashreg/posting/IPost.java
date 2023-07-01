package com.voidstudio.quickcashreg.posting;

import com.voidstudio.quickcashreg.firebase.Firebase;

public interface IPost {
  Firebase firebase = Firebase.getInstance();
  void post();
}
