package com.example.quizholic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleQuizListAdapter extends RecyclerView.Adapter<ScheduleQuizListAdapter.ViewHolder> {
    private final Context context;
    public   User Userinfo=new User();
    private final ArrayList<Questions> questionsModelArrayList;
    private CardView courseCardView;

    // Constructor
    public ScheduleQuizListAdapter(Context context, ArrayList<Questions> questionModelArrayList,User userinfo) {
        this.context = context;
        this.questionsModelArrayList = questionModelArrayList;
        this.Userinfo=userinfo;
    }

    @NonNull
    @Override
    public ScheduleQuizListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_quiz_list, parent, false);
        return new ScheduleQuizListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Questions model = questionsModelArrayList.get(position);
        holder.idQuestion.setText(model.getQuiztitle());
    }



    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return questionsModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView idQuestion;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idQuestion = itemView.findViewById(R.id.idQuestion);
            courseCardView=itemView.findViewById(R.id.schedulequizList);
            courseCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            // Get the data item for this position
            String item = questionsModelArrayList.get(position).getQuiztitle();

            Intent intent = new Intent(v.getContext(), view_Questions.class);
            intent.putExtra("User",Userinfo);
            intent.putExtra("Questions",Userinfo.LstQuestions.get(0).lstQuestion);
            intent.putExtra("SelectedQuiz",item);
            intent.putExtra("prevpage","Schedulepage");
            v.getContext().startActivity(intent);

        }
    }


}
