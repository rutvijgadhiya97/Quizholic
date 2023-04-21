package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class UpdateProfessorCourses extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView textView;
    boolean[] selectedLanguage;
    public ArrayList<Course> CourseInfo;
    public User userinfo = new User();
    private ProgressBar pgrBar;

    public String Selectedcourse = null;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_professor_courses);

        Resources res = getResources();
        String[] courseslst = res.getStringArray(R.array.cnames);
        textView = findViewById(R.id.textView);
        selectedLanguage = new boolean[courseslst.length];
        ArrayList<Integer> courseList = new ArrayList<>();
        CourseInfo = new ArrayList<>();
        userinfo = (User) getIntent().getSerializableExtra("User");
        textView.setText(userinfo.SelectedCourse);
        SelectCourse(courseslst, courseList);
        button = (Button) findViewById(R.id.UpdateCourses);
        button.setOnClickListener(this);
        pgrBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void SelectCourse(String[] courseslst, ArrayList<Integer> courseList) {
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfessorCourses.this);

                // set title
                builder.setTitle("Select Courses");

                // set dialog non cancelable
                builder.setCancelable(false);
                builder.setMultiChoiceItems(courseslst, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            courseList.add(i);
                            // Sort array list
                            Collections.sort(courseList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            courseList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < courseList.size(); j++) {
                            // concat array value
                            stringBuilder.append(courseslst[courseList.get(j)]);
                            Course cinfo = new Course(courseslst[courseList.get(j)], false, userinfo.FirstName, userinfo.UserID.toString());
                            CourseInfo.add(cinfo);
                            // check condition
                            if (j != courseList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                        Selectedcourse = stringBuilder.toString();
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
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            courseList.clear();
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

    @Override
    public void onClick(View v) {
        userinfo.CourseInfo = CourseInfo;
        userinfo.SelectedCourse = Selectedcourse;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference("Users")
                .child(userinfo.UserID)
                .setValue(userinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateProfessorCourses.this, "Courses Updated Successfully.", Toast.LENGTH_LONG).show();
                            pgrBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(UpdateProfessorCourses.this, "Failed to Update Course! Try again!.", Toast.LENGTH_LONG).show();
                            pgrBar.setVisibility(View.GONE);
                        }
                    }
                });
        if (userinfo.Role.equals("Student")) {
            for (int i = 0; i < CourseInfo.size(); i++) {
                FirebaseDatabase.getInstance().getReference(CourseInfo.get(i).CoursesId)
                        .child(userinfo.UserID)
                        .setValue(CourseInfo.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateProfessorCourses.this, "Courses Updated Successfully.", Toast.LENGTH_LONG).show();
                                    pgrBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(UpdateProfessorCourses.this, "Failed to Update Course! Try again!.", Toast.LENGTH_LONG).show();
                                    pgrBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        }
    }
}