package com.example.manushrivastava.muj_campusconnect;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class OtpVerification extends AppCompatActivity implements ServerRespone {
    EditText meditText;
    String otp;
    String generatedotp="",mailid="",id="",name="",department="";
    GetOtp s;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Bundle b=getIntent().getExtras();
        id=b.getString("id");
        name=b.getString("name");
        department=b.getString("department");

    }
    public void verifying(android.view.View v)
    {
        meditText = (EditText) findViewById(R.id.otpverify_enteredotp);
        otp = meditText.getText().toString();
        s=new GetOtp(otp);
        s.delegate=this;
        s.execute("");
    }

        public void ServerRespons(String result)
        {
            try {


                JSONObject c = new JSONObject(result);
                Log.d("problem not with", "object creation");
                generatedotp= c.getString("response");
                if(generatedotp.equals("Match"))
                {
                    Intent i=new Intent(this,FacultyHomeActivity.class);
                    i.putExtra("id",id);
                    i.putExtra("name",name);
                    i.putExtra("department",department);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(this,"Wrong otp",Toast.LENGTH_LONG).show();
                    Log.d("checking","Wrong otp");
                }
            }
            catch(Exception e)
            {
                Log.d("Error","in otp verification");
                Toast.makeText(this,"Wrong Otp",Toast.LENGTH_LONG).show();
            }

    }


}


 class GetOtp extends AsyncTask<String, Void, String> {

    ServerRespone delegate = null;
    String text = "",facultyId,facultyName,otp="";
    StringBuilder sb = new StringBuilder();
    String line = null;
    String type="";
    GetOtp(String i) {
        super();

        this.otp = i;
    }


    @Override
    protected String doInBackground(String... arg0) {
        try {
            Log.d("checking", "reached do in background for fetching indivigilation details");
            String link = "http://10.162.4.116/otpverifying.php";
            String data = URLEncoder.encode("otp", "UTF-8")
                    + "=" + URLEncoder.encode(otp, "UTF-8");
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
        delegate.ServerRespons(result);
    }
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}
interface ServerRespone {
    void ServerRespons(String result);
}



