package com.voidstudio.quickcashreg.SearchJob;

import static com.voidstudio.quickcashreg.jobpost.JobPostActivity.USERNAME;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.InAppActivityEmployee;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;
import com.voidstudio.quickcashreg.jobpost.JobDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import users.Employee;

public class JobPostingActivity extends AppCompatActivity{

    //    public static final String MY_PREFS = "MY_PREFS";
//    private static String jobItem;
    private static Firebase firebase;
    private Employee employee;

    private ListView listView;
    private SearchView searchView;

    private Spinner distanceSpinner;
    private String selectedDistance;

    private Spinner durationSpinner;
    private String selectedDuration;

    private Spinner dateListedSpinner;
    private String selectedDate;

    private Spinner tagSpinner;
    private String selectedTag;

    private Spinner minWageSpinner;
    private String selectedMinWage;

    private Spinner maxWageSpinner;
    private String selectedMaxWage;

    private static final String JOB_NAME = "job name";
    private static final String JOB_DURATION = "job duration";
    private static final String JOB_DATE_POSTED = "job date posted";
    private static final String JOB_TAG = "job tag";
    private static final String JOB_EMPLOYER = "job employer";
    private static final String JOB_WAGE = "job wage";



    private static ArrayList<String> selectedFilters = new ArrayList<String>();

    public static final String OPTION_KM_DISTANCE1 = "1 km";
    public static final String OPTION_KM_DISTANCE2 = "2 km";
    public static final String OPTION_KM_DISTANCE3 = "5 km";
    public static final String OPTION_KM_DISTANCE4 = "10 km";

    public static final String OPTION_$_MIN_WAGE1 = "Min 10 $";
    public static final String OPTION_$_MIN_WAGE2 = "Min 20 $";
    public static final String OPTION_$_MIN_WAGE3 = "Min 50 $";
    public static final String OPTION_$_MIN_WAGE4 = "Min 100 $";

    public static final String OPTION_$_MAX_WAGE1 = "Max 100 $";
    public static final String OPTION_$_MAX_WAGE2 = "Max 200 $";
    public static final String OPTION_$_MAX_WAGE3 = "Max 500 $";
    public static final String OPTION_$_MAX_WAGE4 = "Max 1000 $";

    public static final String OPTION_HOUR_DURATION1 = "1 hour";
    public static final String OPTION_HOUR_DURATION2 = "2 hour";
    public static final String OPTION_HOUR_DURATION3 = "3 hour";
    public static final String OPTION_HOUR_DURATION4 = "4 hour";

    public static final String OPTION_DAY_DATE_LISTED1 = "Last 1 day";
    public static final String OPTION_DAY_DATE_LISTED2 = "Last 7 days";
    public static final String OPTION_DAY_DATE_LISTED3 = "Last 30 days";


    public static final String OPTION_TAG_1 = "Tag1";
    public static final String OPTION_TAG_2 = "Tag2";
    public static final String OPTION_TAG_3 = "Tag3";
    public static final String OPTION_TAG_4 = "Tag4";
    public static final String OPTION_TAG_5 = "Tag5";


    public static final String DEFAULT_DURATION = "Duration";
    public static final String DEFAULT_DISTANCE = "Radius";
    public static final String DEFAULT_DATE_LISTED = "Date listed";
    public static final String DEFAULT_TAG = "Tag";
    public static final String DEFAULT_MIN_WAGE = "Minimum Wage";
    public static final String DEFAULT_MAX_WAGE = "Maximum Wage";





    ArrayList<Job> allJobsList = new ArrayList<>();
    ArrayList<Job> filteredJobList = new ArrayList<>();

    private String currentSearchText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);
        firebase = Firebase.getInstance();
        employee = InAppActivityEmployee.employee;
        allJobsList = (ArrayList<Job>) firebase.getAllJobs();
        setUpList(allJobsList);

        onItemClickListener();

        searchView = findViewById(R.id.search_bar);
        onQueryTextListener();

        distanceSpinner = findViewById(R.id.distanceList);
        String[] distances = new String[]{DEFAULT_DISTANCE,OPTION_KM_DISTANCE1,OPTION_KM_DISTANCE2,OPTION_KM_DISTANCE3,OPTION_KM_DISTANCE4};


        durationSpinner = findViewById(R.id.durationList);
        String[] durations = new String[]{DEFAULT_DURATION,OPTION_HOUR_DURATION1,OPTION_HOUR_DURATION2,OPTION_HOUR_DURATION3,OPTION_HOUR_DURATION4};

        dateListedSpinner = findViewById(R.id.dayPostedList);
        String[] days = new String[]{DEFAULT_DATE_LISTED,OPTION_DAY_DATE_LISTED1,OPTION_DAY_DATE_LISTED2,OPTION_DAY_DATE_LISTED3};

        tagSpinner = findViewById(R.id.jobTagList);
        String[] tags = new String[]{DEFAULT_TAG,OPTION_TAG_1,OPTION_TAG_2,OPTION_TAG_3,OPTION_TAG_4,OPTION_TAG_5};

        minWageSpinner = findViewById(R.id.minimumWage);
        String[] minWages = new String[]{DEFAULT_MIN_WAGE,OPTION_$_MIN_WAGE1,OPTION_$_MIN_WAGE2,OPTION_$_MIN_WAGE3,OPTION_$_MIN_WAGE4};

        maxWageSpinner = findViewById(R.id.maximumWage);
        String[] maxWages = new String[]{DEFAULT_MAX_WAGE,OPTION_$_MAX_WAGE1,OPTION_$_MAX_WAGE2,OPTION_$_MAX_WAGE3,OPTION_$_MAX_WAGE4};

        setUpListSpinner(distanceSpinner, distances);
        setUpListSpinner(durationSpinner, durations);
        setUpListSpinner(dateListedSpinner, days);
        setUpListSpinner(tagSpinner,tags);
        setUpListSpinner(minWageSpinner,minWages);
        setUpListSpinner(maxWageSpinner,maxWages);


        spinnerListener();
    }


    private void spinnerListener() {
        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {

                selectedDistance = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedDistance);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedDuration = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedDuration);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dateListedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedDate = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedTag = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedTag);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        minWageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedMinWage = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedMinWage);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        maxWageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedMaxWage = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedMaxWage);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setUpListSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinner.setAdapter(listAdapter);
    }


    private void setUpList(ArrayList<Job> jobList)
    {
        listView = (ListView) findViewById(R.id.jobListView);
        setAdapter(jobList);
    }

    private void onItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job selectJob = (Job) (listView.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                intent.putExtra(USERNAME,((Employee) employee).getUsername());
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

    private void onQueryTextListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                currentSearchText = newText;
                ArrayList<Job> filteredBySearchJobs = new ArrayList<Job>();
                ArrayList<Job> jobList = new ArrayList<>();
                if((selectedFilters.size() == 0) || (selectedFilters.size() == 1 && selectedFilters.get(0).equals("all"))){
                    jobList = allJobsList;
                } else{
                    jobList = filteredJobList;
                }
                for(Job job : jobList){
                    if(job.getJobName().toUpperCase().contains(newText.toUpperCase())){
                        if(selectedFilters.contains("all")){
                            filteredBySearchJobs.add(job);
                        }
                        else{
                            for(String filterParam: selectedFilters){
                                String[] filterId = filterParam.split(" ");
                                if(filterId[filterId.length -1].contains("km")) {
                                    Location jobLocation = job.getLocation();
                                    double distance = jobLocation.distanceTo(employee.getLocation());
                                    if (distance <= Double.valueOf(filterId[0])) {
                                        if (currentSearchText == "") {
                                            filteredBySearchJobs.add(job);
                                        } else {
                                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                                filteredBySearchJobs.add(job);
                                            }
                                        }
                                    }
                                }

                                else if(filterId[filterId.length -1].contains("hour")){
                                    if (Integer.valueOf(job.getDuration()) <= Integer.valueOf(filterId[0])) {
                                        if (currentSearchText == "") {
                                            filteredBySearchJobs.add(job);
                                        } else {
                                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                                filteredBySearchJobs.add(job);
                                            }
                                        }
                                    }
                                }
                                else if(filterId[filterId.length -1].contains("day")){
                                    SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                    Date today = new Date();;
                                    Date jobDate = null;
                                    try {
                                        jobDate = sdf.parse(job.getDatePosted());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    long diffInMillies = Math.abs(today.getTime() - jobDate.getTime());
                                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                                    if ( diff <= Integer.valueOf(filterId[1])){
                                        if (currentSearchText == "") {
                                            filteredJobList.add(job);
                                        } else {
                                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                                filteredJobList.add(job);
                                            }
                                        }
                                    }
                                }
                                else if(filterId[0].contains("Tag")){
                                    if (filterId[0].equals(job.getTag())) {
                                        if (currentSearchText == "") {
                                            filteredBySearchJobs.add(job);
                                        } else {
                                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                                filteredBySearchJobs.add(job);
                                            }
                                        }
                                    }

                                }

                                else if(filterId[0].contains("Min")){
                                    if (Double.parseDouble(filterId[1]) >= Double.parseDouble(job.getWage())) {
                                        if (currentSearchText == "") {
                                            filteredBySearchJobs.add(job);
                                        } else {
                                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                                filteredBySearchJobs.add(job);
                                            }
                                        }
                                    }
                                }

                                else if(filterId[0].contains("Max")){
                                    if (Double.parseDouble(filterId[1]) <= Double.parseDouble(job.getWage())) {
                                        if (currentSearchText == "") {
                                            filteredBySearchJobs.add(job);
                                        } else {
                                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                                filteredBySearchJobs.add(job);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                setAdapter(filteredBySearchJobs);
                return false;
            }
        });
    }

    //TODO: IMPLEMENT SALARY RANGE FILTER
    private void filterList(String filterParameter) throws ParseException {
        String regex = "";
        if(filterParameter.contains("km")){
            regex = "km";
        } else if(filterParameter.contains("hour")){
            regex = "hour";
        } else if(filterParameter.contains("day")){
            regex = "day";
        } else if(filterParameter.contains("Tag")){
            regex = "Tag";
        }else if(filterParameter.contains("Min")){
            regex = "Min";
        }else if(filterParameter.contains("Max")){
            regex = "Max";
        }

        replaceFilter(regex,filterParameter);
        if(!selectedFilters.contains(filterParameter)){
            selectedFilters.add(filterParameter);
        }

        filteredJobList = new ArrayList<>();
        for(Job job: allJobsList) {
            checkAndAddFilter(job);
        }
        setAdapter(filteredJobList);
    }

    private void replaceFilter(String regex, String filterParameter){
        for(String filter: selectedFilters){
            if(filter.contains(regex)){
                int index = selectedFilters.indexOf(filter);
                selectedFilters.set(index, filterParameter);
            }
        }

    }
    private void setAdapter(ArrayList<Job> jobList)
    {
        SearchAdapter adapter = new SearchAdapter(getApplicationContext(), 0, jobList);
        listView.setAdapter(adapter);
    }

    public void allFilterTapped(View view){
        selectedFilters.clear();
        selectedFilters.add("all");
        dateListedSpinner.setSelection(0);
        durationSpinner.setSelection(0);
        distanceSpinner.setSelection(0);
        tagSpinner.setSelection(0);
        minWageSpinner.setSelection(0);
        maxWageSpinner.setSelection(0);
        searchView.setQuery("", false);
        setAdapter(allJobsList);
    }

    private void checkAndAddFilter(Job job) throws ParseException {
      for(String filterParam: selectedFilters){
        String[] filterId = filterParam.split(" ");
        if(filterId[filterId.length -1].contains("km")) {
//            JobLocation location = new JobLocation(job.getJobName());
//            Location jobLocation = location.getMyLocation();
//            double distance = jobLocation.distanceTo(employee.getLocation());
//            if (distance <= Double.valueOf(filterId[0]) && !filteredJobList.contains(job)) {
//                if (currentSearchText == "") {
//                    filteredJobList.add(job);
//                } else {
//                    if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
//                        filteredJobList.add(job);
//                    }
//                }
//            }
//            for(int i =0; i < filteredJobList.size(); i++){
//                JobLocation location1 = new JobLocation(filteredJobList.get(i).getJobName());
//                Location jobLocation1 = location.getMyLocation();
//                double distance1 = jobLocation.distanceTo(employee.getLocation());
//                if (distance1 > Double.valueOf(filterId[0])){
//                    filteredJobList.remove(i);
//                }
//            }
        }

        else if(filterId[filterId.length -1].contains("hour")){
            if (Integer.valueOf(job.getDuration()) <= Integer.valueOf(filterId[0]) && !filteredJobList.contains(job)) {
                if (currentSearchText == "") {
                    filteredJobList.add(job);
                } else {
                    if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                        filteredJobList.add(job);
                    }
                }
            }
            for(int i =0; i < filteredJobList.size(); i++){
                if (Integer.valueOf(filteredJobList.get(i).getDuration()) > Integer.valueOf(filterId[0])){
                    filteredJobList.remove(i);
                }
            }
        }
        else if(filterId[filterId.length -1].contains("day")){
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            Date today = new Date();;
            Date jobDate = sdf.parse(job.getDatePosted());
            long diffInMillies = Math.abs(today.getTime() - jobDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if ( diff <= Integer.valueOf(filterId[1]) && !filteredJobList.contains(job)){
                if (currentSearchText == "") {
                    filteredJobList.add(job);
                } else {
                    if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                        filteredJobList.add(job);
                    }
                }
            }

            for(int i =0; i < filteredJobList.size(); i++){
                SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                Date today1 = new Date();;
                Date jobDate1 = sdf1.parse(filteredJobList.get(i).getDatePosted());
                long diffInMillies1 = Math.abs(today1.getTime() - jobDate1.getTime());
                long diff1 = TimeUnit.DAYS.convert(diffInMillies1, TimeUnit.MILLISECONDS);
                if ( diff1 > Integer.valueOf(filterId[1])){
                    filteredJobList.remove(i);
                }
            }
        }
        else if(filterId[0].contains("Tag")){
            if (filterId[0].equals(job.getTag()) && !filteredJobList.contains(job) && !filteredJobList.contains(job)) {
                if (currentSearchText == "") {
                    filteredJobList.add(job);
                } else {
                    if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                        filteredJobList.add(job);
                    }
                }
            }

            for(int i =0; i < filteredJobList.size(); i++){
                if (!filterId[0].equals(filteredJobList.get(i).getTag())){
                    filteredJobList.remove(i);
                }
            }
        }

        else if(filterId[0].contains("Min") && filterId[filterId.length -1].contains("$") ){
            if (Double.parseDouble(filterId[1]) <= Double.parseDouble(job.getWage())&& !filteredJobList.contains(job)) {
                if (currentSearchText == "" ) {
                    filteredJobList.add(job);
                } else {
                    if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                        filteredJobList.add(job);
                    }
                }
            }

            for(int i =0; i < filteredJobList.size(); i++){
                if (Double.parseDouble(filterId[1]) > Double.parseDouble(filteredJobList.get(i).getWage())) {
                    filteredJobList.remove(i);
                }
            }
        }

        else if(filterId[0].contains("Max") && filterId[filterId.length -1].contains("$") ){
            if (Double.parseDouble(filterId[1]) >= Double.parseDouble(job.getWage()) && !filteredJobList.contains(job)) {
                if (currentSearchText == "") {
                    filteredJobList.add(job);
                } else {
                    if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                        filteredJobList.add(job);
                    }
                }
            }

            for(int i =0; i < filteredJobList.size(); i++){
                if (Double.parseDouble(filterId[1]) < Double.parseDouble(filteredJobList.get(i).getWage())) {
                    filteredJobList.remove(i);
                }
            }
        }
      }
    }
}