package com.example.quizholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentDashboard extends AppCompatActivity implements View.OnClickListener{
    public CardView takeQuiz,updateCourse,viewProgress,announcement;
    public User userinfo=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        userinfo=(User) getIntent().getSerializableExtra("User");

        takeQuiz=(CardView)findViewById(R.id.quizQuestions);
        updateCourse=(CardView)findViewById(R.id.UpadteCourse);
        viewProgress=(CardView)findViewById(R.id.ViewProgress);
        announcement=(CardView)findViewById(R.id.AnnouncmentsStudent);

        takeQuiz.setOnClickListener(this);
        updateCourse.setOnClickListener(this);
        viewProgress.setOnClickListener(this);
        announcement.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quizQuestions:

                break;
            case R.id.UpadteCourse:
                //startActivity(new Intent(this, RegisterUser.class));
                Intent intent = new Intent(getApplicationContext(), UpdateProfessorCourses.class);
                intent.putExtra("User", userinfo);
                startActivity(intent);
                break;
            case R.id.ViewProgress:
                Intent intent1 = new Intent(getApplicationContext(), ApproveStudents.class);
                intent1.putExtra("User", userinfo);
                startActivity(intent1);
                break;
            case R.id.AnnouncmentsStudent:
                Intent intent2 = new Intent(getApplicationContext(), NotifyUser_Student.class);
                intent2.putExtra("User", userinfo);
                startActivity(intent2);
                break;

        }
    }
}