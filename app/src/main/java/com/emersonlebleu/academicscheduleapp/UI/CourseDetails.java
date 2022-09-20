package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emersonlebleu.academicscheduleapp.Adapters.AssessmentAdapter;
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

public class CourseDetails extends AppCompatActivity {
    EditText courseTitleField;
    EditText startDateField;
    EditText endDateField;
    Spinner statusField;
    EditText instructorNameField;
    EditText instructorPhoneField;
    EditText instructorEmailField;
    Spinner termIdField;
    TextView courseIdField;

    int id;
    String title;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    int termId;

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
        setContentView(R.layout.activity_course_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pathDeterminer = MainActivity.rootList;

        courseTitleField = findViewById(R.id.courseTitleField);
        startDateField = findViewById(R.id.courseStartField);
        endDateField = findViewById(R.id.courseEndField);
        statusField = findViewById(R.id.courseStatusField);
        instructorNameField = findViewById(R.id.courseInstructorField);
        instructorPhoneField = findViewById(R.id.courseInsPhoneField);
        instructorEmailField = findViewById(R.id.courseInsEmailField);
        termIdField = findViewById(R.id.termOfCourseField);
        courseIdField = findViewById(R.id.courseIdNum);

        id = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("termId", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");

        courseTitleField.setText(title);
        startDateField.setText(startDate);
        endDateField.setText(endDate);
        instructorNameField.setText(instructorName);
        instructorPhoneField.setText(instructorPhone);
        instructorEmailField.setText(instructorEmail);
        courseIdField.setText(String.valueOf(id));

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusField.setAdapter(statusAdapter);
        statusField.setSelection(statusAdapter.getPosition(status));

        //Setting up the scroll view population
        RecyclerView assessmentsOnCourseView = findViewById(R.id.assessmentsRecyclerView);

        Repository repo = new Repository(getApplication());

        //Setting Term Id Spinner
        List<Integer> termIdArray = new ArrayList<>();

        List<Term> terms = repo.getAllTerms();
        for (Term term: terms){
            termIdArray.add(term.getId());
        }

        ArrayAdapter<Integer> termIdAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, termIdArray);
        termIdField.setAdapter(termIdAdapter);
        termIdField.setSelection(termIdAdapter.getPosition(termId));

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

                new DatePickerDialog(CourseDetails.this, start, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
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
                if (tempEndDate.equals("")) tempEndDate = LocalDate.now().plus(3, ChronoUnit.MONTHS).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                System.out.println(tempEndDate);

                try {
                    calendarEnd.setTime(format.parse(tempEndDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(CourseDetails.this, end, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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

    @Override
    public void onResume() {
        super.onResume();
        //Setting up the scroll view population
        RecyclerView assessmentsOnCourseView = findViewById(R.id.assessmentsRecyclerView);

        Repository repo = new Repository(getApplication());

        List<Objective> objectives = repo.getObjectiveAssessmentsInCourse(id);
        List<Performance> performances = repo.getPerformanceAssessmentsInCourse(id);
        List<Assessment> assessments = new ArrayList<>();
        assessments.addAll(objectives);
        assessments.addAll(performances);

        final AssessmentAdapter adapter = new AssessmentAdapter(this, "CourseDetails");

        assessmentsOnCourseView.setAdapter(adapter);
        assessmentsOnCourseView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_delete_notify, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if (itemId == R.id.saveOption){
            title = courseTitleField.getText().toString();
            startDate = startDateField.getText().toString();
            endDate = endDateField.getText().toString();
            status = statusField.getSelectedItem().toString().toUpperCase();
            instructorName = instructorNameField.getText().toString();
            instructorPhone = instructorPhoneField.getText().toString();
            instructorEmail = instructorEmailField.getText().toString();

            termId = Integer.parseInt(termIdField.getSelectedItem().toString());

            Course course = new Course(id, title, startDate, endDate, Course.Status.valueOf(status), instructorName, instructorPhone, instructorEmail, termId);

            Repository repo = new Repository(getApplication());
            Term term = repo.getTermById(termId);
            LocalDate termStartDate = LocalDate.parse(term.getStartDate(), DateTimeFormatter.ofPattern(dtFormat));
            LocalDate termEndDate = LocalDate.parse(term.getEndDate(), DateTimeFormatter.ofPattern(dtFormat));

            if (LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dtFormat))
                    .isBefore(LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)))){
                new AlertDialog.Builder(this).setTitle("Date Error")
                        .setMessage("End date is before the start date please correct this.")
                        .setPositiveButton("Okay", null).show();
            } else if (LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)).isBefore(termStartDate) || LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dtFormat)).isAfter(termEndDate)) {
                new AlertDialog.Builder(this).setTitle("Date Error")
                        .setMessage("Course dates must be within specified term ("+ termStartDate.format(DateTimeFormatter.ofPattern(dtFormat)) + " - "+ termEndDate.format(DateTimeFormatter.ofPattern(dtFormat)) +"). Please correct this.")
                        .setPositiveButton("Okay", null).show();
            } else {
                repo.update(course);

                //The Toast functionality
                Context context = getApplicationContext();
                String text = "Saved!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } else if (itemId == R.id.deleteOption){
            Repository repo = new Repository(getApplication());
            List<Note> notes = repo.getNotesInCourse(id);

            List<Objective> objectives = repo.getObjectiveAssessmentsInCourse(id);
            List<Performance> performances = repo.getPerformanceAssessmentsInCourse(id);
            List<Assessment> assessments = new ArrayList<>();
            assessments.addAll(objectives);
            assessments.addAll(performances);

            title = courseTitleField.getText().toString();
            startDate = startDateField.getText().toString();
            endDate = endDateField.getText().toString();
            status = statusField.getSelectedItem().toString().toUpperCase();
            instructorName = instructorNameField.getText().toString();
            instructorPhone = instructorPhoneField.getText().toString();
            instructorEmail = instructorEmailField.getText().toString();
            termId = Integer.parseInt(termIdField.getSelectedItem().toString());

            Course course = new Course(id, title, startDate, endDate, Course.Status.valueOf(status), instructorName, instructorPhone, instructorEmail, termId);

            String message = "";

            if (!notes.isEmpty() && !assessments.isEmpty()){
                message += "Are you sure? There are: " + notes.size() + " notes & " + assessments.size() + " assessments associated with this course."
                        + " Deleting this will delete those notes and assessments. Press yes to delete associated notes, assessments, and this course.";

                new AlertDialog.Builder(this).setTitle("Confirm Delete")
                        .setMessage(message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (Note note: notes){
                                    repo.delete(note);
                                }
                                for (Objective objective: objectives){
                                    repo.delete(objective);
                                }
                                for (Performance performance: performances){
                                    repo.delete(performance);
                                }

                                repo.delete(course);

                                if (pathDeterminer.equals("Course")){
                                    Intent intent = new Intent(CourseDetails.this, ListView.class);
                                    startActivity(intent);
                                } else if (pathDeterminer.equals("Term")) {
                                    Term current = repo.getTermById(termId);

                                    Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                                    intent.putExtra("id", termId);
                                    intent.putExtra("title", current.getTitle());
                                    intent.putExtra("startDate", current.getStartDate());
                                    intent.putExtra("endDate", current.getEndDate());

                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("No", null).show();
            } else if (!notes.isEmpty()){
                message += "Are you sure? There are: " + notes.size() + " notes associated with this course."
                        + " Deleting this will delete those notes. Press yes to delete associated notes and this course.";

                new AlertDialog.Builder(this).setTitle("Confirm Delete")
                        .setMessage(message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (Note note: notes){
                                    repo.delete(note);
                                }

                                repo.delete(course);

                                if (pathDeterminer.equals("Course")){
                                    Intent intent = new Intent(CourseDetails.this, ListView.class);
                                    startActivity(intent);
                                } else if (pathDeterminer.equals("Term")) {
                                    Term current = repo.getTermById(termId);

                                    Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                                    intent.putExtra("id", termId);
                                    intent.putExtra("title", current.getTitle());
                                    intent.putExtra("startDate", current.getStartDate());
                                    intent.putExtra("endDate", current.getEndDate());

                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("No", null).show();
            } else if (!assessments.isEmpty()) {
                message += "Are you sure? There are: " + assessments.size() + " assessments associated with this course."
                        + " Deleting this will delete those assessments. Press yes to delete associated assessments and this course.";

                new AlertDialog.Builder(this).setTitle("Confirm Delete")
                        .setMessage(message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (Objective objective: objectives){
                                    repo.delete(objective);
                                }
                                for (Performance performance: performances){
                                    repo.delete(performance);
                                }

                                repo.delete(course);

                                if (pathDeterminer.equals("Course")){
                                    Intent intent = new Intent(CourseDetails.this, ListView.class);
                                    startActivity(intent);
                                } else if (pathDeterminer.equals("Term")) {
                                    Term current = repo.getTermById(termId);

                                    Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                                    intent.putExtra("id", termId);
                                    intent.putExtra("title", current.getTitle());
                                    intent.putExtra("startDate", current.getStartDate());
                                    intent.putExtra("endDate", current.getEndDate());

                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("No", null).show();
                } else {
                repo.delete(course);

                if (pathDeterminer.equals("Course")){
                    Intent intent = new Intent(CourseDetails.this, ListView.class);
                    startActivity(intent);
                } else if (pathDeterminer.equals("Term")) {
                    Term current = repo.getTermById(termId);

                    Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                    intent.putExtra("id", termId);
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());

                    startActivity(intent);
                }
            }
        } else if (itemId == android.R.id.home){
            if (pathDeterminer.equals("Course")){
                Intent intent = new Intent(CourseDetails.this, ListView.class);
                startActivity(intent);
            } else if (pathDeterminer.equals("Term")) {
                Repository repo = new Repository(getApplication());
                Term current = repo.getTermById(termId);

                Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                intent.putExtra("id", termId);
                intent.putExtra("title", current.getTitle());
                intent.putExtra("startDate", current.getStartDate());
                intent.putExtra("endDate", current.getEndDate());

                startActivity(intent);
            }
        }
        return true;
    }

    public void showAddNoteOrAssessmentPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.add_assess_or_note_popup);
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Add Objective Assessment")){
                    Intent intent=new Intent(CourseDetails.this, AddObjectiveActivity.class);
                    intent.putExtra("courseId", id);
                    startActivity(intent);
                    return true;
                } else if (menuItem.getTitle().equals("Add Note")){
                    Intent intent = new Intent(CourseDetails.this, AddNoteActivity.class);
                    intent.putExtra("courseId", id);
                    intent.putExtra("noteParent", "courseDetails");
                    startActivity(intent);
                    return true;
                } else if (menuItem.getTitle().equals("Add Performance Assessment")){
                    Intent intent=new Intent(CourseDetails.this, AddPerformanceActivity.class);
                    intent.putExtra("courseId", id);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    public void goToNotesList(View view) {
        Intent intent = new Intent(CourseDetails.this, NotesList.class);
        intent.putExtra("courseId", id);
        startActivity(intent);
    }

    private void updateLabelStart(){
        startDateField.setText(format.format(calendarStart.getTime()));
    }

    private void updateLabelEnd(){
        endDateField.setText(format.format(calendarEnd.getTime()));
    }
}







