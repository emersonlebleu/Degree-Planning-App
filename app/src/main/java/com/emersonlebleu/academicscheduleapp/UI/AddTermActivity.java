package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.emersonlebleu.academicscheduleapp.Checker;
import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
import com.emersonlebleu.academicscheduleapp.R;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTermActivity extends AppCompatActivity {
    EditText termTitleField;
    EditText startDateField;
    EditText endDateField;

    String title;
    String startDate;
    String endDate;

    DatePickerDialog.OnDateSetListener start;
    DatePickerDialog.OnDateSetListener end;

    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();

    String dtFormat = "MM/dd/yyyy";
    SimpleDateFormat format = new SimpleDateFormat(dtFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termTitleField = findViewById(R.id.addTermTitleField);
        startDateField = findViewById(R.id.addTermStartField);
        endDateField = findViewById(R.id.addTermEndField);

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

                new DatePickerDialog(AddTermActivity.this, start, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
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

                new DatePickerDialog(AddTermActivity.this, end, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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

    public void saveNewTerm(View view) {
        title = termTitleField.getText().toString();
        startDate = startDateField.getText().toString();
        endDate = endDateField.getText().toString();

        if (LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dtFormat))
                .isBefore(LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dtFormat)))){
            new AlertDialog.Builder(this).setTitle("Date Error")
                    .setMessage("End date is before the start date please correct this.")
                    .setPositiveButton("Okay", null).show();
        } else if (!Checker.checkNewTerm(title)) {
            new AlertDialog.Builder(this).setTitle("Null Title")
                    .setMessage("Term title cannot be empty.")
                    .setPositiveButton("Okay", null).show();
        } else {
            Term newTerm = new Term(title, startDate, endDate);

            Repository repo = new Repository(getApplication());
            repo.insert(newTerm);

            onBackPressed();
        }
    }
}