package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class view_Questions extends AppCompatActivity implements View.OnClickListener {
    TextView Title, ScheduledDate;
    private DatabaseReference userlistReference;
    Button btnPublish;
    private FirebaseAuth mAuth;
    public List<String> lstStudent = new ArrayList<>();
    ArrayList<Questions> Questionslist = new ArrayList<>();
    public User Userinfo=new User();
    String PrevPage="";
    Button btnDatePicker;
    EditText txtDate, time;
    private int mYear, mMonth, mDay,hr,min;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);
        RecyclerView QuestionsRV = findViewById(R.id.idRVQuestions);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(this);
        txtDate=(EditText)findViewById(R.id.ScheduleDate);
        time=(EditText)findViewById(R.id.ScheduleTime);


        // Here, we have created new array list and added data to it
        Title = (TextView) findViewById(R.id.Quiztitle);
        ScheduledDate = (TextView) findViewById(R.id.QuizDate);
        btnPublish = (Button) findViewById(R.id.btnPublish);
        btnPublish.setOnClickListener(this);

        Questionslist = (ArrayList<Questions>) getIntent().getSerializableExtra("Questions");
        Userinfo=(User) getIntent().getSerializableExtra("User");

        PrevPage=getIntent().getStringExtra("prevpage");
        if(PrevPage.equals("publishquiz")) {
            Title.setText(Questionslist.get(0).getQuiztitle());
            ScheduledDate.setText(Questionslist.get(0).getScheduleDate());
            txtDate.setVisibility(View.GONE);
            time.setVisibility(View.GONE);
            ScheduledDate.setVisibility(View.GONE);
            btnDatePicker.setVisibility(View.GONE);

        }
        else{

            String title=getIntent().getStringExtra("SelectedQuiz");
            Title.setText(title);



        }
        ArrayList<Questions> courseModelArrayList = new ArrayList<>();
        QuestionAdpater courseAdapter = new QuestionAdpater(this, Questionslist);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        QuestionsRV.setLayoutManager(linearLayoutManager);
        QuestionsRV.setAdapter(courseAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v==btnPublish) {
            mAuth = FirebaseAuth.getInstance();


            FirebaseDatabase.getInstance().getReference("garbage")
                    .setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                PublisQuestions();
                            } else {
                                Toast.makeText(view_Questions.this, "Failed to Publish the quiz! Try again!.", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
        else if (v == btnDatePicker) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                hr= c.get(Calendar.HOUR_OF_DAY);
                min= c.get(Calendar.MINUTE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time.setText(hourOfDay+":"+minute);
                            }


                        }, hr, min, false);

                datePickerDialog.show();
                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        timePickerDialog.show();
                    }
                });

        }
    }
    private void PublisQuestions()
    {
        if(Userinfo.Role.equals("TA"))
        {
            PublishForTA();

        }
        else
        {
            if(txtDate.getText().toString().isEmpty() || time.getText().toString().isEmpty() )
            {
                Toast.makeText(view_Questions.this, "Please select date and time.", Toast.LENGTH_LONG).show();

            }
            else {
                PublishForProfessor();
            }
        }

    }

    private void PublishForProfessor() {
        userlistReference = FirebaseDatabase.getInstance().getReference(Questionslist.get(0).getSelectedCourse().trim());
        userlistReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Course courses = postSnapshot.getValue(Course.class);
                    lstStudent.add(courses.UserId);


                    // here you can access to name property like university.name

                }
                ListQuestions lstquestio=new ListQuestions(false,Questionslist,Questionslist.get(0).getSelectedCourse().trim(),Questionslist.get(0).getQuiztitle());
                for (String stu : lstStudent) {
                    FirebaseDatabase.getInstance().getReference("Users").child(stu)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);



                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(user.UserID).child(Questionslist.get(0).getSelectedCourse().trim()+"_Questions").child(Questionslist.get(0).getQuiztitle())
                                            .setValue(lstquestio).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent intent1 = new Intent(getApplicationContext(), ProfessorOrTADashboard.class);
                                                    intent1.putExtra("User", Userinfo);
                                                    startActivity(intent1);
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  void PublishForTA()
    {
        userlistReference = FirebaseDatabase.getInstance().getReference("CoursesTakenBy");
        userlistReference.child(Questionslist.get(0).getSelectedCourse().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String userId = snapshot.getValue(String.class);
                ListQuestions lstquestio=new ListQuestions(false,Questionslist,Questionslist.get(0).getSelectedCourse().trim(),Questionslist.get(0).getQuiztitle());
                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(userId).child(Questionslist.get(0).getSelectedCourse().trim()+"_Questions").child(Questionslist.get(0).getQuiztitle())
                                            .setValue(lstquestio).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent intent1 = new Intent(getApplicationContext(), ProfessorOrTADashboard.class);
                                                    intent1.putExtra("User", Userinfo);
                                                    startActivity(intent1);
                                                }
                                            });





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}