package com.emersonlebleu.academicscheduleapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String startDate;
    private int courseId;

    public enum Type {PERFORMANCE, OBJECTIVE};

    @Ignore
    public Assessment(int id, String title, String startDate, int courseId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.courseId = courseId;
    }

    public Assessment(String title, String startDate, int courseId) {
        this.title = title;
        this.startDate = startDate;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", courseId=" + courseId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
