package com.example.manushrivastava.muj_campusconnect;

/**
 * Created by sameer on 22/6/17.
 */

public class InvigilationSchedule {

    String courseName;
    String CourseID;
    String date;
    String time;
    String venue;

    public InvigilationSchedule(String courseName, String courseID, String date, String time, String venue) {
        this.courseName = courseName;
        CourseID = courseID;
        this.date = date;
        this.time = time;
        this.venue = venue;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
