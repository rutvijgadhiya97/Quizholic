package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScorePage extends AppCompatActivity implements View.OnClickListener {
    TextView TotalScore;

    Button btn_report,btn_home,Btn_classReport;
    User Userinfo=new User();
    ArrayList<Questions> questions=new ArrayList<>();
    int score=0;
    int totalScore=0;
    ListQuestions lques=new ListQuestions();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);
       // questionsModelArrayList = (ArrayList<Questions>) getIntent().getSerializableExtra("questionsModelArrayList");
        Userinfo = (User) getIntent().getSerializableExtra("Userinfo");
        questions = (ArrayList<Questions>) getIntent().getSerializableExtra("questions");
        btn_report=(Button)findViewById(R.id.report_button);
        btn_home=(Button)findViewById(R.id.Home_button) ;
        btn_report.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        TotalScore=(TextView) findViewById(R.id.Quiz_score);
        Btn_classReport=(Button)findViewById(R.id.ClassReport_button);
        Btn_classReport.setOnClickListener(this);
        for(Questions q :questions)
        {
            if(q.getUserans().equals(q.getCorrectans()))
            {
                score+=Integer.parseInt(q.getQuestionPts());
            }
            totalScore+=Integer.parseInt(q.getQuestionPts());
        }

        TotalScore.setText(String.valueOf(score)+"/"+String.valueOf(totalScore));
        lques=new ListQuestions();
        for(ListQuestions lq:Userinfo.LstQuestions)
        {
            if(lq.CourseName.equals(questions.get(0).getSelectedCourse()) && lq.QuizTitle.equals(questions.get(0).getQuiztitle()))
            {
                lques=lq;
                break;
            }
        }
        lques.QuizTakenFlag=true;
        for(Questions q:lques.lstQuestion)
        {
            q.setScore(String.valueOf(score));
        }
        FirebaseDatabase.getInstance().getReference("Users")
                .child(Userinfo.UserID).child(lques.lstQuestion.get(0).getSelectedCourse().trim()+"_Questions").child(lques.QuizTitle)
                .setValue(lques).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        FirebaseDatabase.getInstance().getReference("CourseScores")
                .child(lques.lstQuestion.get(0).getSelectedCourse().trim()).child(lques.QuizTitle)
                .child(Userinfo.UserID)
                .setValue(score).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.report_button:
                Intent intent3 = new Intent(getApplicationContext(),ViewQuizReport.class);
                intent3.putExtra("User", Userinfo);
                intent3.putExtra("questions", questions);
                startActivity(intent3);
                break;
            case R.id.Home_button:
                Intent intent1 = new Intent(getApplicationContext(), StudentDashboard.class);
                intent1.putExtra("User", Userinfo);
                startActivity(intent1);

                break;
            case R.id.ClassReport_button:
                Intent intent2 = new Intent(getApplicationContext(), viewClassReport.class);
                intent2.putExtra("User", Userinfo);
                intent2.putExtra("questions", lques);
                startActivity(intent2);

                break;


        }
    }
}