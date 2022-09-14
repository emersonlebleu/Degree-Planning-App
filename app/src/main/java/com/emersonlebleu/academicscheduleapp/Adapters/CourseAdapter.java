package com.emersonlebleu.academicscheduleapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emersonlebleu.academicscheduleapp.Entity.Course;
import com.emersonlebleu.academicscheduleapp.R;
import com.emersonlebleu.academicscheduleapp.UI.CourseDetails;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final Button courseItemView;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.itemViewBtn);
            courseItemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);

                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getId());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("status", current.getStatus().toString());
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("courseParent", parent);
                    context.startActivity(intent);
                }
            });
        }
    }

    public CourseAdapter(Context context, String parent){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.parent = parent;
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    private final String parent;

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //When view holder created then inflate the list item
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null){
            Course current = mCourses.get(position);
            String title = current.getTitle();
            holder.courseItemView.setText(title);
        } else {
            holder.courseItemView.setText("Not Titled");
        }
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCourses == null ? 0: mCourses.size();
    }
}
