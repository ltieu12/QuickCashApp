package com.voidstudio.quickcashreg.PayPal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.voidstudio.quickcashreg.R;

import java.util.List;
public class PaySearchAdapter extends ArrayAdapter<String> {
  public PaySearchAdapter(Context context, int resource, List<String> payList)
  {
    super(context,resource,payList);
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    String item = getItem(position);

    if(convertView == null)
    {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.job_cell, parent, false);
    }

    TextView tv = (TextView) convertView.findViewById(R.id.jobName);
    tv.setText(item);
    return convertView;
  }
}
