package com.emersonlebleu.academicscheduleapp.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Objective;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
import com.emersonlebleu.academicscheduleapp.R;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddObjectiveActivity extends AppCompatActivity {
    EditText assessmentTitleField;
    EditText startDateField;
    EditText scoreField;
    Spinner courseIdField;

    int id;
    String title;
    String startDate;
    Integer score;
    String type;
    int courseId;

    String pathDeterminer;

    DatePickerDialog.OnDateSetListener start;
    final Calendar calendarStart = Calendar.getInstance();
    String dtFormat = "MM/dd/yyyy";
    SimpleDateFormat format = new SimpleDateFormat(dtFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_objective_assessment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pathDeterminer = MainActivity.rootList;

        assessmentTitleField = findViewById(R.id.addObjectiveTitleField);
        startDateField = findViewById(R.id.addObjectiveStartField);
        scoreField = findViewById(R.id.addScore);
        courseIdField = findViewById(R.id.addCourseOfObjectiveField);

        courseId = getIntent().getIntExtra("courseId", -1);

        //Setting Course Id Spinner
        Repository repo = new Repository(getApplication());
        List<Integer> courseIdArray = new ArrayList<>();

        List<Course> courses = repo.getAllCourses();
        for (Course course: courses){
            courseIdArray.add(course.getId());
        }

        ArrayAdapter<Integer> courseIdAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, courseIdArray);
        courseIdField.setAdapter(courseIdAdapter);

        if(courseId != -1){
            courseIdField.setSelection(courseIdAdapter.getPosition(courseId));
        } else {
            courseIdField.setSelection(0);
        }

        //START DATE PICKER ----------------------------------------------------------------
        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;

                String tempStartDate = startDateField.getText().toString();
                if (tempStartDate.equals("")) tempStartDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                System.out.println(tempStartDate);

                try {
                    calendarStart.setTime(format.parse(tempStartDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(AddObjectiveActivity.this, start, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (pathDeterminer.equals("Course") || pathDeterminer.equals("Term")){
            Repository repo = new Repository(getApplication());
            Course current = repo.getCourseById(courseId);

            Intent intent = new Intent(AddObjectiveActivity.this, CourseDetails.class);
            intent.putExtra("id", courseId);
            intent.putExtra("termId", current.getTermId());
            intent.putExtra("title", current.getTitle());
            intent.putExtra("startDate", current.getStartDate());
            intent.putExtra("endDate", current.getEndDate());
            intent.putExtra("status", current.getStatus().toString());
            intent.putExtra("instructorName", current.getInstructorName());
            intent.putExtra("instructorPhone", current.getInstructorPhone());
            intent.putExtra("instructorEmail", current.getInstructorEmail());

            startActivity(intent);
        } else {
            Intent intent = new Intent(AddObjectiveActivity.this, ListView.class);
            startActivity(intent);
        }
        return true;
    }

    public void saveNewObjective(View view) {
        title = assessmentTitleField.getText().toString();
        startDate = startDateField.getText().toString();
        courseId = Integer.parseInt(courseIdField.getSelectedItem().toString());

        if (scoreField.getText().toString().equals("")){
            score = 0;
        } else {
            score = Integer.parseInt(scoreField.getText().toString());
        }

        Objective newObjective = new Objective(id, title, startDate, courseId, score);

        Repository repo = new Repository(getApplication());
        Course course = repo.getCourseById(courseId);
        LocalDate courseStartDate = LocalDate.parse(course.getStartDate(), DateTimeFormatter.ofPattern(dtFormat));
        LocalDate courseEndDate = LocalDate.parse(course.getEndDate(), DateTimeFormatter.ofPattern(dtFormat));

        if (LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)).isBefore(courseStartDate)
                || LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)).isAfter(courseEndDate)) {
            new AlertDialog.Builder(this).setTitle("Date Error")
                    .setMessage("Assessment date must be within course timeframe ("+ courseStartDate.format(DateTimeFormatter.ofPattern(dtFormat)) + " - "+ courseEndDate.format(DateTimeFormatter.ofPattern(dtFormat)) +"). Please correct this.")
                    .setPositiveButton("Okay", null).show();
        } else {
            repo.insert(newObjective);
            onBackPressed();
        }
    }

    private void updateLabelStart(){
        startDateField.setText(format.format(calendarStart.getTime()));
    }

}
