package com.example.quizholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class Student_Course_Progress extends AppCompatActivity   {
    User userinfo=new User();
    ArrayList<Questions> Questionslist = new ArrayList<>();
    RecyclerView QuizTitleRV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_progress);
        userinfo = (User) getIntent().getSerializableExtra("item");
         QuizTitleRV=findViewById(R.id.idRVQuizProgress);

        //Getthequizlist(userinfo.CourcePostion);
        // Set the item data in the TextView in the layout
        for(ListQuestions lq:userinfo.LstQuestions)
        {
            if(lq.CourseName.equals(userinfo.CourcePostion) && lq.QuizTakenFlag)
            {
                Questions q=new Questions();
                q.setQuiztitle(lq.lstQuestion.get(0).getQuiztitle());
                q.setScore(lq.lstQuestion.get(0).getScore());
                Questionslist.add(q);
            }
        }
        StudentCourseProgressAdapter QuizAdapter = new StudentCourseProgressAdapter(this, Questionslist,userinfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        QuizTitleRV.setLayoutManager(linearLayoutManager);
        QuizTitleRV.setAdapter(QuizAdapter);

    }
}