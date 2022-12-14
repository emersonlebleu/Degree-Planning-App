package com.emersonlebleu.academicscheduleapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emersonlebleu.academicscheduleapp.Entity.Assessment;
import com.emersonlebleu.academicscheduleapp.Entity.Objective;
import com.emersonlebleu.academicscheduleapp.Entity.Performance;
import com.emersonlebleu.academicscheduleapp.R;
import com.emersonlebleu.academicscheduleapp.UI.ObjectiveDetails;
import com.emersonlebleu.academicscheduleapp.UI.PerformanceDetails;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final Button assessmentItemView;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.itemViewBtn);
            assessmentItemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);

                    if (current.getClass() == Objective.class){
                        Intent intent = new Intent(context, ObjectiveDetails.class);
                        intent.putExtra("id", current.getId());
                        intent.putExtra("title", current.getTitle());
                        intent.putExtra("startDate", current.getStartDate());
                        intent.putExtra("score", ((Objective) current).getScore());

                        intent.putExtra("type", "OBJECTIVE");
                        intent.putExtra("courseId", current.getCourseId());
                        intent.putExtra("parent", parent);
                        context.startActivity(intent);

                    } else if (current.getClass() == Performance.class){
                        Intent intent = new Intent(context, PerformanceDetails.class);
                        intent.putExtra("id", current.getId());
                        intent.putExtra("title", current.getTitle());
                        intent.putExtra("startDate", current.getStartDate());
                        intent.putExtra("endDate", ((Performance) current).getEndDate());

                        intent.putExtra("percentComplete", ((Performance) current).getPercentageComplete());
                        intent.putExtra("type", "PERFORMANCE");
                        intent.putExtra("courseId", current.getCourseId());
                        intent.putExtra("parent", parent);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
    public AssessmentAdapter(Context context, String parent){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.parent = parent;
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    private final String parent;

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            holder.assessmentItemView.setText(title);
        } else {
            holder.assessmentItemView.setText("Not Titled");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAssessments == null ? 0: mAssessments.size();
    }
}
