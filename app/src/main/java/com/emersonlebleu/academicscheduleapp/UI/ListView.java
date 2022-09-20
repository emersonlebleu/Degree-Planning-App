package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.emersonlebleu.academicscheduleapp.Adapters.AssessmentAdapter;
import com.emersonlebleu.academicscheduleapp.Adapters.CourseAdapter;
import com.emersonlebleu.academicscheduleapp.Adapters.TermAdapter;
import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Objective;
import com.emersonlebleu.academicscheduleapp.Entity.Performance;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
import com.emersonlebleu.academicscheduleapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListView extends AppCompatActivity {
    String rootList;
    TextView searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        searchField = findViewById(R.id.searchField);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootList = MainActivity.rootList;
    }

    @Override
    public void onResume(){
        super.onResume();

        if (rootList.equals("Term")){
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());
            List<Term> terms = repo.getAllTerms();
            final TermAdapter adapter = new TermAdapter(this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setTerms(terms);
            setTitle("All Terms");
        } else if (rootList.equals("Course")){
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());
            List<Course> courses = repo.getAllCourses();
            final CourseAdapter adapter = new CourseAdapter(this, "ListView");

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setCourses(courses);
            setTitle("All Courses");
        } else if (rootList.equals("Assessment")) {
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());

            List<Objective> objectives = repo.getAllObjectiveAssessments();
            List<Performance> performances = repo.getAllPerformanceAssessments();
            List<Assessment> assessments = new ArrayList<>();
            assessments.addAll(objectives);
            assessments.addAll(performances);

            final AssessmentAdapter adapter = new AssessmentAdapter(this, "ListView");

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setAssessments(assessments);
            setTitle("All Assessments");
        }
    }

    public void showAddPopup(View view) {
        if (rootList.equals("Term")){
            PopupMenu popup = new PopupMenu(this, view);
            popup.inflate(R.menu.add_term_popup);
            popup.show();

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem != null){
                        Intent intent=new Intent(ListView.this, AddTermActivity.class);
                        intent.putExtra("rootList", rootList);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        } else if (rootList.equals("Course")){
            PopupMenu popup = new PopupMenu(this, view);
            popup.inflate(R.menu.add_course_popup);
            popup.show();

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem != null){
                        Intent intent=new Intent(ListView.this, AddCourseActivity.class);
                        intent.putExtra("rootList", rootList);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        } else if (rootList.equals("Assessment")) {
            PopupMenu popup = new PopupMenu(this, view);
            popup.inflate(R.menu.add_assess_popup);
            popup.show();

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getTitle().equals("Add Objective Assessment")){
                        Intent intent=new Intent(ListView.this, AddObjectiveActivity.class);
                        startActivity(intent);
                        return true;
                    } else if (menuItem.getTitle().equals("Add Performance Assessment")){
                        Intent intent=new Intent(ListView.this, AddPerformanceActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        }


    }

    public void findBtnClick(View view) {
        String searchCriteria = searchField.getText().toString();

        if (rootList.equals("Term")){
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());
            List<Term> terms = repo.getAllTerms();
            List<Term> termsSearched = new ArrayList<Term>();

            for (Term term: terms){
                if (term.getTitle().toLowerCase().contains(searchCriteria.toLowerCase())){
                    termsSearched.add(term);
                }
            }

            final TermAdapter adapter = new TermAdapter(this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setTerms(termsSearched);
            setTitle("All Terms");
        } else if (rootList.equals("Course")){
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());
            List<Course> courses = repo.getAllCourses();
            List<Course> coursesSearched = new ArrayList<Course>();

            for (Course course: courses){
                if (course.getTitle().toLowerCase().contains(searchCriteria.toLowerCase())){
                    coursesSearched.add(course);
                }
            }

            final CourseAdapter adapter = new CourseAdapter(this, "ListView");

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setCourses(coursesSearched);
            setTitle("All Courses");
        } else if (rootList.equals("Assessment")) {
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());

            List<Objective> objectives = repo.getAllObjectiveAssessments();
            List<Performance> performances = repo.getAllPerformanceAssessments();
            List<Assessment> assessments = new ArrayList<>();
            assessments.addAll(objectives);
            assessments.addAll(performances);

            List<Assessment> assessmentsSearched = new ArrayList<>();

            for (Assessment assessment: assessments){
                if (assessment.getTitle().toLowerCase().contains(searchCriteria.toLowerCase())){
                    assessmentsSearched.add(assessment);
                }
            }

            final AssessmentAdapter adapter = new AssessmentAdapter(this, "ListView");

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setAssessments(assessmentsSearched);
            setTitle("All Assessments");
        }
    }

    public void allBtnClick(View view) {
        searchField.setText("");

        if (rootList.equals("Term")){
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());
            List<Term> terms = repo.getAllTerms();
            final TermAdapter adapter = new TermAdapter(this);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setTerms(terms);
            setTitle("All Terms");
        } else if (rootList.equals("Course")){
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());
            List<Course> courses = repo.getAllCourses();
            final CourseAdapter adapter = new CourseAdapter(this, "ListView");

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setCourses(courses);
            setTitle("All Courses");
        } else if (rootList.equals("Assessment")) {
            RecyclerView recyclerView = findViewById(R.id.rootRecyclerView);
            Repository repo = new Repository(getApplication());

            List<Objective> objectives = repo.getAllObjectiveAssessments();
            List<Performance> performances = repo.getAllPerformanceAssessments();
            List<Assessment> assessments = new ArrayList<>();
            assessments.addAll(objectives);
            assessments.addAll(performances);

            final AssessmentAdapter adapter = new AssessmentAdapter(this, "ListView");

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setAssessments(assessments);
            setTitle("All Assessments");
        }
    }
}