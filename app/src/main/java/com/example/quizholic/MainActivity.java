package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText _editUserId,_editPassword;
    private Button signIn;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private DatabaseReference userlistReference;
    private FirebaseAuth mAuth;
    private ProgressBar pgrBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn=(Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        _editUserId=(EditText) findViewById(R.id.UserId);
        _editPassword=(EditText) findViewById(R.id.password);

        radioGroup=(RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton=(RadioButton) findViewById(i);

            }
        });

        pgrBar=(ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.forgetpassword:
                //startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.signIn:
                userLogin();
                break;

        }
    }

    private void userLogin() {
        String UserId=_editUserId.getText().toString().trim();
        String password=_editPassword.getText().toString().trim();
        String role=radioButton.getText().toString().trim();
        String Email = null;
        final String[] email = new String[1];


        if(UserId.isEmpty())
        {
            _editUserId.setError("UserID is Required");
            _editUserId.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            _editPassword.setError("Password is Required");
            _editPassword.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            _editPassword.setError("Minimum password length should be 6 characters.");
            _editPassword.requestFocus();
            return;
        }
        switch (role) {
            case "Student":
                Email=UserId+"@mavs.uta.edu";
                break;
            case "Professor":
            case "TA":
                Email=UserId+"@gmail.com";
                break;
        }
        mAuth=FirebaseAuth.getInstance();
        pgrBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser fbUser=FirebaseAuth.getInstance().getCurrentUser();
                    //if(fbUser.isEmailVerified()) {
                    userlistReference = FirebaseDatabase.getInstance().getReference("Users");
                    userlistReference.child(UserId)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User userlist = snapshot.getValue(User.class);
                                    switch (role) {
                                        case "Student":
                                            Intent intent1 = new Intent(getApplicationContext(), StudentDashboard.class);
                                            intent1.putExtra("User", userlist);
                                            startActivity(intent1);
                                            break;
                                        case "Professor":
                                            Intent intent = new Intent(getApplicationContext(), ProfessorOrTADashboard.class);
                                            intent.putExtra("User", userlist);
                                            startActivity(intent);
                                            break;
                                        case "TA":
                                            break;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                    //  }
                    //  else{fbUser.sendEmailVerification();
                    //    Toast.makeText(MainActivity.this,"Check your email to verify your account.",Toast.LENGTH_LONG).show();
                    //  pgrBar.setVisibility(View.GONE);
                    //}
                }
                else{
                    Toast.makeText(MainActivity.this,"Failed to login! Email or Password is wrong.",Toast.LENGTH_LONG).show();
                    pgrBar.setVisibility(View.GONE);
                }
            }
        });
    }
}