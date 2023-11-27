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

// This is a activity which is shown when the app starts
// This shows the loading vector to give the feel that data is being loaded
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("CodersHub_Errors","Entered splashActivity");
        // A handler to to delay the function called by 1 second
        new Handler().postDelayed(() -> {
            if (FirebaseUtil.isLoggedIn()) {
                // if the user is logged in than fetch the user data and open main activity
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
                    // Convert the document to a Map
                    Map<String, Object> userData = document.getData();
                    Log.d("CodersHub_Errors","User data fetched, loading data into intent");
                    Log.d("CodersHub_Errors",userData.toString());
                    // Now, start the main activity and pass the user data
                    startMainActivity(userData);
                } else {
                    // Document doesn't exist
                    Log.d("CodersHub_Errors","User data not found");
                    Toast.makeText(SplashActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle errors
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