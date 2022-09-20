package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
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

public class AddCourseActivity extends AppCompatActivity {
    EditText courseTitleField;
    EditText startDateField;
    EditText endDateField;
    Spinner statusField;
    EditText instructorNameField;
    EditText instructorPhoneField;
    EditText instructorEmailField;
    Spinner termIdField;

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
        setContentView(R.layout.activity_add_course);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pathDeterminer = MainActivity.rootList;

        courseTitleField = findViewById(R.id.addCourseTitleField);
        startDateField = findViewById(R.id.addCourseStartField);
        endDateField = findViewById(R.id.addCourseEndField);
        statusField = findViewById(R.id.addCourseStatusField);
        instructorNameField = findViewById(R.id.addCourseInstructorField);
        instructorPhoneField = findViewById(R.id.addCourseInsPhoneField);
        instructorEmailField = findViewById(R.id.addCourseInsEmailField);
        termIdField = findViewById(R.id.addTermOfCourseField);

        termId = getIntent().getIntExtra("termId", -1);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusField.setAdapter(adapter);

        //Setting Term Id Spinner
        Repository repo = new Repository(getApplication());
        List<Integer> termIdArray = new ArrayList<>();

        List<Term> terms = repo.getAllTerms();
        for (Term term: terms){
            termIdArray.add(term.getId());
        }

        ArrayAdapter<Integer> termIdAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, termIdArray);
        termIdField.setAdapter(termIdAdapter);

        if (termId != -1 ){
            termIdField.setSelection(termIdAdapter.getPosition(termId));
        } else {
            termIdField.setSelection(0);
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

                new DatePickerDialog(AddCourseActivity.this, start, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
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

                new DatePickerDialog(AddCourseActivity.this, end, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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
        if (pathDeterminer.equals("Term")){
            Repository repo = new Repository(getApplication());
            Term current = repo.getTermById(termId);

            Intent intent = new Intent(AddCourseActivity.this, TermDetails.class);
            intent.putExtra("id", termId);
            intent.putExtra("title", current.getTitle());
            intent.putExtra("startDate", current.getStartDate());
            intent.putExtra("endDate", current.getEndDate());

            startActivity(intent);
        } else {
            Intent intent = new Intent(AddCourseActivity.this, ListView.class);
            startActivity(intent);
        }
        return true;
    }

    public void saveNewCourse(View view) {
        title = courseTitleField.getText().toString();
        startDate = startDateField.getText().toString();
        endDate = endDateField.getText().toString();
        status = statusField.getSelectedItem().toString().toUpperCase();
        instructorName = instructorNameField.getText().toString();
        instructorPhone = instructorPhoneField.getText().toString();
        instructorEmail = instructorEmailField.getText().toString();
        termId = Integer.parseInt(termIdField.getSelectedItem().toString());

        Course newCourse = new Course(title, startDate, endDate, Course.Status.valueOf(status), instructorName, instructorPhone, instructorEmail, termId);

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
            repo.insert(newCourse);
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