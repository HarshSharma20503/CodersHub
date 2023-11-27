package com.xoxoharsh.codershub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class GfgProfile extends Fragment {

    TextView handle,totalQuestions,easyQuestions,mediumQuestions,hardQuestions;
    TextView codingScore,monthlyCodingScore,potdStreak;

    public GfgProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gfg_profile, container, false);
        Log.d("CodersHub_Errors","Entered GFG Profile Fragment");

        handle = view.findViewById(R.id.username);
        totalQuestions = view.findViewById(R.id.total_question);
        easyQuestions = view.findViewById(R.id.easy_questions);
        mediumQuestions = view.findViewById(R.id.medium_questions);
        hardQuestions = view.findViewById(R.id.hard_questions);
        codingScore = view.findViewById(R.id.contestAttempted);
        monthlyCodingScore = view.findViewById(R.id.contests_rating);
        potdStreak = view.findViewById(R.id.codingScorebox);

        Bundle bundle = getArguments();
        if(bundle != null) {
            Log.d("CodersHub_Errors","Recieved Data");
            Map<String, Object> userData = (Map<String, Object>) bundle.getSerializable("userData");
            handle.setText(userData.get("Handle").toString());
            totalQuestions.setText(userData.get("Problem_Solved").toString());
            easyQuestions.setText(userData.get("Easy_Ques_Solved").toString());
            mediumQuestions.setText(userData.get("Medium_Ques_Solved").toString());
            hardQuestions.setText(userData.get("Hard_Ques_Solved").toString());
            codingScore.setText(userData.get("Coding_Score").toString());
            monthlyCodingScore.setText(userData.get("Monthly_Coding_Score").toString());
            potdStreak.setText(userData.get("POTD_Streak").toString());
        } else {
            Log.d("CodersHub_Errors","Data Not recieved in fragment");
            Toast.makeText(getContext(), "Data Not recieved in fragment", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}