package com.example.quizholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StudentDashboard extends AppCompatActivity implements View.OnClickListener{
    public CardView takeQuiz,updateCourse,viewProgress,announcement;
    public User userinfo=new User();
    public Button btn_logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        userinfo=(User) getIntent().getSerializableExtra("User");

        takeQuiz=(CardView)findViewById(R.id.quizQuestions);
        updateCourse=(CardView)findViewById(R.id.UpadteCourse);
        viewProgress=(CardView)findViewById(R.id.ViewProgress);
        announcement=(CardView)findViewById(R.id.AnnouncmentsStudent);
        btn_logout=(Button)findViewById(R.id.Logouts);
        btn_logout.setOnClickListener(this);
        takeQuiz.setOnClickListener(this);
        updateCourse.setOnClickListener(this);
        viewProgress.setOnClickListener(this);
        announcement.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quizQuestions:
                Intent intent3 = new Intent(getApplicationContext(), Student_Select_Course_For_Quiz.class);
                intent3.putExtra("User", userinfo);
                intent3.putExtra("previousPage", "quizQuestions");
                startActivity(intent3);
                break;
            case R.id.UpadteCourse:
                //startActivity(new Intent(this, RegisterUser.class));
                Intent intent = new Intent(getApplicationContext(), UpdateProfessorCourses.class);
                intent.putExtra("User", userinfo);
                startActivity(intent);
                break;
            case R.id.ViewProgress:
                Intent intent1 = new Intent(getApplicationContext(), Student_Select_Course_For_Quiz.class);
                intent1.putExtra("User", userinfo);
                intent1.putExtra("previousPage", "ViewProgress");
                startActivity(intent1);
                break;
            case R.id.AnnouncmentsStudent:
                Intent intent2 = new Intent(getApplicationContext(), NotifyUser_Student.class);
                intent2.putExtra("User", userinfo);
                startActivity(intent2);
                break;
            case R.id.Logouts:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }
}