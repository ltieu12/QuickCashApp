package com.voidstudio.quickcashreg.SearchJob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.List;
public class SearchAdapter extends ArrayAdapter<Job> {
    public SearchAdapter(Context context, int resource, List<Job> jobList)
    {
        super(context,resource,jobList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Job job = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.job_cell, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.jobName);
        tv.setText(job.toString());
        return convertView;
    }
}
