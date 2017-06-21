package com.example.manushrivastava.muj_campusconnect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sameer on 21/6/17.
 */

public class ExamScheduleAdapter extends RecyclerView.Adapter<ExamScheduleAdapter.ExamViewHolder> {

    private ArrayList<ExamSchedule> examScheduleList;

    class ExamViewHolder extends RecyclerView.ViewHolder{

        public TextView courseName;
        public TextView courseID;
        public TextView date;
        public TextView time;
        public TextView venue;
        public TextView invigilator;

        public ExamViewHolder(View view){
            super(view);
            courseName = (TextView)view.findViewById(R.id.exam_courseName);
            courseID = (TextView)view.findViewById(R.id.exam_courseID);
            date = (TextView)view.findViewById(R.id.exam_date);
            time = (TextView)view.findViewById(R.id.exam_time);
            venue = (TextView)view.findViewById(R.id.exam_venue);
            invigilator = (TextView)view.findViewById(R.id.exam_invigilator);
        }
    }

    public ExamScheduleAdapter(ArrayList<ExamSchedule> examScheduleList){
        this.examScheduleList = examScheduleList;
    }

    @Override
    public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_schedule_row, parent, false);
        return new ExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExamViewHolder holder, int position) {
        ExamSchedule examSchedule = examScheduleList.get(position);
        holder.courseName.setText(examSchedule.getCourseName());
        holder.courseID.setText(examSchedule.getCourseID());
        holder.date.setText(examSchedule.getDate());
        holder.time.setText(examSchedule.getTime());
        holder.venue.setText(examSchedule.getVenue());
        holder.invigilator.setText(examSchedule.getInvigilator());
    }

    @Override
    public int getItemCount() {
        return examScheduleList.size();
    }
}

