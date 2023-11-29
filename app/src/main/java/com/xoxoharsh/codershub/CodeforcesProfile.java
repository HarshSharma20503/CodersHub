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
public class CodeforcesProfile extends Fragment {
    TextView handle,currentRank,maxRank,currentRating,maxRating,questionSolved,maxCodingStreak,noOfContribution,noOfFriends;
    public CodeforcesProfile() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_codeforces_profile, container, false);
        Log.d("CodersHub_Errors","Entered Codeforces Profile Fragment");
        handle = view.findViewById(R.id.username);
        currentRank = view.findViewById(R.id.currentRank);
        maxRank = view.findViewById(R.id.maxRank);
        currentRating = view.findViewById(R.id.currentRating);
        maxRating = view.findViewById(R.id.maxRating);
        questionSolved = view.findViewById(R.id.questionsSolved);
        maxCodingStreak = view.findViewById(R.id.maxcodingStreakbox);
        noOfContribution = view.findViewById(R.id.noofcontributions);
        noOfFriends = view.findViewById(R.id.nooffriends);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.d("CodersHub_Errors", "Received Data");
            Map<String, Object> userData = (Map<String, Object>) bundle.getSerializable("userData");
            Log.d("CodersHub_Errors", userData.toString());
            if (userData.containsKey("Handle")) {
                handle.setText(userData.get("Handle").toString());
            }
            if (userData.containsKey("Current_Rank")) {
                currentRank.setText(userData.get("Current_Rank").toString());
            }
            if (userData.containsKey("Max_Rank")) {
                maxRank.setText(userData.get("Max_Rank").toString());
            }
            if (userData.containsKey("Current_Rating")) {
                currentRating.setText(userData.get("Current_Rating").toString());
            }
            if (userData.containsKey("Max_Rating")) {
                maxRating.setText(userData.get("Max_Rating").toString());
            }
            if (userData.containsKey("Number_Of_Ques_solved")) {
                questionSolved.setText(userData.get("Number_Of_Ques_solved").toString());
            }
            if (userData.containsKey("Max_Streak")) {
                maxCodingStreak.setText(userData.get("Max_Streak").toString());
            }
            if (userData.containsKey("Number_Of_Contributions")) {
                noOfContribution.setText(userData.get("Number_Of_Contributions").toString());
            }
            if (userData.containsKey("Number_Of_Friends")) {
                noOfFriends.setText(userData.get("Number_Of_Friends").toString());
            }
        } else {
            Log.d("CodersHub_Errors", "Data Not received in fragment");
            Toast.makeText(getContext(), "Data Not received in fragment", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}