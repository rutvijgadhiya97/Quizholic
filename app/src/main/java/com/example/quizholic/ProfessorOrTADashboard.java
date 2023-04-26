package com.example.quizholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfessorOrTADashboard extends AppCompatActivity implements View.OnClickListener {
    public CardView questions,approveStudents,updateCourse,notifyUser;
    public Button btn_logout;
    public User userinfo=new User();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_or_tadashboard);
        questions=(CardView)findViewById(R.id.quizQuestions);
        approveStudents=(CardView)findViewById(R.id.ApproveStudents);
        updateCourse=(CardView)findViewById(R.id.UpdateCourseList);
        notifyUser=(CardView)findViewById(R.id.Notify_for_quiz);
        btn_logout=(Button)findViewById(R.id.Logout);
         userinfo=(User) getIntent().getSerializableExtra("User");
        questions.setOnClickListener(this);
        approveStudents.setOnClickListener(this);
        updateCourse.setOnClickListener((this));
        notifyUser.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quizQuestions:
                Intent intent3 = new Intent(getApplicationContext(), Publish_Quiz.class);
                intent3.putExtra("User", userinfo);
                startActivity(intent3);
                break;
            case R.id.ApproveStudents:
                //startActivity(new Intent(this, RegisterUser.class));
                Intent intent = new Intent(getApplicationContext(), ApproveStudents.class);
                intent.putExtra("User", userinfo);
                startActivity(intent);
                break;
            case R.id.UpdateCourseList:
                Intent intent1 = new Intent(getApplicationContext(), UpdateProfessorCourses.class);
                intent1.putExtra("User", userinfo);
                startActivity(intent1);
                break;
            case R.id.Notify_for_quiz:
                Intent intent2 = new Intent(getApplicationContext(), NotifyUser.class);
                intent2.putExtra("User", userinfo);
                startActivity(intent2);
                break;
            case R.id.Logout:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));

                break;

        }
    }
}