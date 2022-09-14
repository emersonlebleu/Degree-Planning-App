package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.emersonlebleu.academicscheduleapp.Adapters.AssessmentAdapter;
import com.emersonlebleu.academicscheduleapp.Adapters.CourseAdapter;
import com.emersonlebleu.academicscheduleapp.Adapters.TermAdapter;
import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
import com.emersonlebleu.academicscheduleapp.R;

import java.util.List;

public class ListView extends AppCompatActivity {
    String rootList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootList = MainActivity.rootList;

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
            List<Assessment> assessments = repo.getAllAssessments();
            final AssessmentAdapter adapter = new AssessmentAdapter(this, "ListView");

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter.setAssessments(assessments);
            setTitle("All Assessments");
        }
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
            List<Assessment> assessments = repo.getAllAssessments();
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
                    if (menuItem != null){
                        Intent intent=new Intent(ListView.this, AddAssessmentActivity.class);
                        intent.putExtra("rootList", rootList);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        }


    }
}