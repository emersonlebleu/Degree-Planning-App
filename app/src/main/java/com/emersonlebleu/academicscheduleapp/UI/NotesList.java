package com.emersonlebleu.academicscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.emersonlebleu.academicscheduleapp.Adapters.NoteAdapter;
import com.emersonlebleu.academicscheduleapp.Database.Repository;
import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.R;

import java.util.List;

public class NotesList extends AppCompatActivity {
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseId = getIntent().getIntExtra("courseId", -1);

        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Note> notes = repo.getNotesInCourse(courseId);
        final NoteAdapter adapter = new NoteAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setNotes(notes);
    }

    @Override
    public void onResume() {
        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Note> notes = repo.getNotesInCourse(courseId);
        final NoteAdapter adapter = new NoteAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setNotes(notes);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Repository repo = new Repository(getApplication());
        Course current = repo.getCourseById(courseId);

        Intent intent = new Intent(NotesList.this, CourseDetails.class);
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
        return true;
    }

    public void showAddNotePopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.add_note_popup);
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem != null){
                    Intent intent=new Intent(NotesList.this, AddNoteActivity.class);
                    intent.putExtra("courseId", courseId);
                    intent.putExtra("parent", "notesList");
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}