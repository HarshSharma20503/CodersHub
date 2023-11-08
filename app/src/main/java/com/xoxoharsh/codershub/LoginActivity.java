package com.xoxoharsh.codershub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.xoxoharsh.codershub.util.Utility;

public class LoginActivity extends AppCompatActivity {

    TextView signUpBtn;
    Button loginBtn;
    EditText emailEditText,passwordEditText;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpBtn = findViewById(R.id.SignUp);
        emailEditText = findViewById(R.id.emailId);
        passwordEditText = findViewById(R.id.password);
        progressBar = findViewById(R.id.Progress_Bar);
        loginBtn = findViewById(R.id.LogIn);

        signUpBtn.setOnClickListener((v)-> startActivity(new Intent(LoginActivity.this, SignUpActivity1.class)));

        loginBtn.setOnClickListener((v)-> loginUser() );
    }

    void loginUser(){
        String email  = emailEditText.getText().toString();
        String password  = passwordEditText.getText().toString();
        boolean isValidated = validateData(email,password);
        if(!isValidated){
            return;
        }
        loginAccountInFirebase(email,password);
    }

    void loginAccountInFirebase(String email,String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            changeInProgress(false);
            if(task.isSuccessful()){
                //login is success
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    //go to main activity
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    Utility.showToast(LoginActivity.this,"Email not verified, Please verify your email.");
                }
            }else{
                //login failed
                Utility.showToast(LoginActivity.this,task.getException().getLocalizedMessage());
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }
    boolean validateData(String email,String password){
        //validate the data that are input by user.

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is invalid");
            return false;
        }
        if(password.length()<6){
            passwordEditText.setError("Password length is invalid");
            return false;
        }
        return true;
    }
}