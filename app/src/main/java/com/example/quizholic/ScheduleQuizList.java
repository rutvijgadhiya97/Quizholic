package com.example.quizholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class ScheduleQuizList extends AppCompatActivity {
    User userinfo=new User();
    RecyclerView QuizTitleRV;
    ArrayList<Questions> Questionslist = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_quiz_list);
        userinfo = (User) getIntent().getSerializableExtra("item");
        QuizTitleRV=findViewById(R.id.idRVScheduleQuiz);

        //Getthequizlist(userinfo.CourcePostion);
        // Set the item data in the TextView in the layout
        for(ListQuestions lq:userinfo.LstQuestions)
        {
            if(lq.CourseName.equals(userinfo.CourcePostion))
            {
                Questions q=new Questions();
                q.setQuiztitle(lq.lstQuestion.get(0).getQuiztitle());
                Questionslist.add(q);
            }
        }
        ScheduleQuizListAdapter QuizAdapter = new ScheduleQuizListAdapter(this, Questionslist,userinfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        QuizTitleRV.setLayoutManager(linearLayoutManager);
        QuizTitleRV.setAdapter(QuizAdapter);

    }
}