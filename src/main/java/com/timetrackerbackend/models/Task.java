package com.timetrackerbackend.models;

public final class Task {
    private String title, date, startTime, endTime;

    public Task(String title, String date) {
        this.title = title;
        this.date = date;
        this.startTime = null;
        this.endTime = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
