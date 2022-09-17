package com.emersonlebleu.academicscheduleapp.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "performanceAssessments")
public class Performance extends Assessment{
    private String endDate;
    private Integer percentageComplete;

    @Ignore
    public Performance(int id, String title, String startDate, int courseId, String endDate, Integer percentageComplete) {
        super(id, title, startDate, courseId);
        this.endDate = endDate;
        this.percentageComplete = percentageComplete;
    }

    public Performance(String title, String startDate, int courseId, String endDate, Integer percentageComplete) {
        super(title, startDate, courseId);
        this.endDate = endDate;
        this.percentageComplete = percentageComplete;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String goalCompleteDate) {
        this.endDate = endDate;
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
