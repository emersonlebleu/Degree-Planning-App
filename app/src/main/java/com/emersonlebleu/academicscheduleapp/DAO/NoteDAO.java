package com.emersonlebleu.academicscheduleapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Query("SELECT * from notes WHERE courseId LIKE :courseId")
    List<Note> getNotesInCourse(int courseId);

    @Query("SELECT * FROM notes WHERE id LIKE :id")
    Note getNoteById(int id);
}
