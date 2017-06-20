package com.example.manushrivastava.muj_campusconnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity implements AsyncResponse{
    private RadioGroup mradioGroup;
    private RadioButton mradioButton;
    EditText meditText;
    String loginid,password,user;

    SignInActivity s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void checking(android.view.View v) throws Exception {

        mradioGroup = (RadioGroup) findViewById(R.id.login_radio);
        int selectedId = mradioGroup.getCheckedRadioButtonId();
        mradioButton = (RadioButton) findViewById(selectedId);
        user=mradioButton.getText().toString();
        meditText = (EditText) findViewById(R.id.login_inputid);
         loginid = meditText.getText().toString();
        meditText = (EditText) findViewById(R.id.login_inputpassword);
         password = meditText.getText().toString();

        String str = loginid + password;
        s = new SignInActivity(loginid, password,user);
        s.delegate=this;
        s.execute("");

    }

    @Override
    public void processFinish(String receivedid,String receivedpassword,String receivedname,String receiveddepartment,String receivedsemester,String receivedcourse,String result){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.


        if(user.equals("Student")) {
            //code for sending to studenthome page
/*
            if (receivedid.equals(loginid)) {
                Intent i = new Intent(this, UserHomeActivity.class);
                i.putExtra("id", receivedid);
                i.putExtra("name", receivedname);
                i.putExtra("department", receiveddepartment);
                i.putExtra("semester", receivedsemester);
                i.putExtra("course",receivedcourse);
                startActivity(i);
            } else if (receivedid.equals("no tuples"))
                Toast.makeText(this, "Wrong id or password", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Exception" + result, Toast.LENGTH_LONG).show();
                */

        }
        if(user.equals("Faculty")) {

            //code for sending to faculty home page

            if (user.equals("Faculty")) {

                if (receivedid.equals(loginid)) {
                    Intent i = new Intent(this, FacultyHomeActivity.class);
                    startActivity(i);
                } else if (receivedid.equals("no tuples"))
                    Toast.makeText(this, "Wrong id or password", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Exception" + result, Toast.LENGTH_LONG).show();

            }
        }



    }


}
   class SignInActivity extends AsyncTask<String, Void, String> {
        AsyncResponse delegate = null;
        InputStream is = null;

        String result = null;
        String text = "";
        Context c;
        String id;
       String user;
        String password,receivedid,receivedpassword,receivedname,receiveddepartment,receivedsemester,receivedcourse;
        StringBuilder sb = new StringBuilder();
        String line = null;
        SignInActivity(String i, String m,String l) {
            super();
            this.user=l;
            this.id = i;
            this.password = m;
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                Log.d("checking", "reached do in background");
                String link = "http://192.168.43.220/TextUpload.php";
                String data = URLEncoder.encode("id", "UTF-8")
                        + "=" + URLEncoder.encode(id, "UTF-8");

                data += "&" + URLEncoder.encode("pswd", "UTF-8") + "="
                        + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("user", "UTF-8") + "="
                        + URLEncoder.encode(user, "UTF-8");
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
                Log.d("checking", text);
            }





            return text;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(user.equals("Student")) {

                try {


                    JSONObject c = new JSONObject(result);
                    Log.d("problem not with", "object creation");
                    receivedid = c.getString("id");
                    receivedpassword = c.getString("password");
                    receivedname = c.getString("studentname");
                    receiveddepartment = c.getString("department");
                    receivedsemester = c.getString("semester");
                    receivedcourse = c.getString("course");
                } catch (JSONException e) {
                    Log.d("Error", "Exception");
                    receivedid = "exception in parsing";
                }
                delegate.processFinish(receivedid,receivedpassword,receivedname,receiveddepartment,receivedsemester,receivedcourse,result);
            }
            if(user.equals("Faculty"))
            {
                try {


                    JSONObject c = new JSONObject(result);
                    Log.d("problem not with", "object creation");
                    receivedid = c.getString("id");
                    receivedpassword = c.getString("password");
                    receivedname = c.getString("facultyname");
                    receiveddepartment = c.getString("department");
                } catch (JSONException e) {
                    Log.d("Error", "Exception");
                    receivedid = "exception in parsing";
                }
                delegate.processFinish(receivedid,receivedpassword,receivedname,receiveddepartment,"no semester","no course",result);
            }

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    interface AsyncResponse {
    void processFinish(String receivedid,String receivedpassword,String receivedname,String receiveddepartment,String receivedsemester,String receivedcourse,String result);
}







