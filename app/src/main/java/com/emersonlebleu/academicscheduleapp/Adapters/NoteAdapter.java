package com.emersonlebleu.academicscheduleapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emersonlebleu.academicscheduleapp.Entity.Note;
import com.emersonlebleu.academicscheduleapp.R;
import com.emersonlebleu.academicscheduleapp.UI.NoteDetails;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final Button noteItemView;

        private NoteViewHolder(View itemView){
            super(itemView);
            noteItemView = itemView.findViewById(R.id.itemViewBtn);
            noteItemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Note current = mNotes.get(position);

                    Intent intent = new Intent(context, NoteDetails.class);
                    intent.putExtra("id", current.getId());
                    intent.putExtra("text", current.getText());
                    intent.putExtra("courseId", current.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }
    public NoteAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    private List<Note> mNotes;
    private final Context context;
    private final LayoutInflater mInflater;

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new NoteAdapter.NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if(mNotes != null){
            Note current = mNotes.get(position);
            String text = current.getText();
            holder.noteItemView.setText(text);
        } else {
            holder.noteItemView.setText("Not Titled");
        }
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0: mNotes.size();
    }
}
