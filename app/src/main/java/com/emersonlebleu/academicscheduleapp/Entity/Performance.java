package com.emersonlebleu.academicscheduleapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "performanceAssessments")
public class Performance extends Assessment{
    private String goalCompleteDate;
    private String completeDate;
    private Integer percentageComplete;

    @Ignore
    public Performance(int id, String title, String startDate, int courseId, String goalCompleteDate, String completeDate, Integer percentageComplete) {
        super(id, title, startDate, courseId);
        this.goalCompleteDate = goalCompleteDate;
        this.percentageComplete = percentageComplete;
        this.completeDate = completeDate;
    }

    public Performance(String title, String startDate, int courseId, String goalCompleteDate, String completeDate, Integer percentageComplete) {
        super(title, startDate, courseId);
        this.goalCompleteDate = goalCompleteDate;
        this.percentageComplete = percentageComplete;
        this.completeDate = completeDate;
    }

    public String getGoalCompleteDate() {
        return goalCompleteDate;
    }

    public void setGoalCompleteDate(String goalCompleteDate) {
        this.goalCompleteDate = goalCompleteDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public Integer getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(Integer percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    public void incrementPercentComplete(Integer amount){
        if (percentageComplete + amount <= 100){
            percentageComplete += amount;
        }
    }
}
