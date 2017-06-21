package com.example.manushrivastava.muj_campusconnect;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
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

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentHomeActivity extends AppCompatActivity implements ServerResponse {


    JSONArray peoples = null;
    IndivigilationDetails a;
    String examinfoarr[][]=new String[20][6];
    int NoofDuty=0;
    String id="",name="",semester="",department="",course="";
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
        Bundle b=getIntent().getExtras();
        id=b.getString("id");
        name=b.getString("name");
        department=b.getString("department");
        semester=b.getString("semester");
        course=b.getString("course");

        a=new IndivigilationDetails(semester,department,"Student");
        a.delegate=this;
        a.execute("");
        super.onCreate(savedInstanceState);
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

    }
    public void ServerResponds(String result)
    {
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
                Toast.makeText(this,str,Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Log.d("Error","in json parsing");
            Toast.makeText(this,str+"Exception in json parsing"+e,Toast.LENGTH_LONG).show();
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
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

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
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (sectionNumber){
                case 1: rootView = inflater.inflate(R.layout.fragment_student_home_info, container, false);
                    studentInfoFragment(rootView);
                    break;
                case 2: rootView = inflater.inflate(R.layout.fragment_student_home_info, container, false);
                    studentExamFragment(rootView);
                    break;
                case 3: rootView = inflater.inflate(R.layout.fragment_student_home_info, container, false);
                    studentEventsFragment(rootView);
                    break;
                default: rootView = inflater.inflate(R.layout.fragment_faculty_home_info, container, false);
            }
            return rootView;
        }

        public void studentInfoFragment(View rootView){

            TextView studentInfoName = (TextView)rootView.findViewById(R.id.student_info_name_view);
            studentInfoName.setText("Ravi Singh");

            TextView studentInfoID = (TextView)rootView.findViewById(R.id.student_info_ID_view);
            studentInfoID.setText("12345");

            TextView studentInfoCourse = (TextView)rootView.findViewById(R.id.student_info_course_view);
            studentInfoCourse.setText("BTech");

            TextView studentInfoDept = (TextView)rootView.findViewById(R.id.student_info_dept_view);
            studentInfoDept.setText("CSE");

            TextView studentInfoSemester = (TextView)rootView.findViewById(R.id.student_info_semester_view);
            studentInfoSemester.setText("4");

            return;
        }

        public void studentExamFragment(View rootView){

        }

        public void studentEventsFragment(View rootView){

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
