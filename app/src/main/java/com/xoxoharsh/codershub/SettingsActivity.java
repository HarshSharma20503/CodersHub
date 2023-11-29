package com.xoxoharsh.codershub;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.xoxoharsh.codershub.util.FirebaseUtil;

import java.util.HashMap;
import java.util.Map;
public class SettingsActivity extends AppCompatActivity {
    String gfgHandle,leetcodeHandle,codeforcesHandle;
    EditText GfgHandle,LeetcodeHandle,CodeforcesHandle,ChangePassword,ConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d("CodersHub_Errors","Entered SettingsActivity");
        gfgHandle = getIntent().getStringExtra("gfgHandle");
        leetcodeHandle = getIntent().getStringExtra("leetcodeHandle");
        codeforcesHandle = getIntent().getStringExtra("codeforcesHandle");
        GfgHandle = findViewById(R.id.gfg_handle);
        LeetcodeHandle = findViewById(R.id.leetcode_handle);
        CodeforcesHandle = findViewById(R.id.codeforces_handle);
        ChangePassword = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmPassword);
        if(gfgHandle != null) {
            GfgHandle.setText(gfgHandle);
        }
        if(leetcodeHandle != null) {
            LeetcodeHandle.setText(leetcodeHandle);
        }
        if(codeforcesHandle != null) {
            CodeforcesHandle.setText(codeforcesHandle);
        }
        findViewById(R.id.backbutton).setOnClickListener((v)-> finish());
        findViewById(R.id.updatehandlebutton).setOnClickListener((v)-> updateHandles());
        findViewById(R.id.updatepasswordbutton).setOnClickListener((v)-> updatePassword());
    }
    public void updateHandles(){
        String newCfHandle = CodeforcesHandle.getText().toString().trim();
        String newLHandle = LeetcodeHandle.getText().toString().trim();
        String newGfgHandle = GfgHandle.getText().toString().trim();
        Log.d("CodersHub_Errors", "Clicked Handles apply button");
        DocumentReference userDocRef = FirebaseUtil.currentUserDetails();
        Map<String, Object> updatedHandles = new HashMap<>();
        if (!newCfHandle.isEmpty()) {
            Map<String,Object>cf = new HashMap<>();
            cf.put("Handle",newCfHandle);
            updatedHandles.put("codeforces", cf);
        }
        if (!newLHandle.isEmpty()) {
            Map<String,Object>le = new HashMap<>();
            le.put("Handle",newLHandle);
            updatedHandles.put("leetcode", le);
        }
        if (!newGfgHandle.isEmpty()) {
            Map<String,Object>gfg = new HashMap<>();
            gfg.put("Handle",newGfgHandle);
            updatedHandles.put("gfg", gfg);
        }
        userDocRef.set(updatedHandles)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SendRequestWithoutResponseTask task2 = new SendRequestWithoutResponseTask();
                        task2.setCallback(new SendRequestWithoutResponseTask.Callback() {
                            @Override
                            public void onTaskCompleted(int responseCode) {
                                Log.d("CodersHub_Errors", "Task completed with response code: " + responseCode);
                            }
                        });
                        Toast.makeText(SettingsActivity.this, "Scrapping your Data please wait for few seconds", Toast.LENGTH_LONG).show();
                        task2.execute(FirebaseUtil.currentUserEmail());
                        Log.d("CodersHub_Errors","Finish seding request");
                        Toast.makeText(SettingsActivity.this, "Handles updated successfully, Please Login again to see effect", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SettingsActivity.this, "Failed to update handles", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    boolean validateData(String password1,String confirmPassword1){
        if(password1.length()<6){
            ChangePassword.setError("Password length is invalid");
            return false;
        }
        if(!password1.equals(confirmPassword1)){
            ConfirmPassword.setError("Password not matched");
            return false;
        }
        return true;
    }
    public void updatePassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String pass = ChangePassword.getText().toString();
        String confirmPass = ChangePassword.getText().toString();
        if(validateData(pass,confirmPass)) {
            if (user != null) {
                user.updatePassword(pass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SettingsActivity.this,
                                            "Password changed successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SettingsActivity.this,
                                            "Failed to change password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(SettingsActivity.this,"User not signed in", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Not valid Password", Toast.LENGTH_SHORT).show();
        }
    }
}