package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TakeQuiz extends AppCompatActivity implements View.OnClickListener{
    private TextView quiztitle,question,questionno,questionpts;
    private Button btn_Next,btn_Previous;
    String SelectedQuiz="";
    public   User Userinfo=new User();
    private  ArrayList<Questions> questionsModelArrayList;
    private ArrayList<Questions> questions=new ArrayList<>();
    private CountDownTimer timer;
    private int currentQuestionIndex = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        questionsModelArrayList = (ArrayList<Questions>) getIntent().getSerializableExtra("questionsModelArrayList");
        Userinfo = (User) getIntent().getSerializableExtra("Userinfo");
        SelectedQuiz = getIntent().getStringExtra("SelectedQuiz");

        quiztitle=(TextView) findViewById(R.id.Quiz_title);
        question=(TextView) findViewById(R.id.question_textview);
        questionno=(TextView) findViewById(R.id.question_textview);
        questionpts=(TextView)findViewById(R.id.questionpts_textview);
        btn_Next=(Button) findViewById(R.id.Next_button);
        btn_Previous=(Button) findViewById(R.id.prev_button);
        btn_Next.setOnClickListener(this);
        btn_Previous.setOnClickListener(this);
        btn_Previous.setVisibility(View.INVISIBLE);
        quiztitle.setText(SelectedQuiz);
        for(ListQuestions q :Userinfo.LstQuestions)
        {
            if(q.CourseName.equals(Userinfo.CourcePostion) && q.QuizTitle.equals(SelectedQuiz))
            {
                questions.addAll(q.lstQuestion);
            }
        }

        currentQuestionIndex = 0;
        displayCurrentQuestion();

        startTimer();

    }
    private void startTimer() {
        timer = new CountDownTimer(60000 * Integer.parseInt(questions.get(0).getQuizTime()), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer text view with the remaining time
                TextView timerTextView = (TextView) findViewById(R.id.timer_textview);
                int minutesRemaining = (int) (millisUntilFinished / (1000 * 60));
                int secondsRemaining = (int) ((millisUntilFinished / 1000) % 60);
                timerTextView.setText("Time remaining: " + minutesRemaining + "m " + secondsRemaining + "s");
            }


            @Override
            public void onFinish() {
                // End the quiz when the timer finishes
                endQuiz();
            }
        }.start();
    }
    @SuppressLint("ResourceType")
    private void displayCurrentQuestion() {
        Questions currentQuestion = questions.get(currentQuestionIndex);

        RadioGroup optionsRadioGroup = (RadioGroup) findViewById(R.id.options_radiogroup);

        // Set the question text
        question.setText(currentQuestion.getQuestionText());
        questionpts.setText(currentQuestion.getQuestionPts()+" pts");

        // Clear the radio group and add a radio button for each answer option
        optionsRadioGroup.removeAllViews();
        String options1 = currentQuestion.getOption1();
        RadioButton radioButton1 = new RadioButton(this);
        radioButton1.setText(options1);
        radioButton1.setId(0);
        optionsRadioGroup.addView(radioButton1);

        String options2 = currentQuestion.getOption2();
        RadioButton radioButton2 = new RadioButton(this);
        radioButton2.setText(options2);
        radioButton2.setId(1);
        optionsRadioGroup.addView(radioButton2);

        String options3 = currentQuestion.getOption3();
        if(options3!=null && !options3.isEmpty()) {
            RadioButton radioButton3 = new RadioButton(this);
            radioButton3.setText(options3);
            radioButton3.setId(2);
            optionsRadioGroup.addView(radioButton3);
        }

        String options4 = currentQuestion.getOption4();
        if(options4!=null && !options4.isEmpty()) {
            RadioButton radioButton4 = new RadioButton(this);
            radioButton4.setText(options4);
            radioButton4.setId(3);
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
        RadioGroup optionsRadioGroup = (RadioGroup) findViewById(R.id.options_radiogroup);

        // Get the selected radio button ID
        int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId == -1) {
            // No option selected
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayCurrentQuestion();
            } else {
                // End of quiz
                endQuiz();
            }
        } else {
            // Option selected
            int selectedOptionIndex = selectedRadioButtonId;
            String selectedOption="";
            switch (selectedOptionIndex)
            {
                case 0:
                    selectedOption=currentQuestion.getOption1();
                    break;
                case 1:
                    selectedOption=currentQuestion.getOption2();
                    break;
                case 2:
                    selectedOption=currentQuestion.getOption3();
                    break;
                case 3:
                    selectedOption=currentQuestion.getOption4();
                    break;
            }
            //String selectedOption = currentQuestion.().get(selectedOptionIndex);

            questions.get(currentQuestionIndex).setUserans(selectedOption);
            questions.get(currentQuestionIndex).setradioId(selectedRadioButtonId);
            // Increment the current question index and display the next question
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayCurrentQuestion();
            } else {
                // End of quiz
                endQuiz();
            }
        }
        if(currentQuestionIndex==1)
        {
            btn_Previous.setVisibility(View.VISIBLE);
        }
    }
    private void prev() {
        Questions currentQuestion = questions.get(currentQuestionIndex);
        RadioGroup optionsRadioGroup = (RadioGroup) findViewById(R.id.options_radiogroup);

        // Get the selected radio button ID
        int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId == -1) {
            // No option selected
            currentQuestionIndex--;
            if (currentQuestionIndex < questions.size()) {
                displayCurrentQuestion();
            } else {
                // End of quiz
                endQuiz();
            }
        } else {
            // Option selected
            int selectedOptionIndex = selectedRadioButtonId;
            String selectedOption="";
            switch (selectedOptionIndex)
            {
                case 0:
                    selectedOption=currentQuestion.getOption1();
                    break;
                case 1:
                    selectedOption=currentQuestion.getOption2();
                    break;
                case 2:
                    selectedOption=currentQuestion.getOption3();
                    break;
                case 3:
                    selectedOption=currentQuestion.getOption4();
                    break;
            }
            //String selectedOption = currentQuestion.().get(selectedOptionIndex);

            questions.get(currentQuestionIndex).setUserans(selectedOption);
            questions.get(currentQuestionIndex).setradioId(selectedRadioButtonId);
            // Increment the current question index and display the next question
            currentQuestionIndex--;
            if (currentQuestionIndex < questions.size()) {
                displayCurrentQuestion();
            } else {
                // End of quiz
                endQuiz();
            }

        }
        if(currentQuestionIndex==0)
        {
            btn_Previous.setVisibility(View.INVISIBLE);
        }
    }
    private void endQuiz() {


        Intent intent3 = new Intent(getApplicationContext(), ScorePage.class);
        intent3.putExtra("Userinfo", Userinfo);
        intent3.putExtra("questions", questions);
        startActivity(intent3);
        // Toast.makeText(this, "Quiz ended! Your score is: " + score + " out of " + questions.size(), Toast.LENGTH_LONG).show();
        // You can also start a new activity here to display the final score and allow the user to retry the quiz
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Next_button:
                next();
                break;
            case R.id.prev_button:
                prev();
                break;


        }
    }
}