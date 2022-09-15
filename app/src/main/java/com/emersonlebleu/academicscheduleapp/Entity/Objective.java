package com.emersonlebleu.academicscheduleapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "objectiveAssessments")
public class Objective extends Assessment {
    Integer score;

    @Ignore
    public Objective(int id, String title, String startDate, int courseId, Integer score) {
        super(id, title, startDate, courseId);
        this.score = score;
    }

    public Objective(String title, String startDate, int courseId, Integer score) {
        super(title, startDate, courseId);
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
