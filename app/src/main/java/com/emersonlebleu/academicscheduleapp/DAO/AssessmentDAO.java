package com.emersonlebleu.academicscheduleapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments")
    List<Assessment> getAllAssessments();

    @Query("SELECT * from assessments WHERE courseId LIKE :courseId")
    List<Assessment> getAssessmentsInCourse(int courseId);

    @Query("SELECT * FROM assessments WHERE id LIKE :id")
    Assessment getAssessmentById(int id);
}
