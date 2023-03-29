package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class NotifyUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    public User userinfo=new User();
    public Spinner courselst;
    private Button share;
    private EditText announcmentTex,subject;
    public String sdelectedcourse=null;
    private DatabaseReference userlistReference;
    public ArrayList<Course> CourseInfo;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_user);
        share=(Button)findViewById(R.id.share);
        share.setOnClickListener(this);
        userinfo=(User) getIntent().getSerializableExtra("User");
        announcmentTex=(EditText)findViewById(R.id.textView_announce) ;
        subject=(EditText)findViewById(R.id.subject);
        SelectCourse();
    }

    private void SelectCourse() {
        String[] arrOfCourses = userinfo.SelectedCourse.split(",");
        ArrayList<String> arr=new ArrayList<>();
        arr.add("----Select----");
        arr.addAll(Arrays.asList(arrOfCourses));

        courselst=(Spinner) findViewById(R.id.spinnercourse);
        courselst.setOnItemSelectedListener(this);

        ArrayAdapter addp=new ArrayAdapter(this, android.R.layout.simple_spinner_item,arr);
        addp.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        courselst.setAdapter(addp);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String course = parent.getItemAtPosition(position).toString();
        if(!course.isEmpty())
        {
            sdelectedcourse=course;
        }
        Toast.makeText(parent.getContext(), "Selected: " + course, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        String announcetext=announcmentTex.getText().toString().trim();
        String subjecttext=subject.getText().toString().trim();
        if(sdelectedcourse.isEmpty())
        {
            Toast.makeText(NotifyUser.this,"Please select the course from Course-list.",Toast.LENGTH_LONG).show();
            courselst.requestFocus();
            return;
        }
        if(subjecttext.isEmpty())
        {
            Toast.makeText(NotifyUser.this,"Please Write a subject line.",Toast.LENGTH_LONG).show();
            courselst.requestFocus();
            return;
        }
        if(announcetext.isEmpty())
        {
            Toast.makeText(NotifyUser.this,"Please write a announcement,it cannot be empty.",Toast.LENGTH_LONG).show();
            courselst.requestFocus();
            return;
        }

        userlistReference = FirebaseDatabase.getInstance().getReference(sdelectedcourse);
        CourseInfo=new ArrayList<>();
        userlistReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Course courses = postSnapshot.getValue(Course.class);
                    CourseInfo.add(courses);

                    // here you can access to name property like university.name

                }
                PostNotification(CourseInfo,announcetext,subjecttext);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void PostNotification(ArrayList<Course> courseInfo,String announcetext,String subjecttext) {


        for(Course c:courseInfo)
        {
            userlistReference = FirebaseDatabase.getInstance().getReference("Users");
            userlistReference.child(c.UserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userlist = snapshot.getValue(User.class);
                   if(c.approvalflag)
                   {
                       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                       String date = sdf.format(new Date());
                       userlist.AnnouncmentList.add(new Notify(c.CoursesId,announcetext,date,subjecttext));
                   }
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(c.UserId)
                            .setValue(userlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(NotifyUser.this,"Announcement published successfully",Toast.LENGTH_LONG).show();
                                    announcmentTex.setText("");
                                            subject.setText("");

                                }
                            });
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}