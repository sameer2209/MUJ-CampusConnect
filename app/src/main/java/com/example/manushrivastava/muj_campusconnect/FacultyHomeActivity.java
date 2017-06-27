package com.example.manushrivastava.muj_campusconnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FacultyHomeActivity extends AppCompatActivity implements Response,ServerResponse,SubExamInterface{
    DataFetching s;
    static int numberOfCourses=0;
    static String courseId[]=new String[10];
    static String courseName[]=new String[10];
    IndivigilationDetails a;
    SubExams t;
    static String id="",name="",department="",type="";
    private static final String TAG_RESULTS="result";
    private static final String TAG_FacultyID = "facultyId";
    private static final String TAG_COURSEID = "courseId";
    private static final String TAG_COURSENAME = "courseName";
    JSONArray peoples = null;

    static String courseexam[][]=new String[20][6];
    static String examinfoarr[][]=new String[20][5];
    static int NoofDuty=0;int numberOfExams=0;

    static RecyclerView facultyExamRecyclerView;
    static boolean isArrayListPopulated = false;
    static ArrayList<InvigilationSchedule> invigilationScheduleArrayList = new ArrayList<>();
    static ArrayList<ExamSchedule> examScheduleArrayList = new ArrayList<>();

    private static final String TAG_IRESULTS="result";
    private static final String TAG_ICOURSEID = "courseId";
    private static final String TAG_ICOURSENAME = "courseName";
    private static final String TAG_DATE ="date";
    private static final String TAG_TIME ="time";
    private static final String TAG_VENUE ="venue";
    JSONArray people = null;



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
        type=b.getString("entry");
        t=new SubExams(id,name,"nocourse","Faculty","exam");
        t.delegate=this;
        t.execute("");
        a=new IndivigilationDetails(id,name,"nocourse","Faculty","none");
        a.delegate=this;
        a.execute("");

        s=new DataFetching(id);
        s.delegate=this;
        s.execute("");


        super.onCreate(savedInstanceState);



    }
    public void processFinish(String result)
    {
        numberOfCourses=0;
        Log.d("reached","process finish of fetching courses teaching");
        Log.d("string is",result);
        try
        {
            JSONObject jsonObj = new JSONObject(result);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String facultyid=c.getString(TAG_FacultyID);
                String coursesid = c.getString(TAG_COURSEID);
                String coursesname = c.getString(TAG_COURSENAME);

                numberOfCourses += 1;
                courseId[numberOfCourses-1]=coursesid;
                courseName[numberOfCourses-1]=coursesname;
                //Toast.makeText(this,courseId[0]+courseName[0],Toast.LENGTH_LONG).show();
                Log.d("checking",courseId[i]+" "+courseName[i]);
            }
        }
        catch(Exception e)
        {
            Log.d("Error","Exception in json parsing"+e);

        }



        setContentView(R.layout.activity_faculty_home);




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
        Log.d("reached","process finish for indivigilation details");
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
                NoofDuty+=1;
                examinfoarr[i][0]=courseid;
                examinfoarr[i][1]=coursename;
                examinfoarr[i][2]=date;
                examinfoarr[i][3]=time;
                examinfoarr[i][4]=venue;

                str=examinfoarr[i][0]+" "+examinfoarr[i][1]+" "+examinfoarr[i][2]+" "+examinfoarr[i][3]+" "+examinfoarr[i][4];
                Toast.makeText(this,str,Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Log.d("Error","in json parsing");
            Toast.makeText(this,str+"Exception in json parsing"+e,Toast.LENGTH_LONG).show();
        }
    }
    public void DataRetrieved(String result)
    {
        Log.d("reached","data retrieved for course exam info");
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
                String sem=c.getString("semester");
               numberOfExams+=1;
                courseexam[i][0]=courseid;
                courseexam[i][1]=coursename;
                courseexam[i][2]=date;
                courseexam[i][3]=time;
                courseexam[i][4]=venue;
                courseexam[i][5]=sem;
                str=courseexam[i][0]+" "+courseexam[i][1]+" "+courseexam[i][2]+" "+courseexam[i][3]+" "+courseexam[i][4]+courseexam[i][5];
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
        getMenuInflater().inflate(R.menu.menu_faculty_home, menu);
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
    public static class PlaceholderFragment extends Fragment  {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        adddelcourse obj;

        private static final String ARG_SECTION_NUMBER = "section_number";

        final int PICKFILE_RESULT_CODE = 1;
        String FilePath="";
        UploadFileAsync u;

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
                case 1: rootView = inflater.inflate(R.layout.fragment_faculty_home_info, container, false);
                    facultyInfoFragment(rootView);
                    break;
                case 2: rootView = inflater.inflate(R.layout.fragment_faculty_home_exam, container, false);
                    facultyExamFragment(rootView);
                    break;
                case 3: rootView = inflater.inflate(R.layout.fragment_faculty_home_info, container, false);
                    facultyEventsFragment(rootView);
                    break;
                default: rootView = inflater.inflate(R.layout.fragment_faculty_home_info, container, false);
            }
            return rootView;
        }

        public void facultyInfoFragment(View rootView){

            TextView facultyInfoName = (TextView)rootView.findViewById(R.id.faculty_info_name_view);
            facultyInfoName.setText(name);

            TextView facultyInfoID = (TextView)rootView.findViewById(R.id.faculty_info_ID_view);
            facultyInfoID.setText(id);

            TextView facultyInfoDept = (TextView)rootView.findViewById(R.id.faculty_info_dept_view);
            facultyInfoDept.setText(department);

            Button facultyInfoCaddButton = (Button)rootView.findViewById(R.id.faculty_info_cADD_button);
            Button facultyInfoCdeleteButton = (Button)rootView.findViewById(R.id.faculty_info_cDelete_button);

            final EditText facultyInfoCIDField = (EditText)rootView.findViewById(R.id.faculty_info_cID_field);
            final EditText facultyInfoCnameField = (EditText)rootView.findViewById(R.id.faculty_info_cname_field);

            if (facultyInfoCIDField.getText().toString() != null && facultyInfoCnameField.getText().toString() != null){
                facultyInfoCaddButton.setEnabled(true);
                facultyInfoCdeleteButton.setEnabled(true);
            }
            else{
                facultyInfoCaddButton.setEnabled(false);
                facultyInfoCdeleteButton.setEnabled(false);
            }

            facultyInfoCaddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  obj=  new adddelcourse("add",facultyInfoCIDField.getText().toString(),facultyInfoCnameField.getText().toString(),id);
                    obj.execute();
                }
            });

            facultyInfoCdeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obj=  new adddelcourse("del",facultyInfoCIDField.getText().toString(),facultyInfoCnameField.getText().toString(),id);
                    obj.execute();

                }
            });

            LinearLayout facultyInfoLayout = (LinearLayout)rootView.findViewById(R.id.faculty_info_layout);

                Log.d("courses fragment","reaching here");
            TextView coursesTextView[] = new TextView[numberOfCourses];

            for (int i = 0; i < numberOfCourses; i++){
                Log.d("checking",courseId[i]+" "+courseName[i]);
                coursesTextView[i] = new TextView(getContext());
                coursesTextView[i].setText(courseId[i] + "  " + courseName[i]); //enter the course ID and course name returned from database
                coursesTextView[i].setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                coursesTextView[i].setTextSize(18.0f);
                facultyInfoLayout.addView(coursesTextView[i]);
            }
            Log.d("check","done");
            return;
        }

        public void facultyExamFragment(View rootView){

            Button uploadExamScheduleButton = (Button)rootView.findViewById(R.id.faculty_exam_upload_button);
            uploadExamScheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("*/*");
                    startActivityForResult(intent,PICKFILE_RESULT_CODE);
                }
            });

            Button uploadFacultyTimetableButton = (Button)rootView.findViewById(R.id.faculty_self_timetable_upload_button);
            uploadFacultyTimetableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("*/*");
                    startActivityForResult(intent,PICKFILE_RESULT_CODE);
                }
            });

            facultyExamRecyclerView = (RecyclerView)rootView.findViewById(R.id.faculty_exam_recyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

            facultyExamRecyclerView.setLayoutManager(layoutManager);
            facultyExamRecyclerView.setItemAnimator(new DefaultItemAnimator());

            Button viewInvigilationScheduleButton = (Button)rootView.findViewById(R.id.faculty_view_invigilation_schedule_button);
            viewInvigilationScheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isArrayListPopulated){
                        invigilationScheduleArrayList.clear();
                        examScheduleArrayList.clear();
                    }

                    InvigilationScheduleAdapter invigilationScheduleAdapter;
                    InvigilationSchedule invigilationSchedule;

                    invigilationScheduleAdapter = new InvigilationScheduleAdapter(invigilationScheduleArrayList);
                    facultyExamRecyclerView.setAdapter(invigilationScheduleAdapter);

                    int i = 0;
                    while (examinfoarr[i][0] != null){
                        invigilationSchedule = new InvigilationSchedule(examinfoarr[i][1], examinfoarr[i][0], examinfoarr[i][2], examinfoarr[i][3], examinfoarr[i][4]);
                        invigilationScheduleArrayList.add(invigilationSchedule);
                        i++;
                    }

                    isArrayListPopulated = true;
                }
            });

            Button viewCourseScheduleButton = (Button)rootView.findViewById(R.id.faculty_view_course_schedule_button);
            viewCourseScheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isArrayListPopulated){
                        examScheduleArrayList.clear();
                        invigilationScheduleArrayList.clear();
                    }
                    ExamScheduleAdapter examScheduleAdapter;
                    ExamSchedule examSchedule;


                    examScheduleAdapter = new ExamScheduleAdapter(examScheduleArrayList);
                    facultyExamRecyclerView.setAdapter(examScheduleAdapter);

                    int i =0;
                    while (courseexam[i][0] != null){
                        examSchedule = new ExamSchedule(courseexam[i][1], courseexam[i][0], courseexam[i][2], courseexam[i][3], courseexam[i][4], courseexam[i][5]);
                        examScheduleArrayList.add(examSchedule);
                        i++;
                    }

                    isArrayListPopulated = true;
                }
            });


        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            switch(requestCode){
                case PICKFILE_RESULT_CODE:
                    if(resultCode==RESULT_OK){

                        FilePath = data.getData().getPath();
                        FilePath=FilePath.split(":")[1];
                        u=new UploadFileAsync(FilePath);
                        u.execute("");
                    }
                    break;
            }
        }

        public static void facultyEventsFragment(View rootView){

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


class UploadFileAsync extends AsyncTask<String, Void, String> {

    String FilePath;

    UploadFileAsync(String FilePath){
        this.FilePath = FilePath;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String sourceFileUri ="/sdcard/"+FilePath;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (sourceFile.isFile()) {

                try {
                    String upLoadServerUri = "http://"+"10.162.4.116"+"/FileUpload.php";
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(upLoadServerUri);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""+ sourceFileUri + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens+ lineEnd);


                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    if (serverResponseCode == 200) {


                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {


                    e.printStackTrace();

                }

                // dialog.dismiss();

            } // End else block


        } catch (Exception ex) {
            // dialog.dismiss();

            ex.printStackTrace();
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}



class DataFetching extends AsyncTask<String, Void, String> {
    Response delegate = null;
    InputStream is = null;

    String result = null;
    String text = "";
    Context c;
    String id;
    String user;
    String password, receivedid, receivedpassword, receivedname, receiveddepartment, receivedsemester, receivedcourse;
    StringBuilder sb = new StringBuilder();
    String line = null;

    DataFetching(String id) {
        super();
        this.id = id;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            Log.d("checking", "reached do in background");
            String link = "http://"+"10.162.4.116"+"/coursesfetching.php";
            String data = URLEncoder.encode("id", "UTF-8")
                    + "=" + URLEncoder.encode(id, "UTF-8");
            Log.d("encoded", data);
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            Log.d("checking", "success in writing");
            BufferedReader reader = null;

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }

            text = sb.toString();
            Log.d("checking", text);
            wr.close();
            reader.close();
        } catch (Exception e) {
            text = text + "Exception";
            Log.d("checking", text+e);
        }


        return text;
    }


    @Override
    protected void onPostExecute(String result) {
        Log.d("checking","text");
        super.onPostExecute(result);
        delegate.processFinish(result);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
interface Response {
    void processFinish(String result);
}


class adddelcourse extends AsyncTask<String, Void, String> {
    InputStream is = null;

    String result = null;
    String text = "";
    Context c;
    String id;
    String name;
    String work;
    String facultyId;
    String password,receivedid,receivedpassword,receivedname,receiveddepartment,receivedsemester,receivedcourse;
    StringBuilder sb = new StringBuilder();
    String line = null;
    adddelcourse(String l,String id,String name,String facid) {
        super();
        this.work=l;
        this.id=id;
        this.name=name;
        this.facultyId=facid;
    }

    @Override
    protected String doInBackground(String... arg0) {

        try {
            Log.d("checking", "reached do in background"+work);String link="";
            if(work.equals("add"))
                link = "http://"+"10.162.4.116"+"/courses.php";
            if(work.equals("del"))
                link="http://"+"10.162.4.116"+"/deletecourses.php";
            String data = URLEncoder.encode("courseId", "UTF-8")
                    + "=" + URLEncoder.encode(id, "UTF-8");

            data += "&" + URLEncoder.encode("courseName", "UTF-8") + "="
                    + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("facultyId", "UTF-8") + "="
                    + URLEncoder.encode(facultyId, "UTF-8");
            Log.d("encoded", data);
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            Log.d("checking", "success in writing");
            BufferedReader reader = null;

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }

            text = sb.toString();
            Log.d("checking", text);
            wr.close();
            reader.close();
        } catch (Exception e) {
            text = text + "Exception";
            Log.d("checking", text+e);
        }





        return text;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);String response="";
            try {


                JSONObject c = new JSONObject(result);
                Log.d("problem not with", "object creation");
                response = c.getString("response");
            }
            catch(Exception e) {
                Log.d("Error","in adding courses"+e);
            }
            Log.d("Server Response",result);
        }



    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}