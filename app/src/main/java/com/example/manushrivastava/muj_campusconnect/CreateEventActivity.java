package com.example.manushrivastava.muj_campusconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateEventActivity extends AppCompatActivity {

    String mEventName;
    String mEventID;
    String mEventCategory;
    String mEventSubCategory;
    String mEventDate;
    String mEventTime;
    String mEventVenue;
    String mEventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        EditText eventName = (EditText)findViewById(R.id.student_event_name);
        EditText eventID = (EditText)findViewById(R.id.student_event_id);
        EditText eventCategory = (EditText)findViewById(R.id.student_event_category);
        EditText eventSubCategory = (EditText)findViewById(R.id.student_event_sub_category);
        EditText eventDate = (EditText)findViewById(R.id.student_event_date);
        EditText eventTime = (EditText)findViewById(R.id.student_event_time);
        EditText eventVenue = (EditText)findViewById(R.id.student_event_venue);
        EditText eventDescription = (EditText)findViewById(R.id.student_event_description);
        Button createEventButton = (Button)findViewById(R.id.student_create_event_button);

        mEventName = eventName.getText().toString();
        mEventID = eventID.getText().toString();
        mEventCategory = eventCategory.getText().toString();
        mEventSubCategory = eventSubCategory.getText().toString();
        mEventDate = eventDate.getText().toString();
        mEventTime = eventTime.getText().toString();
        mEventVenue = eventVenue.getText().toString();
        mEventDescription = eventDescription.getText().toString();

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
