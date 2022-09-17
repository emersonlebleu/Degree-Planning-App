package com.emersonlebleu.academicscheduleapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Objective;

import java.util.List;

@Dao
public interface ObjectiveDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Objective objective);

    @Update
    void update(Objective objective);

    @Delete
    void delete(Objective objective);

    @Query("SELECT * FROM objectiveAssessments")
    List<Objective> getAllObjectiveAssessments();

    @Query("SELECT * from objectiveAssessments WHERE courseId LIKE :courseId")
    List<Objective> getObjectiveAssessmentsInCourse(int courseId);

    @Query("SELECT * FROM objectiveAssessments WHERE id LIKE :id")
    Objective getObjectiveAssessmentById(int id);
}
