package com.emersonlebleu.academicscheduleapp.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Performance;
import com.emersonlebleu.academicscheduleapp.R;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddPerformanceActivity extends AppCompatActivity {
    EditText assessmentTitleField;
    EditText startDateField;
    EditText endDateField;
    Spinner courseIdField;
    EditText percentCompleteField;

    int id;
    String title;
    String startDate;
    String endDate;
    Integer percentComplete;
    int courseId;

    String pathDeterminer;

    DatePickerDialog.OnDateSetListener start;
    DatePickerDialog.OnDateSetListener end;

    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();

    String dtFormat = "MM/dd/yyyy";
    SimpleDateFormat format = new SimpleDateFormat(dtFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_performance_assessment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pathDeterminer = MainActivity.rootList;

        assessmentTitleField = findViewById(R.id.addPerformanceTitleField);
        startDateField = findViewById(R.id.addPerformanceStartField);
        endDateField = findViewById(R.id.addPerformanceEndField);
        percentCompleteField = findViewById(R.id.addPercentComplete);
        courseIdField = findViewById(R.id.addCourseOfPerformanceField);

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

                new DatePickerDialog(AddPerformanceActivity.this, start, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
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

        //END DATE PICKER -------------------------------------------------------------------------
        endDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;

                String tempEndDate = endDateField.getText().toString();
                if (tempEndDate.equals("")) tempEndDate = LocalDate.now().plus(1, ChronoUnit.WEEKS).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                System.out.println(tempEndDate);

                try {
                    calendarEnd.setTime(format.parse(tempEndDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(AddPerformanceActivity.this, end, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        end = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (pathDeterminer.equals("Course") || pathDeterminer.equals("Term")){
            Repository repo = new Repository(getApplication());
            Course current = repo.getCourseById(courseId);

            Intent intent = new Intent(AddPerformanceActivity.this, CourseDetails.class);
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
            Intent intent = new Intent(AddPerformanceActivity.this, ListView.class);
            startActivity(intent);
        }
        return true;
    }

    public void saveNewPerformance(View view) {
        title = assessmentTitleField.getText().toString();
        startDate = startDateField.getText().toString();
        endDate = endDateField.getText().toString();
        courseId = Integer.parseInt(courseIdField.getSelectedItem().toString());

        if (percentCompleteField.getText().toString().equals("")){
            percentComplete = 0;
        } else {
            percentComplete = Integer.parseInt(percentCompleteField.getText().toString());
        }

        Performance newPerformance = new Performance(id, title, startDate, courseId, endDate, percentComplete);

        Repository repo = new Repository(getApplication());
        Course course = repo.getCourseById(courseId);
        LocalDate courseStartDate = LocalDate.parse(course.getStartDate(), DateTimeFormatter.ofPattern(dtFormat));
        LocalDate courseEndDate = LocalDate.parse(course.getEndDate(), DateTimeFormatter.ofPattern(dtFormat));

        if (LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dtFormat))
                .isBefore(LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)))){
            new AlertDialog.Builder(this).setTitle("Date Error")
                    .setMessage("End date is before the start date please correct this.")
                    .setPositiveButton("Okay", null).show();
        } else if (LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)).isBefore(courseStartDate)
                || LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)).isAfter(courseEndDate)
                || LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dtFormat)).isAfter(courseEndDate)
                || LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dtFormat)).isBefore(courseStartDate)) {
            new AlertDialog.Builder(this).setTitle("Date Error")
                    .setMessage("Performance assessment dates must be within specified course date range ("+ courseStartDate.format(DateTimeFormatter.ofPattern(dtFormat)) + " - "+ courseEndDate.format(DateTimeFormatter.ofPattern(dtFormat)) +"). Please correct this.")
                    .setPositiveButton("Okay", null).show();
        } else {
            repo.insert(newPerformance);
            onBackPressed();
        }
    }

    private void updateLabelStart(){
        startDateField.setText(format.format(calendarStart.getTime()));
    }

    private void updateLabelEnd(){
        endDateField.setText(format.format(calendarEnd.getTime()));
    }
}
