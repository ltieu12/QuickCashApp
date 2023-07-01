package com.voidstudio.quickcashreg;

import android.widget.EditText;

import androidx.annotation.NonNull;

/**
 *
 */
public class TextReader {
  public TextReader() {
  }

  @NonNull
  public String getFromEditText(EditText textBox) {
    return textBox.getText().toString().trim();
  }
}