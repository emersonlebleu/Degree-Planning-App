package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.R;

public class AddNoteActivity extends AppCompatActivity {
    int courseId;
    String noteParent;

    EditText noteField;
    EditText courseIdField;

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteField = findViewById(R.id.addNoteField);
        courseIdField = findViewById(R.id.addCourseOfNoteField);

        courseId = getIntent().getIntExtra("courseId", -1);
        noteParent = getIntent().getStringExtra("noteParent");

        courseIdField.setText(String.valueOf(courseId));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (noteParent != null && noteParent.equals("courseDetails")){
            Repository repo = new Repository(getApplication());
            Course current = repo.getCourseById(courseId);

            Intent intent = new Intent(AddNoteActivity.this, CourseDetails.class);
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
            Intent intent = new Intent(AddNoteActivity.this, NotesList.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
        return true;
    }

    public void saveNewNote(View view) {
        text = noteField.getText().toString();

        Note newNote = new Note(text, courseId);

        Repository repo = new Repository(getApplication());
        repo.insert(newNote);

        onBackPressed();
    }
}