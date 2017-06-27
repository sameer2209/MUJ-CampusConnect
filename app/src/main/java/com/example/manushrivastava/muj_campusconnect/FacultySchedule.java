package com.example.manushrivastava.muj_campusconnect;

/**
 * Created by sameer on 28/6/17.
 */

public class FacultySchedule {

    String time;
    String detail;

    public FacultySchedule(String time, String detail) {
        this.time = time;
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
