package com.emersonlebleu.academicscheduleapp.Database;

import android.app.Application;

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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private Term mTerm;
    private CourseDAO mCourseDAO;
    private Course mCourse;

    private ObjectiveDAO mObjectiveDAO;
    private Objective mObjective;

    private PerformanceDAO mPerformanceDAO;
    private Performance mPerformance;

    private NoteDAO mNoteDAO;
    private Note mNote;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Course> mCoursesInTerm;

    private List<Objective> mAllObjectiveAssessments;
    private List<Objective> mObjectiveAssessmentsInCourse;

    private List<Performance> mAllPerformanceAssessments;
    private List<Performance> mPerformanceAssessmentsInCourse;

    private List<Note> mAllNotes;
    private List<Note> mNotesInCourse;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        ScheduleDBBuilder db = ScheduleDBBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mPerformanceDAO = db.performanceDAO();
        mObjectiveDAO = db.objectiveDAO();
        mNoteDAO=db.noteDAO();
    }

    //TERM--------------------------------------------------
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public Term getTermById(int id){
        databaseExecutor.execute(()->{
            mTerm = mTermDAO.getTermById(id);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mTerm;
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }
    //Course--------------------------------------------------
    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Course> getCoursesInTerm(int termId){
        databaseExecutor.execute(()->{
            mCoursesInTerm=mCourseDAO.getCoursesInTerm(termId);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCoursesInTerm;
    }

    public Course getCourseById(int id){
        databaseExecutor.execute(()->{
            mCourse = mCourseDAO.getCourseById(id);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mCourse;
    }

    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses=mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }

    //ObjectiveAssessment--------------------------------------------------
    public void insert(Objective objective){
        databaseExecutor.execute(()->{
            mObjectiveDAO.insert(objective);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Objective objective){
        databaseExecutor.execute(()->{
            mObjectiveDAO.update(objective);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Objective objective){
        databaseExecutor.execute(()->{
            mObjectiveDAO.delete(objective);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Objective> getObjectiveAssessmentsInCourse(int courseId){
        databaseExecutor.execute(()->{
            mObjectiveAssessmentsInCourse = mObjectiveDAO.getObjectiveAssessmentsInCourse(courseId);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mObjectiveAssessmentsInCourse;
    }

    public Objective getObjectiveAssessmentById(int id){
        databaseExecutor.execute(()->{
            mObjective = mObjectiveDAO.getObjectiveAssessmentById(id);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mObjective;
    }

    public List<Objective> getAllObjectiveAssessments(){
        databaseExecutor.execute(()->{
            mAllObjectiveAssessments=mObjectiveDAO.getAllObjectiveAssessments();
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllObjectiveAssessments;
    }

    //PerformanceAssessment--------------------------------------------------
    public void insert(Performance performance){
        databaseExecutor.execute(()->{
            mPerformanceDAO.insert(performance);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Performance performance){
        databaseExecutor.execute(()->{
            mPerformanceDAO.update(performance);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Performance performance){
        databaseExecutor.execute(()->{
            mPerformanceDAO.delete(performance);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Performance> getPerformanceAssessmentsInCourse(int courseId){
        databaseExecutor.execute(()->{
            mPerformanceAssessmentsInCourse = mPerformanceDAO.getPerformanceAssessmentsInCourse(courseId);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mPerformanceAssessmentsInCourse;
    }

    public Performance getPerformanceAssessmentById(int id){
        databaseExecutor.execute(()->{
            mPerformance = mPerformanceDAO.getPerformanceAssessmentById(id);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mPerformance;
    }

    public List<Performance> getAllPerformanceAssessments(){
        databaseExecutor.execute(()->{
            mAllPerformanceAssessments=mPerformanceDAO.getAllPerformanceAssessments();
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllPerformanceAssessments;
    }

    //Note--------------------------------------------------
    public void insert(Note note){
        databaseExecutor.execute(()->{
            mNoteDAO.insert(note);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Note note){
        databaseExecutor.execute(()->{
            mNoteDAO.update(note);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Note note){
        databaseExecutor.execute(()->{
            mNoteDAO.delete(note);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Note> getNotesInCourse(int courseId){
        databaseExecutor.execute(()->{
            mNotesInCourse = mNoteDAO.getNotesInCourse(courseId);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mNotesInCourse;
    }

    public Note getNoteById(int id){
        databaseExecutor.execute(()->{
            mNote = mNoteDAO.getNoteById(id);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mNote;
    }

    public List<Note> getAllNotes(){
        databaseExecutor.execute(()->{
            mAllNotes = mNoteDAO.getAllNotes();
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllNotes;
    }
}
