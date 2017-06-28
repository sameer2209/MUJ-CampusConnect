package com.example.manushrivastava.muj_campusconnect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by admin on 6/26/2017.
 */
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
                link = "http://"+"192.168.43.220"+"/signupstudent.php";
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
                link = "http://"+LoginActivity.ip+"/signupfaculty.php";
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