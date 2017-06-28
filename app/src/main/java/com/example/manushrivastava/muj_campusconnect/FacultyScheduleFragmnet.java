package com.example.manushrivastava.muj_campusconnect;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class FacultyScheduleFragmnet extends Fragment {

    String facultyScheduleArray[][] = new String[10][2];
    int dataPresent=0,done=0;
    TimeTableFetch timetable;
    String facultyName,department,day;
    View view;

    public FacultyScheduleFragmnet() {
        // Required empty public constructor
        Log.d("position","in on create");
    }


    public static FacultyScheduleFragmnet newInstance(String param1, String param2) {
        FacultyScheduleFragmnet fragment = new FacultyScheduleFragmnet();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        Log.d("position","in on create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("position","in on create view");
        view = inflater.inflate(R.layout.fragment_faculty_schedule_fragmnet, container, false);

        facultyScheduleArray=(String[][])getArguments().getSerializable("key_array_array");

        TextView displayFacultyName = (TextView)view.findViewById(R.id.faculty_name);
        displayFacultyName.setText(facultyName);
        TextView displayDate = (TextView)view.findViewById(R.id.faculty_day);
        displayDate.setText(day);

        RecyclerView facultyScheduleRecyclerView = (RecyclerView)view.findViewById(R.id.faculty_schedule_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        facultyScheduleRecyclerView.setLayoutManager(layoutManager);
        facultyScheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        FacultySchedule facultySchedule;
        FacultyScheduleAdapter facultyScheduleAdapter;
        ArrayList<FacultySchedule> facultyScheduleArrayList = new ArrayList<>();

        facultyScheduleAdapter = new FacultyScheduleAdapter(facultyScheduleArrayList);
        facultyScheduleRecyclerView.setAdapter(facultyScheduleAdapter);

        int i = 0;
        while (facultyScheduleArray[i][0] != null && dataPresent!=0){
            facultySchedule = new FacultySchedule(facultyScheduleArray[i][0], facultyScheduleArray[i][1]);
            facultyScheduleArrayList.add(facultySchedule);
            i++;
        }
        done=1;

        Log.d("check","coming out of locking while loop");
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
class TimeTableFetch extends AsyncTask<String, Void, String> {
     ScheduleFetch delegate = null;
    InputStream is = null;

    String result = null;
    String text = "";
    Context c;
    String id;
    String user,facultyName,department,day;
    String password,receivedid,receivedpassword,receivedname,receiveddepartment,receivedsemester,receivedcourse;
    StringBuilder sb = new StringBuilder();
    String line = null;
    TimeTableFetch(String i, String m,String l) {
        super();
        this.facultyName=i;
        this.department = m;
        this.day = l;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            Log.d("checking", "reached do in background");
            String link = "http://"+"10.162.4.116"+"/TimeTablefetch.php";
            String data = URLEncoder.encode("name", "UTF-8")
                    + "=" + URLEncoder.encode(facultyName, "UTF-8");

            data += "&" + URLEncoder.encode("department", "UTF-8") + "="
                    + URLEncoder.encode(department, "UTF-8");
            data += "&" + URLEncoder.encode("day", "UTF-8") + "="
                    + URLEncoder.encode(day, "UTF-8");
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
            text = text + "Exception"+e;
            Log.d("checking", text);
        }





        return text;
    }


    @Override
    protected void onPostExecute(String result) {
        Log.d("on post execute",result);
        super.onPostExecute(result);
        delegate.Retrieved(result);

    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
 interface ScheduleFetch {
    void Retrieved(String result);

}

