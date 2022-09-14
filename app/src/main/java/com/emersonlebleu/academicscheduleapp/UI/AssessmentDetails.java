package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
import com.emersonlebleu.academicscheduleapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    EditText assessmentTitleField;
    EditText startDateField;
    EditText endDateField;
    Spinner typeField;
    Spinner courseIdField;

    int id;
    String title;
    String startDate;
    String endDate;
    String type;
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
        setContentView(R.layout.activity_assessment_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pathDeterminer = MainActivity.rootList;

        assessmentTitleField = findViewById(R.id.assessmentTitleField);
        startDateField = findViewById(R.id.assessmentStartField);
        endDateField = findViewById(R.id.assessmentEndField);
        typeField = findViewById(R.id.assessmentTypeField);
        courseIdField = findViewById(R.id.courseOfAssessmentField);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        type = getIntent().getStringExtra("type");
        courseId = getIntent().getIntExtra("courseId", -1);

        assessmentTitleField.setText(title);
        startDateField.setText(startDate);
        endDateField.setText(endDate);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeField.setAdapter(adapter);
        typeField.setSelection(adapter.getPosition(type));

        //Setting Course Id Spinner
        Repository repo = new Repository(getApplication());
        List<Integer> courseIdArray = new ArrayList<>();

        List<Course> courses = repo.getAllCourses();
        for (Course course: courses){
            courseIdArray.add(course.getId());
        }

        ArrayAdapter<Integer> courseIdAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, courseIdArray);
        courseIdField.setAdapter(courseIdAdapter);
        courseIdField.setSelection(courseIdAdapter.getPosition(courseId));

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

                new DatePickerDialog(AssessmentDetails.this, start, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
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
                String tempEndDate = endDateField.getText().toString();
                if (tempEndDate.equals("")) tempEndDate = LocalDate.now().plus(1, ChronoUnit.WEEKS).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                System.out.println(tempEndDate);

                try {
                    calendarEnd.setTime(format.parse(tempEndDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(AssessmentDetails.this, end, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_delete_notify, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if (itemId == R.id.saveOption){
            title = assessmentTitleField.getText().toString();
            startDate = startDateField.getText().toString();
            endDate = endDateField.getText().toString();
            type = typeField.getSelectedItem().toString().toUpperCase();
            courseId = Integer.parseInt(courseIdField.getSelectedItem().toString());

            Assessment newAssessment = new Assessment(id, title, startDate, endDate, Assessment.Type.valueOf(type), courseId);

            Repository repo = new Repository(getApplication());
            repo.insert(newAssessment);

            //The Toast functionality
            Context context = getApplicationContext();
            String text = "Saved!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else if (itemId == R.id.deleteOption){
            title = assessmentTitleField.getText().toString();
            startDate = startDateField.getText().toString();
            endDate = endDateField.getText().toString();
            type = typeField.getSelectedItem().toString().toUpperCase();
            courseId = Integer.parseInt(courseIdField.getSelectedItem().toString());

            Assessment newAssessment = new Assessment(id, title, startDate, endDate, Assessment.Type.valueOf(type), courseId);

            Repository repo = new Repository(getApplication());
            repo.delete(newAssessment);

            if (pathDeterminer.equals("Assessment")){
                Intent intent = new Intent(AssessmentDetails.this, ListView.class);
                startActivity(intent);
            } else {
                Course current = repo.getCourseById(courseId);

                Intent intent = new Intent(AssessmentDetails.this, CourseDetails.class);
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
            }
        } else if (itemId == android.R.id.home){
            if (pathDeterminer.equals("Assessment")){
                Intent intent = new Intent(AssessmentDetails.this, ListView.class);
                startActivity(intent);
            } else {
                Repository repo = new Repository(getApplication());
                Course current = repo.getCourseById(courseId);

                Intent intent = new Intent(AssessmentDetails.this, CourseDetails.class);
                courseId = Integer.parseInt(courseIdField.getSelectedItem().toString());
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
            }
        } else if (itemId == R.id.notifyOptionStart) {
            String startDt = startDateField.getText().toString();
            Date st = null;

            try {
                st = format.parse(startDt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long stTrigger = st.getTime();

            Intent stIntentAssessment = new Intent(AssessmentDetails.this, NotificationReceiver.class);
            stIntentAssessment.putExtra("key", "Assessment number " + id + " start on " + startDt);

            PendingIntent stSenderAssessment = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.alertNum++, stIntentAssessment, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, stTrigger, stSenderAssessment);

        } else if (itemId == R.id.notifyOptionEnd){
            String endDt = endDateField.getText().toString();
            Date en = null;

            try {
                en = format.parse(endDt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long enTrigger = en.getTime();

            Intent enIntentAssessment = new Intent(AssessmentDetails.this, NotificationReceiver.class);
            enIntentAssessment.putExtra("key", "Assessment number " + id + " ends on " + endDt);

            PendingIntent enSenderAssessment = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.alertNum++, enIntentAssessment, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, enTrigger, enSenderAssessment);
        }
        return true;
    }

    private void updateLabelStart(){
        startDateField.setText(format.format(calendarStart.getTime()));
    }

    private void updateLabelEnd(){
        endDateField.setText(format.format(calendarEnd.getTime()));
    }
}