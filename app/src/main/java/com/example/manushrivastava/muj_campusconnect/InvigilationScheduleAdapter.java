package com.example.manushrivastava.muj_campusconnect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sameer on 22/6/17.
 */

public class InvigilationScheduleAdapter extends RecyclerView.Adapter<InvigilationScheduleAdapter.InvigilationViewHolder> {

    private ArrayList<InvigilationSchedule> invigilationScheduleList;

    class InvigilationViewHolder extends RecyclerView.ViewHolder{

        public TextView courseName;
        public TextView courseID;
        public TextView date;
        public TextView time;
        public TextView venue;

        public InvigilationViewHolder(View view){
            super(view);
            courseName = (TextView)view.findViewById(R.id.exam_courseName);
            courseID = (TextView)view.findViewById(R.id.exam_courseID);
            date = (TextView)view.findViewById(R.id.exam_date);
            time = (TextView)view.findViewById(R.id.exam_time);
            venue = (TextView)view.findViewById(R.id.exam_venue);
        }
    }

    public InvigilationScheduleAdapter(ArrayList<InvigilationSchedule> invigilationScheduleList){
        this.invigilationScheduleList = invigilationScheduleList;
    }

    @Override
    public InvigilationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invigilation_schedule_row, parent, false);
        return new InvigilationScheduleAdapter.InvigilationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InvigilationScheduleAdapter.InvigilationViewHolder holder, int position) {
        InvigilationSchedule invigilationSchedule = invigilationScheduleList.get(position);
        holder.courseName.setText(invigilationSchedule.getCourseName());
        holder.courseID.setText(invigilationSchedule.getCourseID());
        holder.date.setText(invigilationSchedule.getDate());
        holder.time.setText(invigilationSchedule.getTime());
        holder.venue.setText(invigilationSchedule.getVenue());
    }

    @Override
    public int getItemCount() {
        return invigilationScheduleList.size();
    }
}
