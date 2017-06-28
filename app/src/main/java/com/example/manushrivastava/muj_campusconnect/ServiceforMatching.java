package com.example.manushrivastava.muj_campusconnect;

/**
 * Created by admin on 6/22/2017.
 */
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.provider.BaseColumns;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceforMatching extends Service implements ServerResponse{

    public ServiceforMatching() {

    }
    private static final String TAG_IRESULTS="result";
    private static final String TAG_ICOURSEID = "courseId";
    private static final String TAG_ICOURSENAME = "courseName";
    private static final String TAG_DATE ="date";
    private static final String TAG_TIME ="time";
    private static final String TAG_VENUE ="venue";
    private static final String TAG_SEMESTER ="semester";
    private static final String TAG_DEPARTMENT="department";
    private static final String TAG_COURSE ="course";
    private static final String TAG_INDIVIGILATOR ="indivigilator";
    public static String createQ="CREATE TABLE if not exists exam"+ "("+ BaseColumns._ID+ " integer primary key autoincrement, "+TAG_ICOURSEID+" text, "+TAG_ICOURSENAME+" text, "+TAG_DATE+" text, "+ TAG_TIME+" text, "+TAG_VENUE+" text, "+TAG_SEMESTER+" text, "+TAG_DEPARTMENT+" text, "+TAG_COURSE+" text, "+TAG_INDIVIGILATOR+" text"+")";
   int NoofDuty=0;
    JSONArray peoples = null;
    IndivigilationDetails f;
    String courseId="",courseName="",time="",venue="",date="",indivigilatorId="";
    /** indicates how to behave if the service is killed */
    int mStartMode;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;
    SqliteDatabase create;
    SQLiteDatabase db;
    String semester,course,department;
    String examinfoarr[][]=new String[20][6];
    static String ip="null";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mBinder;
    }

    @Override
    public void onCreate() {
        create=new SqliteDatabase(getBaseContext());
        ip=getBaseContext().getString(R.string.oct1);
        Toast.makeText(this, " Service for matching created ", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         semester = intent.getStringExtra("semester");
        course = intent.getStringExtra("course");
         department = intent.getStringExtra("department");
        Object[] objectArray = (Object[]) intent.getExtras().getSerializable("key_array_array");

        f=new IndivigilationDetails(semester,department,course,"Student","none",ip);
        f.delegate=this;
        f.execute("");
        return mStartMode;

    }
    public void ServerResponds(String result) {
        NoofDuty = 0;
        Log.d("reached", "process finish");
        Log.d("checking for similarity", result);
        String str = "";
        try {
            JSONObject jsonObj = new JSONObject(result);
            peoples = jsonObj.getJSONArray(TAG_IRESULTS);
            for (int i = 0; i < peoples.length(); i++) {
                Log.d("array","works till here");
                JSONObject c = peoples.getJSONObject(i);
                String courseid = c.getString(TAG_ICOURSEID);
                String coursename = c.getString(TAG_ICOURSENAME);
                String date = c.getString(TAG_DATE);
                String time = c.getString(TAG_TIME);
                String venue = c.getString(TAG_VENUE);
                String indivigilator = c.getString(TAG_INDIVIGILATOR);
                NoofDuty += 1;
                examinfoarr[i][0] = courseid;
                examinfoarr[i][1] = coursename;
                examinfoarr[i][2] = date;
                examinfoarr[i][3] = time;
                examinfoarr[i][4] = venue;
                examinfoarr[i][5] = indivigilator;
                str = examinfoarr[i][0] + " " + examinfoarr[i][1] + " " + examinfoarr[i][2] + " " + examinfoarr[i][3] + " " + examinfoarr[i][4] + " " + examinfoarr[i][5];

            }

        } catch (JSONException e) {
            Log.d("Error", "in json parsing");
        }
        Log.d("result ",str);
        Cursor cursor = create.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", "exam"});
        if (cursor != null) {
            int count = cursor.getCount();
            Log.d("result", count + "table existing outcome");
            if (count == 0)
                createTable();

            else
                checkMatching();
        }
        else
            createTable();
    }
    void createTable()
    {
        Toast.makeText(this,"creating table",Toast.LENGTH_LONG).show();
        Log.d("creating","in function create table");
        db = create.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS exam" );
       db.execSQL(createQ);
        for (int i = 0; i < NoofDuty; i++) {
            ContentValues values = new ContentValues();
            values.put(TAG_ICOURSEID, examinfoarr[i][0]);
            values.put(TAG_ICOURSENAME,examinfoarr[i][1]);
            values.put(TAG_DATE, examinfoarr[i][2]);
            values.put(TAG_TIME,examinfoarr[i][3]);
            values.put(TAG_VENUE, examinfoarr[i][4]);
            values.put(TAG_SEMESTER,semester);
            values.put(TAG_DEPARTMENT, department);
            values.put(TAG_COURSE, course);
            values.put(TAG_INDIVIGILATOR,examinfoarr[i][5]);

            db.insert("exam", null, values);
        }
        Log.d("createTable()","data inserted and table created");
    }
    void checkMatching()
    {int i=0;int temp=0;
        Log.d("Matching","in table checkMatching");
        Toast.makeText(this,"matching table",Toast.LENGTH_LONG).show();
        String a[]=new String[]{TAG_ICOURSEID,TAG_ICOURSENAME,TAG_DATE,TAG_TIME,TAG_VENUE,TAG_INDIVIGILATOR};
        SQLiteDatabase db=create.getReadableDatabase();
        Cursor c=db.query("exam",a,null,null,null,null,null,null);
        while(c.moveToNext()) {
            courseId=c.getString(c.getColumnIndexOrThrow(TAG_ICOURSEID));
            courseName=c.getString(c.getColumnIndexOrThrow(TAG_ICOURSENAME));
            date=c.getString(c.getColumnIndexOrThrow(TAG_DATE));
            time=c.getString(c.getColumnIndexOrThrow(TAG_TIME));
            venue=c.getString(c.getColumnIndexOrThrow(TAG_VENUE));
            indivigilatorId=c.getString(c.getColumnIndexOrThrow(TAG_INDIVIGILATOR));
            if(courseId.equals(examinfoarr[i][0]) && courseName.equals(examinfoarr[i][1]) && date.equals(examinfoarr[i][2]) && time.equals(examinfoarr[i][3]) && venue.equals(examinfoarr[i][4])&& indivigilatorId.equals(examinfoarr[i][5]))
            {

            }
            else
            {
                db.execSQL("delete from exam");
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.icon)
                                .setContentTitle("Update in exam scheme")
                                .setContentText("There is a change in the exam scheme. Please check!").setAutoCancel(true);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(2, mBuilder.build());
                temp=1;
                break;
            }
            i++;
        }

        if(i!=NoofDuty)createTable();
        else
        Log.d("Match successful","same");

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Servics Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}
