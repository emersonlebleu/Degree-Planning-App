package com.emersonlebleu.academicscheduleapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.emersonlebleu.academicscheduleapp.DAO.AssessmentDAO;
import com.emersonlebleu.academicscheduleapp.DAO.CourseDAO;
import com.emersonlebleu.academicscheduleapp.DAO.NoteDAO;
import com.emersonlebleu.academicscheduleapp.DAO.TermDAO;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Note.class}, version = 18, exportSchema = false)
public abstract class ScheduleDBBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract NoteDAO noteDAO();

    private static volatile ScheduleDBBuilder INSTANCE;

    static ScheduleDBBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (ScheduleDBBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), ScheduleDBBuilder.class, "ScheduleAppDB")
                            .fallbackToDestructiveMigration()
                            .build();
                    //could allow main thread queries
                }
            }
        }
        return INSTANCE;
    }
}
