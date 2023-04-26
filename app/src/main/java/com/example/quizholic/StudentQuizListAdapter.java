package com.example.quizholic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentQuizListAdapter extends RecyclerView.Adapter<StudentQuizListAdapter.ViewHolder>  {

        private final Context context;
        public   User Userinfo=new User();
        private final ArrayList<Questions> questionsModelArrayList;

        // Constructor
        public StudentQuizListAdapter(Context context, ArrayList<Questions> questionModelArrayList,User userinfo) {
            this.context = context;
            this.questionsModelArrayList = questionModelArrayList;
            this.Userinfo=userinfo;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // to inflate the layout for each item of recycler view.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_course_quiz_selection_card_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // to set data to textview and imageview of each card layout
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
            private  Button btn_start;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                idQuestion = itemView.findViewById(R.id.idQuestion);
                btn_start=itemView.findViewById(R.id.btn_startQuiz);
                btn_start.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();

                // Get the data item for this position
                String item = questionsModelArrayList.get(position).getQuiztitle();

                Intent intent = new Intent(v.getContext(), TakeQuiz.class);
                intent.putExtra("Userinfo",Userinfo);
                intent.putExtra("questionsModelArrayList",questionsModelArrayList);
                intent.putExtra("SelectedQuiz",item);
                v.getContext().startActivity(intent);

            }
        }


}
