package com.xoxoharsh.codershub.util;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.xoxoharsh.codershub.SendRequestWithoutResponseTask;

import java.util.Map;
public class Utility {
    static Map<String, Object>data;
    static int response_code;
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void updateProfile() {
        Log.d("CodersHub_Errors","Entered Update Profile");
        SendRequestWithoutResponseTask task = new SendRequestWithoutResponseTask();
        task.setCallback(new SendRequestWithoutResponseTask.Callback() {
            @Override
            public void onTaskCompleted(int responseCode) {
                Log.d("CodersHub_Errors", "Task completed with response code: " + responseCode);
                response_code = responseCode;
            }
        });
        task.execute(FirebaseUtil.currentUserEmail());
        Log.d("CodersHub_Errors","Update User Profile Ended");
    }
    public static Map<String, Object> fetchUserDataAndStartMainActivity() {
        Log.d("CodersHub_Errors","Entered FetchUserData function");
        DocumentReference userRef = FirebaseUtil.currentUserDetails();
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("CodersHub_Errors","Data fetched successfully");
                    Map<String, Object> userData = document.getData();
                    setData(userData);
                } else {
                    Log.d("CodersHub_Errors","User data not found.");
                }
            } else {
                Log.d("CodersHub_Errors","Error fetching user data.");
            }
        });
        Log.d("CodersHub_Errors","Returning Data");
        return data;
    }
    public static void setData(Map<String, Object> ans) {
        data = ans;
    }
}
