package com.example.manushrivastava.muj_campusconnect;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentHomeActivity extends AppCompatActivity implements ServerResponse {


    JSONArray peoples = null;
    static IndivigilationDetails a;
    static String examinfoarr[][]=new String[20][6];
    static int NoofDuty=0;
    static int temp=0;
    static String id="",name="",semester="",department="",course="",type;
    private static final String TAG_IRESULTS="result";
    private static final String TAG_ICOURSEID = "courseId";
    private static final String TAG_ICOURSENAME = "courseName";
    private static final String TAG_DATE ="date";
    private static final String TAG_TIME ="time";
    private static final String TAG_VENUE ="venue";
    private static final String TAG_INDIVIGILATOR ="indivigilator";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        temp=0;
        Bundle b=getIntent().getExtras();
        id=b.getString("id");
        name=b.getString("name");
        department=b.getString("department");
        semester=b.getString("semester");
        course=b.getString("course");
        type=b.getString("entry");
        a=new IndivigilationDetails(semester,department,course,"Student","none");
        a.delegate=this;
        a.execute("");
        super.onCreate(savedInstanceState);
        temp=0;

    }
    public void ServerResponds(String result)
    {NoofDuty=0;
        Log.d("reached","process finish");
        Log.d("string is",result);
        String str="";
        try {
            JSONObject jsonObj = new JSONObject(result);
            peoples = jsonObj.getJSONArray(TAG_IRESULTS);
            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String courseid = c.getString(TAG_ICOURSEID);
                String coursename = c.getString(TAG_ICOURSENAME);
                String date = c.getString(TAG_DATE);
                String time = c.getString(TAG_TIME);
                String venue = c.getString(TAG_VENUE);
                String indivigilator=c.getString(TAG_INDIVIGILATOR);
                NoofDuty+=1;
                examinfoarr[i][0]=courseid;
                examinfoarr[i][1]=coursename;
                examinfoarr[i][2]=date;
                examinfoarr[i][3]=time;
                examinfoarr[i][4]=venue;
                examinfoarr[i][5]=indivigilator;
                str=examinfoarr[i][0]+" "+examinfoarr[i][1]+" "+examinfoarr[i][2]+" "+examinfoarr[i][3]+" "+examinfoarr[i][4]+" "+examinfoarr[i][5];

            }

        } catch (JSONException e) {
            Log.d("Error","in json parsing");
        }
        if(temp==0) {
            Log.d("arriving", temp + "no prob");
            temp = 1;
            setContentView(R.layout.activity_student_home);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);


            Intent serviceIntent = new Intent(this, ServiceforMatching.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("key_array_array", examinfoarr);
            serviceIntent.putExtras(mBundle);
            serviceIntent.putExtra("semester", semester);
            serviceIntent.putExtra("course", course);
            serviceIntent.putExtra("department", department);
            this.startService(serviceIntent);


            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(this, ServiceforMatching.class);
            mBundle = new Bundle();
            mBundle.putSerializable("key_array_array", examinfoarr);
            intent.putExtras(mBundle);
            intent.putExtra("semester", semester);
            intent.putExtra("course", course);
            intent.putExtra("department", department);
            PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
            Log.d("checking", "doing some work for repeating");
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            // Start service every hour
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    1000, pintent);
            Log.d("ending", "reching of making a service");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ScheduleFetch {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        String facultyScheduleArray[][]=new String[8][2];
        int dataPresent=0;
        TimeTableFetch time;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView;

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (sectionNumber){
                case 1: rootView = inflater.inflate(R.layout.fragment_student_home_info, container, false);
                    studentInfoFragment(rootView);
                    break;
                case 2: rootView = inflater.inflate(R.layout.fragment_student_home_exam, container, false);
                    studentExamFragment(rootView);
                    break;
                case 3: rootView = inflater.inflate(R.layout.fragment_student_home_info, container, false);
                    studentEventsFragment(rootView);
                    break;
                default: rootView = inflater.inflate(R.layout.fragment_faculty_home_info, container, false);
            }
            return rootView;
        }

        public void studentInfoFragment(final View rootView){

            TextView studentInfoName = (TextView)rootView.findViewById(R.id.student_info_name_view);
            studentInfoName.setText(name);

            TextView studentInfoID = (TextView)rootView.findViewById(R.id.student_info_ID_view);
            studentInfoID.setText(id);

            TextView studentInfoCourse = (TextView)rootView.findViewById(R.id.student_info_course_view);
            studentInfoCourse.setText(course);

            TextView studentInfoDept = (TextView)rootView.findViewById(R.id.student_info_dept_view);
            studentInfoDept.setText(department);

            TextView studentInfoSemester = (TextView)rootView.findViewById(R.id.student_info_semester_view);
            studentInfoSemester.setText(semester);

            EditText facultyName = (EditText)rootView.findViewById(R.id.faculty_name_timetable_field);
            EditText facultyDepartment = (EditText)rootView.findViewById(R.id.faculty_dept_timetable_field);
            EditText day = (EditText)rootView.findViewById(R.id.faculty_timetable_day_field);

            Button viewFacultyScheduleButton = (Button)rootView.findViewById(R.id.view_faculty_timetable_button);

            if (facultyName.getText().toString() != null && facultyDepartment.getText().toString() != null && day.getText().toString() != null){
                viewFacultyScheduleButton.setEnabled(true);
            }

            viewFacultyScheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText facultyName = (EditText)rootView.findViewById(R.id.faculty_name_timetable_field);
                    EditText department = (EditText)rootView.findViewById(R.id.faculty_dept_timetable_field);
                    EditText day = (EditText)rootView.findViewById(R.id.faculty_timetable_day_field);

                    time=new TimeTableFetch(facultyName.getText().toString(),department.getText().toString(),day.getText().toString());
                    PlaceholderFragment obj = new PlaceholderFragment();
                    time.delegate=obj;
                    time.execute("");


                }
            });

            return;
        }

        public void studentExamFragment(final View rootView){
            RecyclerView recyclerView;
            ExamScheduleAdapter examScheduleAdapter;
            ExamSchedule examSchedule;
            ArrayList<ExamSchedule> examScheduleArrayList = new ArrayList<>();

            recyclerView = (RecyclerView)rootView.findViewById(R.id.student_exam_recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            examScheduleAdapter = new ExamScheduleAdapter(examScheduleArrayList);
            recyclerView.setAdapter(examScheduleAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Button studentExamTimeTableButton = (Button)rootView.findViewById(R.id.student_exam_view_timetable_button);

            final EditText studentExamCourseField = (EditText)rootView.findViewById(R.id.student_exam_course_field);
            final EditText studentExamDeptField = (EditText)rootView.findViewById(R.id.student_exam_dept_field);
            final EditText studentExamSemesterField = (EditText)rootView.findViewById(R.id.student_exam_semester_field);

            int i = 0;
            while (examinfoarr[i][0] != null && NoofDuty!=0){
                examSchedule = new ExamSchedule(examinfoarr[i][1], examinfoarr[i][0], examinfoarr[i][2], examinfoarr[i][3], examinfoarr[i][4], examinfoarr[i][5]);
                examScheduleArrayList.add(examSchedule);
                i++;
            }

            studentExamTimeTableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a=new IndivigilationDetails(studentExamSemesterField.getText().toString(),studentExamDeptField.getText().toString(),studentExamCourseField.getText().toString(),"Student","none");
                    StudentHomeActivity obj = new StudentHomeActivity();
                    a.delegate=obj;
                    a.execute("");
                    studentExamFragment(rootView);
                }
            });

        }

        public void studentEventsFragment(View rootView){

        }
        public void Retrieved(String result)
        {
            Log.d("checking","reaching in serverresponds interface function");
            dataPresent=0;
            try {


                JSONObject c = new JSONObject(result);
                Log.d("problem not with", "object creation");
                String slotone = c.getString("one");
                String slottwo = c.getString("two");
                String slotthree = c.getString("three");
                String slotfour = c.getString("four");
                String slotfive = c.getString("five");
                String slotsix = c.getString("six");
                String slotseven = c.getString("seven");
                String sloteight = c.getString("eight");
                if(slotone.equals("no tuple"))
                {
                    dataPresent=0;

                }
                else
                {

                    dataPresent=1;
                    facultyScheduleArray[0][0]="9:00 AM - 10:00 AM";
                    facultyScheduleArray[0][1]=slotone;
                    facultyScheduleArray[1][0]="10:00 AM - 11:00 AM";
                    facultyScheduleArray[1][1]=slottwo;
                    facultyScheduleArray[2][0]="11:00 AM - 12:00 PM";
                    facultyScheduleArray[2][1]=slotthree;
                    facultyScheduleArray[3][0]="12:00 PM - 1:00 PM";
                    facultyScheduleArray[3][1]=slotfour;
                    facultyScheduleArray[4][0]="1:00 PM - 2:00 PM";
                    facultyScheduleArray[4][1]=slotfive;
                    facultyScheduleArray[5][0]="2:00 PM - 3:00 PM";
                    facultyScheduleArray[5][1]=slotsix;
                    facultyScheduleArray[6][0]="3:00 PM - 4:00 PM";
                    facultyScheduleArray[6][1]=slotseven;
                    facultyScheduleArray[7][0]="4:00 PM - 5:00 PM";
                    facultyScheduleArray[7][1]=sloteight;
                    String str=facultyScheduleArray[0][0]+facultyScheduleArray[1]+facultyScheduleArray[2]+facultyScheduleArray[3];
                    Log.d("result of ttable fetch ",str);
                }
            } catch (JSONException e) {
                Log.d("Error", "Exception"+e);
            }


            Bundle bundle = new Bundle();
            bundle.putSerializable("key_array_array", facultyScheduleArray);
            Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment == null){
                fragment = new FacultyScheduleFragmnet();
            }
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Exam";
                case 2:
                    return "Events";
            }
            return null;
        }
    }
}
