package com.example.quizholic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentCourseProgressAdapter extends RecyclerView.Adapter<StudentCourseProgressAdapter.ViewHolder> {

    private final Context context;
    public   User Userinfo=new User();
    private final ArrayList<Questions> questionsModelArrayList;

    // Constructor
    public StudentCourseProgressAdapter(Context context, ArrayList<Questions> questionModelArrayList,User userinfo) {
        this.context = context;
        this.questionsModelArrayList = questionModelArrayList;
        this.Userinfo=userinfo;
    }

    @NonNull
    @Override
    public StudentCourseProgressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_progress_quiz_card, parent, false);
        return new StudentCourseProgressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCourseProgressAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Questions model = questionsModelArrayList.get(position);
        holder.idQuestion.setText(model.getQuiztitle());
        holder.idquizScore.setText(model.getScore());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return questionsModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public  class ViewHolder extends RecyclerView.ViewHolder  {

        private final TextView idQuestion;
        private final TextView idquizScore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idQuestion = itemView.findViewById(R.id.idquizTitle);
            idquizScore = itemView.findViewById(R.id.idquizmarks);

        }


    }

}
