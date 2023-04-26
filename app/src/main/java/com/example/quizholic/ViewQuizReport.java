package com.example.quizholic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewQuizReport extends AppCompatActivity implements View.OnClickListener {
    private TextView quiztitle,question,showAns,questionpts;
    private Button btn_Next,btn_Previous;
    public   User Userinfo=new User();
    private int currentQuestionIndex = 0;
    private  ArrayList<Questions> questionsModelArrayList;
    private ArrayList<Questions> questions=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_report);
        Userinfo = (User) getIntent().getSerializableExtra("Userinfo");
        questions = (ArrayList<Questions>) getIntent().getSerializableExtra("questions");

        quiztitle=(TextView) findViewById(R.id.Quiz_title_report);
        question=(TextView) findViewById(R.id.question_textview_report);
        showAns=(TextView)findViewById(R.id.Correctans_textview_report);
        questionpts=(TextView)findViewById(R.id.questionpts_textview_report);
        btn_Next=(Button) findViewById(R.id.Next_button_report);
        btn_Previous=(Button) findViewById(R.id.prev_button_report);
        btn_Next.setOnClickListener(this);
        btn_Previous.setOnClickListener(this);
        btn_Previous.setVisibility(View.INVISIBLE);
        quiztitle.setText(questions.get(0).getQuiztitle());

        currentQuestionIndex = 0;
        displayCurrentQuestion();
    }
    @SuppressLint("ResourceType")
    private void displayCurrentQuestion() {
        Questions currentQuestion = questions.get(currentQuestionIndex);

        RadioGroup optionsRadioGroup = (RadioGroup) findViewById(R.id.options_radiogroup_report);

        // Set the question text
        question.setText(currentQuestion.getQuestionText());
        showAns.setText(currentQuestion.getCorrectans());
        questionpts.setText(currentQuestion.getQuestionPts()+" pts");

        // Clear the radio group and add a radio button for each answer option
        optionsRadioGroup.removeAllViews();
        String options1 = currentQuestion.getOption1();
        RadioButton radioButton1 = new RadioButton(this);
        radioButton1.setText(options1);
        radioButton1.setId(0);
        radioButton1.setEnabled(false);
        optionsRadioGroup.addView(radioButton1);

        String options2 = currentQuestion.getOption2();
        RadioButton radioButton2 = new RadioButton(this);
        radioButton2.setText(options2);
        radioButton2.setId(1);
        radioButton2.setEnabled(false);

        optionsRadioGroup.addView(radioButton2);

        String options3 = currentQuestion.getOption3();
        if(options3!=null && !options3.isEmpty()) {
            RadioButton radioButton3 = new RadioButton(this);
            radioButton3.setText(options3);
            radioButton3.setId(2);
            radioButton3.setEnabled(false);
            optionsRadioGroup.addView(radioButton3);
        }

        String options4 = currentQuestion.getOption4();
        if(options4!=null && !options4.isEmpty()) {
            RadioButton radioButton4 = new RadioButton(this);
            radioButton4.setText(options4);
            radioButton4.setId(3);
            radioButton4.setEnabled(false);
            optionsRadioGroup.addView(radioButton4);
        }
        if(currentQuestion.getradioId()!=-1)
        {
            RadioButton selectedRadioButton = findViewById(currentQuestion.getradioId());
            selectedRadioButton.setChecked(true);
        }



    }
    private void next() {
        Questions currentQuestion = questions.get(currentQuestionIndex);

            // Increment the current question index and display the next question
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayCurrentQuestion();
            } else {
                // End of quiz
                Intent intent3 = new Intent(getApplicationContext(), ScorePage.class);
                intent3.putExtra("User", Userinfo);
                intent3.putExtra("questions", questions);
                startActivity(intent3);
            }
        if(currentQuestionIndex==1)
        {
            btn_Previous.setVisibility(View.VISIBLE);
        }
    }
    private void prev() {
        Questions currentQuestion = questions.get(currentQuestionIndex);
            // Increment the current question index and display the next question
            currentQuestionIndex--;
            if (currentQuestionIndex < questions.size()) {
                displayCurrentQuestion();
            }
        if(currentQuestionIndex==0)
        {
            btn_Previous.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Next_button_report:
                next();
                break;
            case R.id.prev_button_report:
                prev();
                break;


        }
    }
}