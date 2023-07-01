package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.SearchJob.SearchAdapter;
import com.voidstudio.quickcashreg.jobpost.Job;
import com.voidstudio.quickcashreg.jobpost.JobDetailsActivity;
import com.voidstudio.quickcashreg.management.EmployeeContractManager;
import com.voidstudio.quickcashreg.management.IContractManager;
import com.voidstudio.quickcashreg.management.JobContract;

import java.util.ArrayList;

import users.Employee;

public class UserApplicationActivity extends AppCompatActivity {


    private static final String JOB_NAME = "job name";
    private static final String JOB_DURATION = "job duration";
    private static final String JOB_DATE_POSTED = "job date posted";
    private static final String JOB_TAG = "job tag";
    private static final String JOB_EMPLOYER = "job employer";
    private static final String JOB_WAGE = "job wage";
    private ListView listViewInProgress;
    private ListView listViewCompleted;
    private TextView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_application);
        Employee employee = InAppActivityEmployee.employee;
        IContractManager manager = EmployeeContractManager.getInstance(employee);
        ArrayList<JobContract> jobContractsInProgress = manager.getIncompletedContracts();
        ArrayList<JobContract> jobContractsCompleted = manager.getCompletedContracts();
        listViewInProgress = (ListView)findViewById(R.id.listView1);
        listViewCompleted = (ListView)findViewById(R.id.listView2);
        ArrayList<Job> inProgressJobList = new ArrayList<>();
        onItemClickListener();
        if(jobContractsInProgress.size() > 0) {
            for (JobContract c : jobContractsInProgress) {
                inProgressJobList.add(c.getJob());
            }
            setAdapter(inProgressJobList, listViewInProgress);
        }
        else{
            notification = findViewById(R.id.inProgressNotification);
            notification.setText("No In Progress Job Right Now");
        }

        ArrayList<Job> completedJobList = new ArrayList<>();
        if(jobContractsCompleted.size() > 0) {
            for (JobContract c : jobContractsCompleted) {
                completedJobList.add(c.getJob());
            }
            setAdapter(completedJobList, listViewCompleted);
        }
        else{
            notification = findViewById(R.id.CompletedNotification);
            notification.setText("No Completed Job Right Now");
        }
    }

    private void setAdapter(ArrayList<Job> jobList, ListView listView)
    {
        SearchAdapter adapter = new SearchAdapter(getApplicationContext(), 0, jobList);
        listView.setAdapter(adapter);
        ListUtils.setDynamicHeight(listView);
    }

    private void onItemClickListener() {
        listViewInProgress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job selectJob = (Job) (listViewInProgress.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                intent.putExtra(JOB_NAME, selectJob.getJobName());
                intent.putExtra(JOB_DATE_POSTED, selectJob.getDatePosted());
                intent.putExtra(JOB_DURATION, selectJob.getDuration());
                intent.putExtra(JOB_WAGE, selectJob.getWage());
                intent.putExtra(JOB_EMPLOYER, selectJob.getUser());
                intent.putExtra(JOB_TAG, selectJob.getTag());
                startActivity(intent);
            }
        });

        listViewCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job selectJob = (Job) (listViewInProgress.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                intent.putExtra(JOB_NAME, selectJob.getJobName());
                intent.putExtra(JOB_DATE_POSTED, selectJob.getDatePosted());
                intent.putExtra(JOB_DURATION, selectJob.getDuration());
                intent.putExtra(JOB_WAGE, selectJob.getWage());
                intent.putExtra(JOB_EMPLOYER, selectJob.getUser());
                intent.putExtra(JOB_TAG, selectJob.getTag());
                startActivity(intent);
            }
        });
    }


    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}

