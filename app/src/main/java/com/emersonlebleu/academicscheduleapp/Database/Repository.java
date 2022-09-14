package com.emersonlebleu.academicscheduleapp.Database;

import android.app.Application;

import com.emersonlebleu.academicscheduleapp.DAO.AssessmentDAO;
import com.emersonlebleu.academicscheduleapp.DAO.CourseDAO;
import com.emersonlebleu.academicscheduleapp.DAO.NoteDAO;
import com.emersonlebleu.academicscheduleapp.DAO.TermDAO;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private Term mTerm;
    private CourseDAO mCourseDAO;
    private Course mCourse;
    private AssessmentDAO mAssessmentDAO;
    private Assessment mAssessment;
    private NoteDAO mNoteDAO;
    private Note mNote;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Course> mCoursesInTerm;
    private List<Assessment> mAllAssessments;
    private List<Assessment> mAssessmentsInCourse;
    private List<Note> mAllNotes;
    private List<Note> mNotesInCourse;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        ScheduleDBBuilder db = ScheduleDBBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mAssessmentDAO=db.assessmentDAO();
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

    //Assessment--------------------------------------------------
    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Assessment> getAssessmentsInCourse(int courseId){
        databaseExecutor.execute(()->{
            mAssessmentsInCourse=mAssessmentDAO.getAssessmentsInCourse(courseId);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssessmentsInCourse;
    }

    public Assessment getAssessmentById(int id){
        databaseExecutor.execute(()->{
            mAssessment = mAssessmentDAO.getAssessmentById(id);
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssessment;
    }

    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
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
