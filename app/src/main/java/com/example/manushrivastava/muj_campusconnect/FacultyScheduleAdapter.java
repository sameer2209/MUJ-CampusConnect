package com.example.manushrivastava.muj_campusconnect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sameer on 28/6/17.
 */

public class FacultyScheduleAdapter extends RecyclerView.Adapter<FacultyScheduleAdapter.FacultyScheduleViewHolder> {

    private ArrayList<FacultySchedule> facultyScheduleArrayList;

    class FacultyScheduleViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        TextView detail;

        public FacultyScheduleViewHolder(View view){

            super(view);
            time = (TextView)view.findViewById(R.id.faculty_schedule_time_view);
            detail = (TextView)view.findViewById(R.id.faculty_schedule_detail_view);
        }
    }

    FacultyScheduleAdapter(ArrayList<FacultySchedule> facultyScheduleArrayList){

        this.facultyScheduleArrayList = facultyScheduleArrayList;
    }

    @Override
    public FacultyScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_schedule_row, parent, false);
        return  new FacultyScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FacultyScheduleViewHolder holder, int position) {

        FacultySchedule facultySchedule = facultyScheduleArrayList.get(position);
        holder.time.setText(facultySchedule.getTime());
        holder.detail.setText(facultySchedule.getDetail());
    }

    @Override
    public int getItemCount() {
        return facultyScheduleArrayList.size();
    }
}
