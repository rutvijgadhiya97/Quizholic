package com.example.quizholic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    EditText email;
    Button btnSend;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email=(EditText) findViewById(R.id.email);
        btnSend=(Button) findViewById(R.id.RegisterUser);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String emailid = email.getText().toString().trim();
        mAuth.sendPasswordResetEmail(emailid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this,"reset Link is sent in your email.",Toast.LENGTH_LONG).show();

                            // Password reset email sent
                        } else {
                            Toast.makeText(ResetPassword.this,"Failed to send reset Link .",Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }
}