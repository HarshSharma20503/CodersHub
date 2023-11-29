package com.xoxoharsh.codershub;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
        Log.d("CodersHub_Errors","Entered splashActivity");
        new Handler().postDelayed(() -> {
            if (FirebaseUtil.isLoggedIn()) {
                Log.d("CodersHub_Errors","User is LoggedIn, fetching data");
                fetchUserDataAndStartMainActivity();
            } else {
                Log.d("CodersHub_Errors","User is not LoggedIn, Sending to LoginActivity");
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
                    Map<String, Object> userData = document.getData();
                    Log.d("CodersHub_Errors","User data fetched, loading data into intent");
                    Log.d("CodersHub_Errors",userData.toString());
                    startMainActivity(userData);
                } else {
                    Log.d("CodersHub_Errors","User data not found");
                    Toast.makeText(SplashActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("CodersHub_Errors","Error fetching user data");
                Toast.makeText(SplashActivity.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startMainActivity(Map<String, Object> userData) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("Data",(Serializable) userData);
        Log.d("CodersHub_Errors","Loaded data into intent, starting mainActivity");
        startActivity(intent);
        finish();
    }
}