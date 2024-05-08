package com.timetrackerbackend.models;

public final class Task {
    private String title, startTime, endTime;

    public Task(String title) {
        this.title = title;
        this.startTime = "";
        this.endTime = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
