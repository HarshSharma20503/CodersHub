package com.xoxoharsh.codershub;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
public class SignUpActivity1 extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        Log.d("CodersHub_Errors","Entered SignupActivity1");
        button = findViewById(R.id.btn_next);
        button.setOnClickListener((v)-> startActivity(new Intent(SignUpActivity1.this,SignUpActivity2.class)));
    }
}