package com.xoxoharsh.codershub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;
import com.xoxoharsh.codershub.model.CodeforcesModel;
import com.xoxoharsh.codershub.model.GfgModel;
import com.xoxoharsh.codershub.model.LeetcodeModel;
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

        findViewById(R.id.backbutton).setOnClickListener((v)->{
            finish();
        });

        findViewById(R.id.updatehandlebutton).setOnClickListener((v)-> updateHandles());
        findViewById(R.id.updatepasswordbutton).setOnClickListener((v)-> updatePassword());
    }
    public void updateHandles(){
        String newCfHandle = CodeforcesHandle.getText().toString().trim();
        String newLHandle = LeetcodeHandle.getText().toString().trim();
        String newGfgHandle = GfgHandle.getText().toString().trim();

        // Update the document with new handles
        DocumentReference userDocRef = FirebaseUtil.currentUserDetails();
        Map<String, Object> updatedHandles = new HashMap<>();

        if (!newCfHandle.isEmpty()) {
            updatedHandles.put("Codeforces", new CodeforcesModel(newCfHandle));
        }
        if (!newLHandle.isEmpty()) {
            updatedHandles.put("Leetcode", new LeetcodeModel(newLHandle));
        }
        if (!newGfgHandle.isEmpty()) {
            updatedHandles.put("Geeksforgeek", new GfgModel(newGfgHandle));
        }

        userDocRef.set(updatedHandles, SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
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
                String newPassword = pass;
                user.updatePassword(newPassword)
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
                // Handle the case where the user is not signed in
                Toast.makeText(SettingsActivity.this,
                        "User not signed in", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Not valid Password", Toast.LENGTH_SHORT).show();
        }
    }
}