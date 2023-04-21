package com.example.quizholic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class QuestionAdpater extends RecyclerView.Adapter<QuestionAdpater.ViewHolder> {

    private final Context context;
    private final ArrayList<Questions> questionsModelArrayList;

    // Constructor
    public QuestionAdpater(Context context, ArrayList<Questions> questionModelArrayList) {
        this.context = context;
        this.questionsModelArrayList = questionModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_question_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Questions model = questionsModelArrayList.get(position);
        holder.idQuestion.setText(model.getQuestion());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return questionsModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView idQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idQuestion = itemView.findViewById(R.id.idQuestion);
        }
    }
}


