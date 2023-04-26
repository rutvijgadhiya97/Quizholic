package com.example.quizholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Student_Select_Course_For_Quiz extends AppCompatActivity {
    public User userinfo=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_select_course_for_quiz);

        RecyclerView courseRV = findViewById(R.id.idStudentAnnouncement);
        userinfo=(User) getIntent().getSerializableExtra("User");
        String prevPage= getIntent().getStringExtra("previousPage");

         ArrayList<ListCourseName> lstcourse=new ArrayList<>();
        for(Course c : userinfo.CourseInfo)
        {
            if(c.approvalflag)
            {
                lstcourse.add(new ListCourseName(c.CoursesId));
            }
        }

        StudentCourseQuizAdapter scourseAdapter = new StudentCourseQuizAdapter(this,lstcourse,userinfo,prevPage);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(scourseAdapter);
    }
}