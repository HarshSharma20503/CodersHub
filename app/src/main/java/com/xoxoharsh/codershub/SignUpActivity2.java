package com.xoxoharsh.codershub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity2 extends AppCompatActivity {
    Button button;
    EditText codeforcesHandle,leetcodeHandle,gfgHandle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        Log.d("CodersHub_Errors","Entered SignupActivity2");

        codeforcesHandle = findViewById(R.id.codeforces_handle);
        leetcodeHandle = findViewById(R.id.leetcode_handle);
        gfgHandle = findViewById(R.id.gfg_handle);
        button = findViewById(R.id.btn_next);

        button.setOnClickListener((v)->{
            String cfHandle = codeforcesHandle.getText().toString();
            String lHandle = leetcodeHandle.getText().toString();
            String gHandle = gfgHandle.getText().toString();

            if(gHandle.isEmpty() && cfHandle.isEmpty() && lHandle.isEmpty()) {
                Toast.makeText(this, "Please Enter at least one handle.", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(SignUpActivity2.this,SignUpActivity3.class);
                intent.putExtra("codeforces",cfHandle);
                intent.putExtra("leetcode",lHandle);
                intent.putExtra("gfg",gHandle);
                startActivity(intent);
            }
        });
    }
}