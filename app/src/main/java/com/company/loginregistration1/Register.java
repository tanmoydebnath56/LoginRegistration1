package com.company.loginregistration1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText email,fullName,password,phoneNumber;
    Button registerButton;
    FirebaseAuth fAuth;
    ProgressBar progressbar;

    TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.Email);
        fullName = findViewById(R.id.fullName);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phone);
        loginButton = findViewById(R.id.createText);
        registerButton = findViewById(R.id.registerBtn);
        fAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //It's used for going to next activity.
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is Required.");
                    return;
                }

                if(Password.length() < 6){
                    password.setError("Length should be more than 6.");
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                //User registration from now on in FireBase.

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"Registration successful, complete the login process", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{

                            Toast.makeText(Register.this,"Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });



            }
        });
    }
}
