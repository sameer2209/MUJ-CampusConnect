package com.example.manushrivastava.muj_campusconnect;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class FacultyScheduleFragmnet extends Fragment {

    String facultyScheduleArray[][] = new String[10][2];

    public FacultyScheduleFragmnet() {
        // Required empty public constructor
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty_schedule_fragmnet, container, false);
        String facultyName = getArguments().getString("facultyName");
        String department = getArguments().getString("department");
        String day = getArguments().getString("day");

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
        while (facultyScheduleArray[i][0] != null){
            facultySchedule = new FacultySchedule("9:00 AM - 10:00 AM", "Free");
            facultyScheduleArrayList.add(facultySchedule);
            i++;
        }



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
