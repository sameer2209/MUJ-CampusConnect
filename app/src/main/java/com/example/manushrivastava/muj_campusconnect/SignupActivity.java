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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignupActivity extends AppCompatActivity implements RespondingOtp{

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
    OtpCreation v;
    static String ip="null";
    static String courses = "";
    String user="";

    SendingDataToServer s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ip=getBaseContext().getString(R.string.oct1);
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
                    startStudentHomeActivity();
                }

                else if (mFacultyButton.isChecked()){
                    user="Faculty";
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
            mID.setError("ID field is empty");
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
            Log.d("reaching","without errors");
            s = new SendingDataToServer(mName.getText().toString(),mID.getText().toString(),mCourse.getText().toString(),mDepartment.getText().toString(),mSemester.getText().toString(),mPassword.getText().toString(),user);
            s.execute();
            Intent intent = new Intent(this, StudentHomeActivity.class);
            intent.putExtra("name", mName.getText().toString());
            intent.putExtra("id", mID.getText().toString());
            intent.putExtra("course", mCourse.getText().toString());
            intent.putExtra("department", mDepartment.getText().toString());
            intent.putExtra("semester", mSemester.getText().toString());
            intent.putExtra("entry","Signup");
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
            mID.setError("ID field is required");
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
            v=new OtpCreation(mEmail.getText().toString());
            v.delegate = this;
            v.execute("");
            Intent intent = new Intent(this, OtpVerification.class);
            intent.putExtra("id",mID.getText().toString());
            intent.putExtra("name",mName.getText().toString());
            intent.putExtra("department",mDepartment.getText().toString());
            intent.putExtra("password",mPassword.getText().toString());
            startActivity(intent);

        }
    }
    public void ServerResponds(String result)
    {





    }
}



class OtpCreation extends AsyncTask<String, Void, String> {

    RespondingOtp delegate = null;
    String text = "",facultymailId,facultyName;
    StringBuilder sb = new StringBuilder();
    String line = null;
    String type="";
    OtpCreation(String i) {
        super();

        this.facultymailId = i;
    }


    @Override
    protected String doInBackground(String... arg0) {
        try {
            Log.d("checking", "reached do in background for fetching indivigilation details");
            String link = "http://"+SignupActivity.ip+"/SendMail.php";
            String data = URLEncoder.encode("facultymailId", "UTF-8")
                    + "=" + URLEncoder.encode(facultymailId, "UTF-8");
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
            text = text + "Exception in sending mail";
            Log.d("checking", text+e);
        }





        return text;


    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.ServerResponds(result);
    }
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}
 interface RespondingOtp {
    void ServerResponds(String result);
}



