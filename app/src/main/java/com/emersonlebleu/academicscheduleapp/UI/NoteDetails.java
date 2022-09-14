package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.Entity.Term;
import com.emersonlebleu.academicscheduleapp.R;

public class NoteDetails extends AppCompatActivity {
    EditText noteField;
    EditText courseIdField;

    int id;
    String text;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        noteField = findViewById(R.id.noteField);
        courseIdField = findViewById(R.id.courseOfNoteField);

        id = getIntent().getIntExtra("id", -1);
        text = getIntent().getStringExtra("text");
        courseId = getIntent().getIntExtra("courseId", -1);

        noteField.setText(text);
        courseIdField.setText(String.valueOf(courseId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_delete_share_note, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if (itemId == R.id.noteSaveOption){
            text = noteField.getText().toString();

            Note note = new Note(id, text, courseId);

            Repository repo = new Repository(getApplication());
            repo.update(note);

            //The Toast functionality
            Context context = getApplicationContext();
            String text = "Saved!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else if (itemId == R.id.noteDeleteOption){
            text = noteField.getText().toString();

            Note note = new Note(id, text, courseId);

            Repository repo = new Repository(getApplication());
            repo.delete(note);

            Intent intent = new Intent(NoteDetails.this, NotesList.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        } else if (itemId == R.id.noteShareOption){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, noteField.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Note number " + id + " from course number " + courseId);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        } else if (itemId == android.R.id.home){
            Intent intent = new Intent(NoteDetails.this, NotesList.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
        return true;
    }
}