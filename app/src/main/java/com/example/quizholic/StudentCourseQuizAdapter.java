package com.example.quizholic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentCourseQuizAdapter extends RecyclerView.Adapter<StudentCourseQuizAdapter.ViewHolder>  {
    private final Context context;
    private final ArrayList<ListCourseName> listofCoursesSelected;
    private User user=new User();
    String PrevPage="";

    // Constructor
    public StudentCourseQuizAdapter(Context context, ArrayList<ListCourseName> courseModelArrayList,User userin,String prevPage) {
        this.context = context;
        this.listofCoursesSelected = courseModelArrayList;
        this.user=userin;
        this.PrevPage=prevPage;
        Getthequizlist();
    }

    @NonNull
    @Override
    public StudentCourseQuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_course_card_layout, parent, false);
        return new StudentCourseQuizAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListCourseName model = listofCoursesSelected.get(position);
        holder.courseNameTV.setText(model.getCourseId());
    }




    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return listofCoursesSelected.size();
    }
    private void Getthequizlist() {
        user.LstQuestions=new ArrayList<>();
        for(ListCourseName lcn :listofCoursesSelected)
            FirebaseDatabase.getInstance().getReference("Users").child(user.UserID).child(lcn.getCourseId().trim()+"_Questions")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                ListQuestions lstque =postSnapshot.getValue(ListQuestions.class);
                                user.LstQuestions.add(lstque);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            throw error.toException();
                        }
                    });
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //private final ImageView courseIV;
        private final TextView courseNameTV;
        private CardView courseCardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseIV = itemView.findViewById(R.id.idIVCourseImage);
            courseNameTV = itemView.findViewById(R.id.idCourseName);
            courseCardView=itemView.findViewById(R.id.courseCard);
            courseCardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(PrevPage.equals("quizQuestions")) {
                int position = getAdapterPosition();

                // Get the data item for this position
                String item = listofCoursesSelected.get(position).getCourseId();
                user.CourcePostion = item;


                Intent intent = new Intent(v.getContext(), StudentCourseQuizSelect.class);
                intent.putExtra("item", user);
                v.getContext().startActivity(intent);
            }
            if(PrevPage.equals("ViewProgress"))
            {
                int position = getAdapterPosition();

                // Get the data item for this position
                String item = listofCoursesSelected.get(position).getCourseId();
                user.CourcePostion = item;


                Intent intent = new Intent(v.getContext(), Student_Course_Progress.class);
                intent.putExtra("item", user);
                v.getContext().startActivity(intent);
            }

        }



    }
}
