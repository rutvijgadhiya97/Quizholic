package com.example.quizholic;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;


public class Publish_Quiz extends AppCompatActivity implements AdapterView.OnItemSelectedListener , View.OnClickListener {

    private TextView banner,registerUser;
    public EditText quiztitle,question,option1,option2,option3,option4,correctans,quizTime,questionPts,time;
    TextView textView;
    boolean[] selectedLanguage;
    public ArrayList<Course> CourseInfo;
    public User userinfo=new User();
    public Spinner courselst;
    private Button btnAdd, btnview;
    public String selectedcourse=null;
    Button btnDatePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay,hr,min;
    String sdelectedcourse=null;

    public ArrayList<Questions> QuestionList=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_quiz);
        userinfo = (User) getIntent().getSerializableExtra("User");
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnAdd=(Button)findViewById(R.id.add);
        btnview=(Button)findViewById(R.id.view);
        txtDate=(EditText)findViewById(R.id.date);
        time=(EditText)findViewById(R.id.time);
        btnview.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        quiztitle=(EditText)findViewById(R.id.quiztitle);
        question=(EditText)findViewById(R.id.question);
        option1=(EditText)findViewById(R.id.option1);
        option2=(EditText)findViewById(R.id.option2);
        option3=(EditText)findViewById(R.id.option3);
        option4=(EditText)findViewById(R.id.option4);
        correctans=(EditText)findViewById(R.id.correctans);
        quizTime=(EditText)findViewById(R.id.quizTime);
        questionPts=(EditText)findViewById(R.id.questionpts);
        SelectCourse();
    }
    @Override
    public void onClick(View view) {
        if (view == btnDatePicker) {

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
                            quizTime.setText(hourOfDay+":"+minute);
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
        if(view == btnAdd) {

            if (Addquestion()) return;
        }
        if(view== btnview)
        {
            Intent intent = new Intent(getApplicationContext(), view_Questions.class);
            intent.putExtra("Questions", QuestionList);
            intent.putExtra("User",userinfo);
            startActivity(intent);
        }
    }

    private boolean Addquestion() {
        String Quiztitle = quiztitle.getText().toString().trim();
        String Question = question.getText().toString().trim();
        String Option1 = option1.getText().toString().trim();
        String Option2 = option2.getText().toString().trim();
        String Option3 = option3.getText().toString().trim();
        String Option4 = option4.getText().toString().trim();
        String Answer = correctans.getText().toString().trim();
        String date=txtDate.getText().toString().trim();
        String QuizTime=quizTime.getText().toString().trim();
        String QuestionPts=questionPts.getText().toString().trim();

        // validate user input
        if (TextUtils.isEmpty(Question) || TextUtils.isEmpty(Option1)
                || TextUtils.isEmpty(Option2) || TextUtils.isEmpty(Quiztitle)
                || TextUtils.isEmpty(Answer) || TextUtils.isEmpty(date) ||TextUtils.isEmpty(QuestionPts) || TextUtils.isEmpty(QuizTime) || selectedcourse.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return true;
        }

        else {
            // create a new Question object
            Questions newQuestion = new Questions(Quiztitle, Question, Option1, Option2, Option3, Option4, Answer,date,selectedcourse,"",-1,QuizTime,QuestionPts,hr,min,"0");

            // save the new question to the database or wherever you store your questions
            QuestionList.add(newQuestion);
            // display a success message
            Toast.makeText(getApplicationContext(), "Question added successfully", Toast.LENGTH_SHORT).show();

            // clear the input fields

            question.setText("");
            option1.setText("");
            option2.setText("");
            option3.setText("");
            option4.setText("");
            correctans.setText("");

        }
        return false;
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
            selectedcourse = course;
        }
        Toast.makeText(parent.getContext(), "Selected: " + course, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}