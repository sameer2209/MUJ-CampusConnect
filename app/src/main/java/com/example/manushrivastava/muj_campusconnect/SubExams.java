package com.example.manushrivastava.muj_campusconnect;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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

/**
 * Created by admin on 6/20/2017.
 */

public class SubExams extends AsyncTask<String, Void, String> {

    SubExamInterface delegate = null;
    String text = "",facultyId,facultyName,course;
    StringBuilder sb = new StringBuilder();
    String line = null;
    String type="";
    String details;
    SubExams(String i, String m,String c,String type,String r) {
        super();
        this.facultyId = i;
        this.facultyName = m;
        this.type=type;
        this.course = c;
        this.details=r;
    }


    @Override
    protected String doInBackground(String... arg0) {
        try {
            Log.d("checking", "reached do in background for fetching indivigilation details");
            String link = "http://10.162.4.116/Examfetching.php";
            String data = URLEncoder.encode("facultyId", "UTF-8")
                    + "=" + URLEncoder.encode(facultyId, "UTF-8");

            data += "&" + URLEncoder.encode("facultyName", "UTF-8") + "="
                    + URLEncoder.encode(facultyName, "UTF-8");
            data += "&" + URLEncoder.encode("course", "UTF-8") + "="
                    + URLEncoder.encode(course, "UTF-8");
            data += "&" + URLEncoder.encode("type", "UTF-8") + "="
                    + URLEncoder.encode(type, "UTF-8");
            data += "&" + URLEncoder.encode("exam", "UTF-8") + "="
                    + URLEncoder.encode(details, "UTF-8");
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
        super.onPostExecute(result);
        delegate.DataRetrieved(result);
    }
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}