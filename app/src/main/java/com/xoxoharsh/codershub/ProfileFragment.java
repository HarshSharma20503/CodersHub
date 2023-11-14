package com.xoxoharsh.codershub;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class ProfileFragment extends Fragment {
    TextView platform,username,totalQuestions,easyQuestions,mediumQuestions,hardQuestions;
    TextView attemptedContests,contestRating,highestRating;


    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        platform = view.findViewById(R.id.platform);
        username = view.findViewById(R.id.username);
        totalQuestions = view.findViewById(R.id.total_question);
        easyQuestions = view.findViewById(R.id.easy_questions);
        mediumQuestions = view.findViewById(R.id.medium_questions);
        hardQuestions = view.findViewById(R.id.hard_questions);
        attemptedContests = view.findViewById(R.id.contestAttempted);
        contestRating = view.findViewById(R.id.contests_rating);
        highestRating = view.findViewById(R.id.contest_highest);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            Map<String, Object> userData = (Map<String, Object>) bundle.getSerializable("userData");
            platform.setText((String)bundle.getString("Platform"));
            username.setText((String)userData.get("handle"));
            totalQuestions.setText((String)userData.get("totalQuestions"));
            easyQuestions.setText((String)userData.get("easyQuestions"));
            mediumQuestions.setText((String)userData.get("mediumQuestions"));
            hardQuestions.setText((String)userData.get("hardQuestions"));
            attemptedContests.setText((String)userData.get("contestsAttemped"));
            contestRating.setText((String)userData.get("contestsRating"));
            highestRating.setText((String)userData.get("highestRating"));
        }
        else {
            Toast.makeText(getContext(), "Data Not recieved in fragment", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}