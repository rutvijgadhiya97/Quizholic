package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApproveStudents extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    public User userinfo=new User();
    public Spinner courselst;

    public String sdelectedcourse=null;
    boolean[] selectedStudents;
    private DatabaseReference userlistReference;
    public ArrayList<Course> CourseInfo;
    public ArrayList<Course> CourseInfo_Approve;

    public List<String> lstStudent=new ArrayList<>();
    TextView textView;
    Button btn_Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_students);
        userinfo=(User) getIntent().getSerializableExtra("User");
        String[] arrOfCourses = userinfo.SelectedCourse.split(",");
        ArrayList<String> arr=new ArrayList<>();
        arr.add("----Select----");
        arr.addAll(Arrays.asList(arrOfCourses));
        btn_Home=(Button)findViewById(R.id.Home_button);
        btn_Home.setOnClickListener(this);
        courselst=(Spinner) findViewById(R.id.spinner_CourseSelect);
        courselst.setOnItemSelectedListener(this);

        ArrayAdapter addp=new ArrayAdapter(this, android.R.layout.simple_spinner_item,arr);
        addp.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        courselst.setAdapter(addp);

        textView = findViewById(R.id.textView);
        CourseInfo=new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(ApproveStudents.this);

        // set title
        builder.setTitle("Select Courses");

        // set dialog non cancelable
        builder.setCancelable(false);

        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<Integer> studentselectList = new ArrayList<>();
                if(sdelectedcourse.isEmpty())
                {
                    Toast.makeText(ApproveStudents.this,"Please select the course from Course-list.",Toast.LENGTH_LONG).show();
                    courselst.requestFocus();
                    return;
                }
                selectedStudents = new boolean[lstStudent.size()];
                String[] lstdtu=lstStudent.toArray(new String[0]);
                builder.setMultiChoiceItems(lstdtu, selectedStudents, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            studentselectList.add(i);
                            // Sort array list
                            Collections.sort(studentselectList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            studentselectList.remove(Integer.valueOf(i));
                        }
                    }
                });







                builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        CourseInfo_Approve=new ArrayList<>();
                        // use for loop
                        for (int j = 0; j < studentselectList.size(); j++) {
                            // concat array value
                            stringBuilder.append(lstStudent.get(studentselectList.get(j)));
                            Course cinfo=new Course(sdelectedcourse,true,CourseInfo.get(studentselectList.get(j)).StudentName,CourseInfo.get(studentselectList.get(j)).UserId);
                            //UpdateUserAfterApprove(CourseInfo.get(studentselectList.get(j)).UserId);
                            CourseInfo_Approve.add(cinfo);
                            // check condition

                        }
                        ApproveUser(CourseInfo_Approve);
                        // set text on textView


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedStudents.length; j++) {
                            // remove all selection
                            selectedStudents[j] = false;
                            // clear language list
                            studentselectList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }


        });


    }

    private void ApproveUser(ArrayList<Course> coursess) {
        for(Course c: coursess) {
            userlistReference = FirebaseDatabase.getInstance().getReference("Users");
            userlistReference.child(c.UserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userlist = snapshot.getValue(User.class);
                    for(Course cc : userlist.CourseInfo)
                    {
                        if(cc.CoursesId.equals(sdelectedcourse))
                        {
                            cc.approvalflag=true;
                            break;
                        }
                    }
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(c.UserId)
                            .setValue(userlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            userlistReference = FirebaseDatabase.getInstance().getReference(c.CoursesId.trim());
            userlistReference.child(c.UserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Course cours = snapshot.getValue(Course.class);
                    cours.approvalflag=c.approvalflag;

                    FirebaseDatabase.getInstance().getReference(c.CoursesId)
                            .child(c.UserId)
                            .setValue(cours).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String course = parent.getItemAtPosition(position).toString();
        if(!course.isEmpty()) {
            sdelectedcourse = course;
            lstStudent=new ArrayList<>();
            CourseInfo=new ArrayList<>();

            userlistReference = FirebaseDatabase.getInstance().getReference(sdelectedcourse.trim());
            userlistReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Course courses = postSnapshot.getValue(Course.class);
                        lstStudent.add(courses.StudentName);
                        CourseInfo.add(courses);

                        // here you can access to name property like university.name

                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        Toast.makeText(parent.getContext(), "Selected: " + course, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(getApplicationContext(), StudentDashboard.class);
        intent1.putExtra("User", userinfo);
        startActivity(intent1);
    }
}