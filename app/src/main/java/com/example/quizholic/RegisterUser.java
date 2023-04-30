package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private TextView banner,registerUser;
    private EditText lName,email,password,major,cpassword,userId;
    public EditText fName,phonenumber,UTAId;
    private ProgressBar pgrBar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private FirebaseAuth mAuth;

    //for Checkbox dropdown;
    TextView textView;
    boolean[] selectedLanguage;
    public ArrayList<Course> CourseInfo;

    public String Selectedcourse=null;
    Button btnreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        banner=(TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser=(Button) findViewById(R.id.RegisterUser);
        registerUser.setOnClickListener(this);

        fName=(EditText) findViewById(R.id.firstName);
        lName=(EditText) findViewById(R.id.lastName);
        userId=(EditText) findViewById(R.id.userId);
        email=(EditText) findViewById(R.id.email);
        major=(EditText) findViewById(R.id.course);
        password=(EditText) findViewById(R.id.password);
        cpassword=(EditText) findViewById(R.id.confirmpassword);
        radioGroup=(RadioGroup) findViewById(R.id.radio_group);
        phonenumber=(EditText) findViewById(R.id.PhoneNumber);
        UTAId=(EditText) findViewById(R.id.UTAID);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton=(RadioButton) findViewById(i);

            }
        });

        //for dropdowncheckbox;

        Resources res = getResources();
        String[] courseslst = res.getStringArray(R.array.cnames);
        textView = findViewById(R.id.textView);
        selectedLanguage = new boolean[courseslst.length];
        ArrayList<Integer> courseList = new ArrayList<>();
        CourseInfo=new ArrayList<>();
        SelectCourse(courseslst, courseList);



        pgrBar=(ProgressBar) findViewById(R.id.progressBar);


    }

    private void SelectCourse(String[] courseslst, ArrayList<Integer> courseList) {
        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUser.this);

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
                        if(radioButton.getText().toString().trim().equals("TA"))
                        {
                            if (courseList.size() == 1)
                            {

                            }
                            else {
                                Toast.makeText(RegisterUser.this,"There can be only one Course for TA.",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                            StringBuilder stringBuilder = new StringBuilder();
                            // use for loop
                            for (int j = 0; j < courseList.size(); j++) {
                                // concat array value
                                stringBuilder.append(courseslst[courseList.get(j)]);
                                Course cinfo = new Course(courseslst[courseList.get(j)], false, fName.getText().toString(), userId.getText().toString());
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.RegisterUser:
                registerUser();
               // startActivity(new Intent(this, MainActivity.class));
                //startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.signIn:
                //startActivity(new Intent(this, RegisterUser.class));
                break;

        }
    }

    private void registerUser() {
        if(radioButton==null)
        {
            Toast.makeText(RegisterUser.this,"Please select the role.",Toast.LENGTH_LONG).show();
            return;
        }
        String fNamee=fName.getText().toString().trim();
        String lNamee=lName.getText().toString().trim();
        String UserID=userId.getText().toString().trim();
        String emailid=email.getText().toString().trim();
        String majorCourse=major.getText().toString().trim();
        String pass=password.getText().toString().trim();
        String confirmPassword=cpassword.getText().toString().trim();
        String role=radioButton.getText().toString().trim();
        String phoneNumber=phonenumber.getText().toString().trim();
        String utaID=UTAId.getText().toString().trim();
        String CourseName="CSE6324";
        if(fNamee.isEmpty())
        {
            fName.setError("First Name is Required");
            fName.requestFocus();
            return;
        }
        if(lNamee.isEmpty())
        {
            lName.setError("Last Name is Required");
            lName.requestFocus();
            return;
        }
        if(emailid.isEmpty())
        {
            email.setError("EmailId is Required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
        {
            email.setError("Please provide valid Email.");
            email.requestFocus();
            return;
        }
        if(majorCourse.isEmpty())
        {
            major.setError("Marjor Course is Required");
            major.requestFocus();
            return;
        }
        if(phoneNumber.isEmpty())
        {
            major.setError("phone number is Required");
            major.requestFocus();
            return;
        }
        if(utaID.isEmpty())
        {
            major.setError("UTA Id is Required");
            major.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("password is Required");
            password.requestFocus();
            return;
        }
        if(role.isEmpty())
        {
            Toast.makeText(RegisterUser.this,"Please select the role.",Toast.LENGTH_LONG).show();
            radioGroup.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            Toast.makeText(RegisterUser.this,"Minimum password length should be 6 characters.",Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
            //password.setError("Minimum password length should be 6 characters.");
            //return;
        }
        if(confirmPassword.isEmpty())
        {

            cpassword.setError("Confirm password is Required");
            cpassword.requestFocus();
            return;

        }
        if(!pass.equals(confirmPassword))
        {
            cpassword.setError("password doesnot match.");
            cpassword.requestFocus();
            return;
        }

        pgrBar.setVisibility(View.VISIBLE);
        mAuth=FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(emailid,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            User user1=new User(fNamee,lNamee,majorCourse,emailid,pass,role,UserID,CourseInfo,Selectedcourse, new ArrayList<>(),phoneNumber,utaID,new ArrayList<>());


                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(UserID)
                                    .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(RegisterUser.this,"User has been registerd sucessfully.",Toast.LENGTH_LONG).show();
                                                pgrBar.setVisibility(View.GONE);
                                            }
                                            else{
                                                Toast.makeText(RegisterUser.this,"Failed to register! Try again!.",Toast.LENGTH_LONG).show();
                                                pgrBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                            if(role.equals("Student")) {
                                for (int i = 0; i < CourseInfo.size(); i++) {
                                    FirebaseDatabase.getInstance().getReference(CourseInfo.get(i).CoursesId)
                                            .child(UserID)
                                            .setValue(CourseInfo.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegisterUser.this, "User has been registerd sucessfully.", Toast.LENGTH_LONG).show();
                                                        pgrBar.setVisibility(View.GONE);
                                                    } else {
                                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!.", Toast.LENGTH_LONG).show();
                                                        pgrBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                }
                            }
                            if(role.equals("Professor")){
                                for (int i = 0; i < CourseInfo.size(); i++) {
                                    FirebaseDatabase.getInstance().getReference("CoursesTakenBy")
                                            .child(CourseInfo.get(i).CoursesId).setValue(UserID)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                    } else {

                                                    }
                                                }
                                            });
                                }
                            }

                        }
                        else{
                            Toast.makeText(RegisterUser.this,"Failed to register! Try again!.",Toast.LENGTH_LONG).show();
                            pgrBar.setVisibility(View.GONE);
                        }
                    }
                });
        startActivity(new Intent(this, MainActivity.class));
    }
}