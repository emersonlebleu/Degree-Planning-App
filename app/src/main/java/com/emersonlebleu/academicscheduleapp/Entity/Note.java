package com.emersonlebleu.academicscheduleapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private int courseId;

    @Ignore
    public Note(int id, String text, int courseId) {
        this.id = id;
        this.text = text;
        this.courseId = courseId;
    }

    public Note(String text, int courseId) {
        this.text = text;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setNote(String text) {
        this.text = text;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
