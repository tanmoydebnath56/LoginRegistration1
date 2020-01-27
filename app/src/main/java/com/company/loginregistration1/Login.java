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

public class Login extends AppCompatActivity {

    EditText email,password;
    TextView newUser;
    FirebaseAuth fAuth;
    ProgressBar progressbar;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        newUser = findViewById(R.id.createText);
        fAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar);
        login = findViewById(R.id.loginBtn);

        login.setOnClickListener(new View.OnClickListener() {
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

                //Authenticating the user using fAuth variables.

                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Successful, Welcome user", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this,"Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //It's used for going to next activity.
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}
