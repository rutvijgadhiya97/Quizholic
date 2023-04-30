package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class viewClassReport extends AppCompatActivity {
    User Userinfo=new User();

    TextView yourScore,AvgScore,MaxScore,MinScore;
    ListQuestions questions=new ListQuestions();
    ArrayList<Long> listofScore=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_report);
        Userinfo = (User) getIntent().getSerializableExtra("User");
        questions = (ListQuestions) getIntent().getSerializableExtra("questions");
        yourScore=(TextView) findViewById(R.id.Your_Score_value);
        AvgScore=(TextView) findViewById(R.id.Avg_Score_value);
        MaxScore=(TextView) findViewById(R.id.Max_Score_value);
        MinScore=(TextView) findViewById(R.id.Min_Score_value);

        //set your score
        yourScore.setText(questions.lstQuestion.get(0).getScore());




        FirebaseDatabase.getInstance().getReference("CourseScores")
                .child(questions.lstQuestion.get(0).getSelectedCourse().trim()).child(questions.lstQuestion.get(0).getQuiztitle())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Long score = postSnapshot.getValue(Long.class);
                            listofScore.add(score);
                        }
                        AvgScore.setText(String.valueOf(calculateAverage(listofScore)));
                        MaxScore.setText(String.valueOf(Collections.max(listofScore)));
                        MinScore.setText(String.valueOf(Collections.min(listofScore)));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




    }
    public static double calculateAverage(ArrayList<Long> numbers) {
        int sum = 0;
        for (Long number : numbers) {
            sum += number;
        }
        return (double) sum / numbers.size();
    }
}