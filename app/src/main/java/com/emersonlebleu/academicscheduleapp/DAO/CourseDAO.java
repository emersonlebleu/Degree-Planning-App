package com.emersonlebleu.academicscheduleapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emersonlebleu.academicscheduleapp.Entity.Course;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE id LIKE :id")
    Course getCourseById(int id);

    @Query("SELECT * from courses WHERE termId LIKE :termId")
    List<Course> getCoursesInTerm(int termId);
}
