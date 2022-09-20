package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.emersonlebleu.academicscheduleapp.Adapters.CourseAdapter;
import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.Entity.Objective;
import com.emersonlebleu.academicscheduleapp.Entity.Performance;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
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

public class TermDetails extends AppCompatActivity {
    EditText termTitleField;
    EditText startDateField;
    EditText endDateField;
    TextView termIdField;

    String title;
    String startDate;
    String endDate;
    int id;

    Context thisContext;

    DatePickerDialog.OnDateSetListener start;
    DatePickerDialog.OnDateSetListener end;

    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();

    String dtFormat = "MM/dd/yyyy";
    SimpleDateFormat format = new SimpleDateFormat(dtFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_term_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termTitleField = findViewById(R.id.termTitleField);
        startDateField = findViewById(R.id.termStartField);
        endDateField = findViewById(R.id.termEndField);
        termIdField = findViewById(R.id.termIdNum);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        termTitleField.setText(title);
        startDateField.setText(startDate);
        endDateField.setText(endDate);
        termIdField.setText(String.valueOf(id));

        thisContext = this;

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

                new DatePickerDialog(TermDetails.this, start, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
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
                if (tempEndDate.equals("")) tempEndDate = LocalDate.now().plus(6, ChronoUnit.MONTHS).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                System.out.println(tempEndDate);

                try {
                    calendarEnd.setTime(format.parse(tempEndDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(TermDetails.this, end, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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

    private void updateLabelStart(){
        startDateField.setText(format.format(calendarStart.getTime()));
    }

    private void updateLabelEnd(){
        endDateField.setText(format.format(calendarEnd.getTime()));
    }

    @Override
    public void onResume() {
        super.onResume();

        //Setting up the scroll view population
        RecyclerView coursesOnTermView = findViewById(R.id.coursesInTermContainer);

        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getCoursesInTerm(id);
        final CourseAdapter adapter = new CourseAdapter(this, "TermDetails");

        coursesOnTermView.setAdapter(adapter);
        coursesOnTermView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_delete_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if (itemId == R.id.saveOption){
            title = termTitleField.getText().toString();
            startDate = startDateField.getText().toString();
            endDate = endDateField.getText().toString();

            if (LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dtFormat))
                    .isBefore(LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)))){
                new AlertDialog.Builder(this).setTitle("Date Error")
                        .setMessage("End date is before the start date please correct this.")
                        .setPositiveButton("Okay", null).show();
            } else {
                Term term = new Term(id, title, startDate, endDate);

                Repository repo = new Repository(getApplication());
                repo.update(term);

                //The Toast functionality
                Context context = getApplicationContext();
                String text = "Saved!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } else if (itemId == R.id.deleteOption){
            title = termTitleField.getText().toString();
            startDate = startDateField.getText().toString();
            endDate = endDateField.getText().toString();
            Term term = new Term(id, title, startDate, endDate);

            Repository repo = new Repository(getApplication());
            List<Course> courses = repo.getCoursesInTerm(id);

            if (courses.size() != 0){
                new AlertDialog.Builder(this).setTitle("Confirm Delete")
                        .setMessage("You will need to delete " + courses.size() + " associated courses first along with any associated notes or assessments." +
                                " Press 'Yes' now to delete these in preparation for deleting this Term.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (Course course: courses){
                                    List<Note> notes = repo.getNotesInCourse(course.getId());
                                    List<Objective> objectives = repo.getObjectiveAssessmentsInCourse(course.getId());
                                    List<Performance> performances = repo.getPerformanceAssessmentsInCourse(course.getId());
                                    List<Assessment> assessments = new ArrayList<>();
                                    assessments.addAll(objectives);
                                    assessments.addAll(performances);

                                    for (Note note: notes){ repo.delete(note); }
                                    for (Assessment assessment: assessments){
                                        if (assessment.getClass() == Objective.class){
                                            repo.delete((Objective) assessment);
                                        } else if (assessment.getClass() == Performance.class){
                                            repo.delete((Performance) assessment);
                                        }
                                    }
                                    repo.delete(course);

                                    //Reload Recycler
                                    RecyclerView coursesOnTermView = findViewById(R.id.coursesInTermContainer);

                                    List<Course> newCourses = repo.getCoursesInTerm(id);
                                    final CourseAdapter adapter = new CourseAdapter(thisContext, "TermDetails");

                                    coursesOnTermView.setAdapter(adapter);
                                    coursesOnTermView.setLayoutManager(new LinearLayoutManager(thisContext));
                                    adapter.setCourses(newCourses);

                                    //The Toast functionality
                                    Context context = getApplicationContext();
                                    String text = "Associated Entities Deleted!";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            }
                        })
                        .setNegativeButton("No", null).show();
            } else {
                repo.delete(term);
                Intent intent = new Intent(TermDetails.this, ListView.class);
                startActivity(intent);
            }

        } else if (itemId == android.R.id.home){
            Intent intent = new Intent(TermDetails.this, ListView.class);
            startActivity(intent);
        }
        return true;
    }

    public void showAddCoursePopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.add_course_popup);
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem != null){
                    Intent intent = new Intent(TermDetails.this, AddCourseActivity.class);
                    intent.putExtra("termId", id);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}