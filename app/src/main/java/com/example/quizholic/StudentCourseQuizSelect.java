package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentCourseQuizSelect extends AppCompatActivity {

    User userinfo=new User();
       ArrayList<Questions> Questionslist = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_quiz_select);
        userinfo = (User) getIntent().getSerializableExtra("item");
        RecyclerView QuizTitleRV=findViewById(R.id.idRVQuiz);

        //Getthequizlist(userinfo.CourcePostion);
        // Set the item data in the TextView in the layout
        for(ListQuestions lq:userinfo.LstQuestions)
        {
            if(lq.CourseName.equals(userinfo.CourcePostion) && !lq.QuizTakenFlag)
            {
                Questions q=new Questions();
                q.setQuiztitle(lq.lstQuestion.get(0).getQuiztitle());
                Questionslist.add(q);
            }
        }
        StudentQuizListAdapter QuizAdapter = new StudentQuizListAdapter(this, Questionslist,userinfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        QuizTitleRV.setLayoutManager(linearLayoutManager);
        QuizTitleRV.setAdapter(QuizAdapter);


    }

}