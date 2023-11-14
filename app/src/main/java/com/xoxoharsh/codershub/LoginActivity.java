package com.xoxoharsh.codershub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.xoxoharsh.codershub.util.FirebaseUtil;
import com.xoxoharsh.codershub.util.Utility;

import java.io.Serializable;
import java.util.Map;

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

            if(task.isSuccessful()){
                //login is success
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    //go to main activity

                    fetchUserDataAndStartMainActivity();

                }else{
                    Utility.showToast(LoginActivity.this,"Email not verified, Please verify your email.");
                }
            }else{
                //login failed
                changeInProgress(false);
                Utility.showToast(LoginActivity.this,task.getException().getLocalizedMessage());
            }
        });
    }

    private void fetchUserDataAndStartMainActivity() {
        DocumentReference userRef = FirebaseUtil.currentUserDetails();

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Convert the document to a Map
                    Map<String, Object> userData = document.getData();

                    // Now, start the main activity and pass the user data
                    startMainActivity(userData);
                } else {
                    // Document doesn't exist
                    Toast.makeText(LoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle errors
                Toast.makeText(LoginActivity.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMainActivity(Map<String, Object> userData) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Data",(Serializable) userData);
        startActivity(intent);
        finish(); // Optional: finish the current activity
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