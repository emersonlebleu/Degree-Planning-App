package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.Entity.Objective;
import com.emersonlebleu.academicscheduleapp.Entity.Performance;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
import com.emersonlebleu.academicscheduleapp.R;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String rootList;
    public static int alertNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Repository repo = new Repository(getApplication());
//        Term term1 = new Term("First Term", "08/01/2022", "12/31/2022");
//        Term term2 = new Term("Second Term", "01/01/2023", "05/31/2023");
//
//        Course course1 = new Course("First Course", "08/01/2022", "09/31/2022", Course.Status.COMPLETED, "Bob Harper", "902-987-4637", "bobo@gmail.com", 1);
//        Course course2 = new Course("Second Course", "10/01/2022", "11/31/2022", Course.Status.IN_PROGRESS, "Bob Harper", "902-987-4637", "bobo@gmail.com", 1);
//        Course course3 = new Course("Third Course", "01/01/2023", "03/31/2023", Course.Status.PLAN_TO_TAKE, "Bob Harper", "902-987-4637", "bobo@gmail.com", 2);
//
//
//        Assessment assessment1 = new Assessment( "First Assessment", "08/01/2022", "08/02/2022", Assessment.Type.OBJECTIVE, 1);
//        Assessment assessment2 = new Assessment( "Second Assessment", "08/01/2022", "08/15/2022", Assessment.Type.PERFORMANCE, 1);
//        Assessment assessment3 = new Assessment( "Third Assessment", "09/01/2022", "09/15/2022", Assessment.Type.OBJECTIVE, 1);
//        Assessment assessment4 = new Assessment( "Fourth Assessment", "09/01/2022", "09/10/2022", Assessment.Type.PERFORMANCE, 1);
//        Assessment assessment5 = new Assessment( "Fifth Assessment", "09/12/2022", "09/31/2022", Assessment.Type.OBJECTIVE, 1);
//
//        Assessment assessment6 = new Assessment( "Sixth Assessment", "10/31/2022", "10/31/2022", Assessment.Type.OBJECTIVE, 2);
//        Assessment assessment7 = new Assessment( "Seventh Assessment", "11/31/2022", "11/31/2022", Assessment.Type.OBJECTIVE, 2);
//
//        Assessment assessment8 = new Assessment( "Eighth Assessment", "01/01/2023", "03/31/2023", Assessment.Type.PERFORMANCE, 3);
//
//        Note note1 = new Note("Testing notes on course 1.", 1);
//        Note note2 = new Note("Testing notes on course 2.", 2);
//        Note note3 = new Note("Testing notes on course 3.", 3);
//
//        repo.insert(term1);
//        repo.insert(term2);
//
//        repo.insert(course1);
//        repo.insert(course2);
//        repo.insert(course3);
//
//        repo.insert(assessment1);
//        repo.insert(assessment2);
//        repo.insert(assessment3);
//        repo.insert(assessment4);
//        repo.insert(assessment5);
//        repo.insert(assessment6);
//        repo.insert(assessment7);
//        repo.insert(assessment8);
//
//        repo.insert(note1);
//        repo.insert(note2);
//        repo.insert(note3);
    }

    public void enterTerms(View view) {
        rootList = "Term";
        Intent intent = new Intent(MainActivity.this, ListView.class);
        startActivity(intent);
    }

    public void enterAssessments(View view) {
        rootList = "Assessment";
        Intent intent = new Intent(MainActivity.this, ListView.class);
        startActivity(intent);
    }

    public void enterCourses(View view) {
        rootList = "Course";
        Intent intent = new Intent(MainActivity.this, ListView.class);
        startActivity(intent);
    }

    public void generateAndShare(View view) {
        Repository repo = new Repository(getApplication());
        LocalDateTime reportDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        String formatReportDate = reportDate.format(formatter);

        List<Term> terms = repo.getAllTerms();
        String report = "Generated on: " + formatReportDate + "\n";

        for (Term term: terms){
            report += "\nTerm ID #: " + term.getId() + " | " + term.getTitle() + " | Start: " + term.getStartDate() + " | End: " + term.getEndDate() + "\n";
            List<Course> coursesInTerm = repo.getCoursesInTerm(term.getId());
            for (Course course: coursesInTerm){
                report += "    Course ID #: " + course.getId() + " | " + course.getTitle() + " | Start: " + course.getStartDate() + " | End: " + course.getEndDate() + "\n";
                List<Objective> objInCourse = repo.getObjectiveAssessmentsInCourse(course.getId());
                List<Performance> perfInCourse = repo.getPerformanceAssessmentsInCourse(course.getId());
                for (Objective obj: objInCourse){
                    report += "          Obj Assessment ID #: " + obj.getId() + " | " + obj.getTitle() + " | Date: " + obj.getStartDate() + " | Score: " + obj.getScore() + "\n";
                }
                for (Performance perf: perfInCourse){
                    report += "          Perf Assessment ID #: " + perf.getId() + " | " + perf.getTitle() + " | Date: " + perf.getStartDate() + " | End: " + perf.getEndDate() + " | % Complete: " + perf.getPercentageComplete() + "%" + "\n";
                }
            }
        }


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, report);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Degree Plan Report: " + formatReportDate);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Degree Plan Report - generated on: " + formatReportDate);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}