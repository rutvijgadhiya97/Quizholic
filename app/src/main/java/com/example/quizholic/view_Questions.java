package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class view_Questions extends AppCompatActivity implements View.OnClickListener {
    TextView Title,ScheduledDate;
    private DatabaseReference userlistReference;
    Button btnPublish;
    private FirebaseAuth mAuth;
    ArrayList<Questions> Questionslist=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);
        RecyclerView QuestionsRV = findViewById(R.id.idRVQuestions);

        // Here, we have created new array list and added data to it
        Title=(TextView)findViewById(R.id.Quiztitle);
        ScheduledDate=(TextView)findViewById(R.id.QuizDate);
        btnPublish=(Button)findViewById(R.id.btnPublish);
        btnPublish.setOnClickListener(this);

        Questionslist = (ArrayList<Questions>) getIntent().getSerializableExtra("Questions");
        Title.setText(Questionslist.get(0).Quiztitle);
        ScheduledDate.setText(Questionslist.get(0).Date);
        // we are initializing our adapter class and passing our arraylist to it.
        ArrayList<Questions> courseModelArrayList=new ArrayList<>();
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
        mAuth=FirebaseAuth.getInstance();
        userlistReference = FirebaseDatabase.getInstance().getReference(Questionslist.get(0).SelectedCourse+"_questions");
        userlistReference
                .child(Questionslist.get(0).Quiztitle)
                .setValue(Questionslist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(view_Questions.this, "Quiz Published sucessfully.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), ProfessorOrTADashboard.class));
                        } else {
                            Toast.makeText(view_Questions.this, "Failed to Publish the quiz! Try again!.", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}