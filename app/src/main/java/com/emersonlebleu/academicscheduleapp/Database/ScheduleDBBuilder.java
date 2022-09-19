package com.emersonlebleu.academicscheduleapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.emersonlebleu.academicscheduleapp.DAO.CourseDAO;
import com.emersonlebleu.academicscheduleapp.DAO.NoteDAO;
import com.emersonlebleu.academicscheduleapp.DAO.ObjectiveDAO;
import com.emersonlebleu.academicscheduleapp.DAO.PerformanceDAO;
import com.emersonlebleu.academicscheduleapp.DAO.TermDAO;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.Entity.Objective;
import com.emersonlebleu.academicscheduleapp.Entity.Performance;
import com.emersonlebleu.academicscheduleapp.Entity.Term;

@Database(entities = {Term.class, Course.class, Objective.class, Performance.class, Note.class}, version = 20, exportSchema = false)
public abstract class ScheduleDBBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract NoteDAO noteDAO();
    public abstract ObjectiveDAO objectiveDAO();
    public abstract PerformanceDAO performanceDAO();

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
