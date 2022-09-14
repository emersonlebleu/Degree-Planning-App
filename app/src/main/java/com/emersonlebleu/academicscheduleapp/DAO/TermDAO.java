package com.emersonlebleu.academicscheduleapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emersonlebleu.academicscheduleapp.Entity.Term;
import java.util.List;


@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * from terms ORDER BY startDate")
    List<Term> getAllTerms();

    @Query("SELECT * from terms WHERE id LIKE :id")
    Term getTermById(int id);
}
