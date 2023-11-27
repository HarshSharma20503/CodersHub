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

public class LeetcodeProfile extends Fragment {
    TextView handle,totalQuestions,easyQuestions,mediumQuestions,hardQuestions;
    TextView attempted,Rating,noOfBadges,globalRank;
    public LeetcodeProfile() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leetcode_profile, container, false);

        Log.d("CodersHub_Errors","Entered Leetcode Profile Fragment");

        handle = view.findViewById(R.id.username);
        totalQuestions = view.findViewById(R.id.total_question);
        easyQuestions = view.findViewById(R.id.easy_questions1);
        mediumQuestions = view.findViewById(R.id.medium_questions1);
        hardQuestions = view.findViewById(R.id.hard_questions1);
        attempted = view.findViewById(R.id.contestAttempted1);
        Rating = view.findViewById(R.id.contests_rating);
        noOfBadges = view.findViewById(R.id.noofbadges);
        globalRank = view.findViewById(R.id.globalrankbox);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            Log.d("CodersHub_Errors","Recieved Data");
            Map<String, Object> userData = (Map<String, Object>) bundle.getSerializable("userData");
            Log.d("CodersHub_Errors",userData.toString());
            handle.setText((String)userData.get("Handle"));
            totalQuestions.setText((String)userData.get("Number_Of_Ques_solved"));

            easyQuestions.setText(userData.get("Easy_Ques_Solved").toString());
            mediumQuestions.setText(userData.get("Medium_Ques_Solved").toString());
            hardQuestions.setText(userData.get("Difficult_Ques_Solved").toString());
            attempted.setText(userData.get("Contest_Attempted").toString());
            Rating.setText((String)userData.get("Current_Rating"));
            noOfBadges.setText((String)userData.get("Number_Of_Badges"));
            globalRank.setText((String)userData.get("Global_Rank_In_Contest"));
        }
        else {
            Log.d("CodersHub_Errors","Data Not recieved in fragment");
            Toast.makeText(getContext(), "Data Not recieved in fragment", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}