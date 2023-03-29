package com.example.quizholic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Notify> courseModelArrayList;

    // Constructor
    public CourseAdapter(Context context, ArrayList<Notify> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Notify model = courseModelArrayList.get(position);
        holder.courseNameTV.setText(model.getCourseId());
        holder.announcmentText.setText( model.getAnnounce());
        holder.subjectline.setText(model.getSubjectLine()+":");
        holder.date.setText(model.getdate());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }


    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final ImageView courseIV;
        private final TextView courseNameTV;
        private final TextView announcmentText;
        private final TextView subjectline;
        private final TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseIV = itemView.findViewById(R.id.idIVCourseImage);
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            announcmentText = itemView.findViewById(R.id.annoncementContent);
            subjectline = itemView.findViewById(R.id.idSubjectLine);
            date = itemView.findViewById(R.id.idTVCourseRating);
        }
    }
}