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
        if (bundle != null) {
            Log.d("CodersHub_Errors", "Received Data");
            Map<String, Object> userData = (Map<String, Object>) bundle.getSerializable("userData");
            Log.d("CodersHub_Errors", userData.toString());

            // Check if the key exists before setting the text
            if (userData.containsKey("Handle")) {
                handle.setText(userData.get("Handle").toString());
            }
            if (userData.containsKey("Problem_Solved")) {
                totalQuestions.setText(userData.get("Problem_Solved").toString());
            }
            if (userData.containsKey("Easy_Ques_Solved")) {
                easyQuestions.setText(userData.get("Easy_Ques_Solved").toString());
            }
            if (userData.containsKey("Medium_Ques_Solved")) {
                mediumQuestions.setText(userData.get("Medium_Ques_Solved").toString());
            }
            if (userData.containsKey("Hard_Ques_Solved")) {
                hardQuestions.setText(userData.get("Hard_Ques_Solved").toString());
            }
            if (userData.containsKey("Coding_Score")) {
                codingScore.setText(userData.get("Coding_Score").toString());
            }
            if (userData.containsKey("Monthly_Coding_Score")) {
                monthlyCodingScore.setText(userData.get("Monthly_Coding_Score").toString());
            }
            if (userData.containsKey("POTD_Streak")) {
                potdStreak.setText(userData.get("POTD_Streak").toString());
            }
        } else {
            Log.d("CodersHub_Errors", "Data Not received in fragment");
            Toast.makeText(getContext(), "Data Not received in fragment", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}