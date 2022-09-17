package com.emersonlebleu.academicscheduleapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.emersonlebleu.academicscheduleapp.Entity.Performance;

import java.util.List;

@Dao
public interface PerformanceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Performance performance);

    @Update
    void update(Performance performance);

    @Delete
    void delete(Performance performance);

    @Query("SELECT * FROM performanceAssessments")
    List<Performance> getAllPerformanceAssessments();

    @Query("SELECT * from performanceAssessments WHERE courseId LIKE :courseId")
    List<Performance> getPerformanceAssessmentsInCourse(int courseId);

    @Query("SELECT * FROM performanceAssessments WHERE id LIKE :id")
    Performance getPerformanceAssessmentById(int id);
}
