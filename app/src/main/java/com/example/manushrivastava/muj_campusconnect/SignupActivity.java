package com.example.manushrivastava.muj_campusconnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignupActivity extends AppCompatActivity {

    EditText mName;
    EditText mID;
    EditText mEmail;
    EditText mCourse;
    EditText mDepartment;
    EditText mSemester;
    EditText mPassword;
    RadioButton mStudentButton;
    RadioButton mFacultyButton;

    Button mSubmitButton;

    static String courses = "";
    String user="";

    SendingDataToServer s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        mName = (EditText)findViewById(R.id.signup_nameField);
        mID = (EditText)findViewById(R.id.signup_IDField);
        mEmail = (EditText)findViewById(R.id.signup_emailField);
        mCourse = (EditText)findViewById(R.id.signup_courseField);
        mDepartment = (EditText)findViewById(R.id.signup_deptField);
        mSemester = (EditText)findViewById(R.id.signup_semesterField);
        mPassword = (EditText)findViewById(R.id.signup_passwordField);

        mStudentButton = (RadioButton)findViewById(R.id.signup_studentRadioBtn);
        mFacultyButton = (RadioButton)findViewById(R.id.signup_facultyRadioBtn);


        mSubmitButton = (Button)findViewById(R.id.signup_submitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStudentButton.isChecked()){
                    user="Student";
                    s = new SendingDataToServer(mName.getText().toString(),mID.getText().toString(),mCourse.getText().toString(),mDepartment.getText().toString(),mSemester.getText().toString(),mPassword.getText().toString(),user);
                    s.execute();
                    startStudentHomeActivity();
                }

                else if (mFacultyButton.isChecked()){
                    user="Faculty";
                    s = new SendingDataToServer(mName.getText().toString(),mID.getText().toString(),mCourse.getText().toString(),mDepartment.getText().toString(),mSemester.getText().toString(),mPassword.getText().toString(),user);
                    s.execute();
                    startFacultyHomeActivity();
                }
            }
        });
    }

    public void startStudentHomeActivity() {
        boolean flag = false;
        if (mName.getText().toString() == null) {
            mName.setError("Name field is empty");
            flag = true;
        }
        if (mID.getText().length() == 0) {
            mID.setError("Invalid ID");
            flag = true;
        }
        if (mPassword.getText().length() < 4) {
            mPassword.setError("Password should be atleast 4 characters long");
            flag = true;
        }
        if (mCourse.getText().length() == 0) {
            mCourse.setError("Course field is required");
            flag = true;
        }
        if (mDepartment.getText().length() == 0) {
            mDepartment.setError("Department field is required");
            flag = true;
        }
        if (Integer.parseInt(mSemester.getText().toString()) < 1 && Integer.parseInt(mDepartment.getText().toString()) > 8) {
            mDepartment.setError("Invalid semester");
            flag = true;
        }
        if (!flag){
            Intent intent = new Intent(this, StudentHomeActivity.class);
            startActivity(intent);
        }
    }
    public void startFacultyHomeActivity() {
        boolean flag = false;
        if (mName.getText().toString() == null) {
            mName.setError("Name field is empty");
            flag = true;
        }
        if (mID.getText().length() == 0) {
            mID.setError("Invalid ID");
            flag = true;
        }
        if (!mEmail.getText().toString().contains("@jaipur.manipal.edu")) {
            mEmail.setError("manipal.edu email id is required");
            flag = true;
        }
        if (mPassword.getText().length() < 4) {
            mPassword.setError("Password should be atleast 4 characters long");
            flag = true;
        }
        if (mDepartment.getText().length() == 0) {
            mDepartment.setError("Department field is required");
            flag = true;
        }
        if (!flag){
            Intent intent = new Intent(this, FacultyHomeActivity.class);
            startActivity(intent);
        }
    }
}

class SendingDataToServer extends AsyncTask<String, Void, String> {
    InputStream is = null;

    String result = null;
    String text = "";
    Context c;
    String data;
    String link;
    String ID;
    String work;
    String password,receivedid,receivedpassword,receivedname,receiveddepartment,receivedsemester,receivedcourse;
    String name;
    String course;
    String department;
    String semester;
    StringBuilder sb = new StringBuilder();
    String line = null;
    SendingDataToServer(String name, String ID,String course, String department, String semester, String password, String user) {
        this.name = name;
        this.work=user;
        this.ID = ID;
        this.password = password;
        this.course = course;
        this.department = department;
        this.semester = semester;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            Log.d("user",work);
            Log.d("checking", "reached do in background");
            Log.d("user","tnhzfh");
            if(work.equals("Student")) {
                link = "http://10.162.4.116/signupstudent.php";
                data = URLEncoder.encode("id", "UTF-8")
                        + "=" + URLEncoder.encode(ID, "UTF-8");

                data += "&" + URLEncoder.encode("pswd", "UTF-8") + "="
                        + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8") + "="
                        + URLEncoder.encode(name, "UTF-8");
                data += "&" + URLEncoder.encode("semester", "UTF-8") + "="
                        + URLEncoder.encode(semester, "UTF-8");
                data += "&" + URLEncoder.encode("department", "UTF-8") + "="
                        + URLEncoder.encode(department, "UTF-8");
                data += "&" + URLEncoder.encode("course", "UTF-8") + "="
                        + URLEncoder.encode(course, "UTF-8");
            }
                if(work.equals("Faculty"))
                {
                    Log.d("Checking","in faculty if");
                    link = "http://10.162.4.116/signupfaculty.php";
                    Log.d("Checking","in faculty if link working");
                    data = URLEncoder.encode("id", "UTF-8")
                            + "=" + URLEncoder.encode(ID, "UTF-8");

                    data += "&" + URLEncoder.encode("pswd", "UTF-8") + "="
                            + URLEncoder.encode(password, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "="
                            + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("department", "UTF-8") + "="
                            + URLEncoder.encode(department, "UTF-8");
                }


            Log.d("encoded", data);
            URL url = new URL(link);
            Log.d("checking","url made");
            URLConnection conn = url.openConnection();
            Log.d("checking","connection opened");
            conn.setDoOutput(true);
            Log.d("checking","url made and output true");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            Log.d("checking","url made");
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


    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

