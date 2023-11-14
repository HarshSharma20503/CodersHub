package com.xoxoharsh.codershub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.xoxoharsh.codershub.util.FirebaseUtil;

import java.io.Serializable;
import java.util.Map;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (FirebaseUtil.isLoggedIn()) {
                fetchUserDataAndStartMainActivity();
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }, 1000);
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
                    Toast.makeText(SplashActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle errors
                Toast.makeText(SplashActivity.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMainActivity(Map<String, Object> userData) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("Data",(Serializable) userData);
        startActivity(intent);
        finish(); // Optional: finish the current activity
    }

}