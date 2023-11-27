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

public class PotdFragment extends Fragment {

    TextView platform,problemStatement,constraints,examples;

    public PotdFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_p_o_t_d, container, false);

        platform = view.findViewById(R.id.platform);
        problemStatement = view.findViewById((R.id.problem_statement));
        constraints = view.findViewById((R.id.constraints));
        examples = view.findViewById(R.id.examples);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            Map<String, Object> userData = (Map<String, Object>) bundle.getSerializable("userData");
            Log.d("Harsh Error","recieved data");
            String website = ((String)bundle.getString("Platform"));
            if(website.equals("codeforces"))
                platform.setText("Codeforces");
            else if(website.equals("leetcode"))
                platform.setText("Leetcode");
            else if(website.equals("gfg"))
                platform.setText("Geeksforgeeks");

            String pStatement = ((String)userData.get("Problem Statement"));
            String example = ((String)userData.get("Examples"));
            String constraint = (String)userData.get("Constraints");
            problemStatement.setText(replaceAtExclamation(pStatement));
            examples.setText(replaceAtExclamation(example));
            constraints.setText(replaceAtExclamation(constraint));
        }
        else {
            Toast.makeText(getContext(), "Data Not recieved in fragment", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
    private static String replaceAtExclamation(String input) {
        // Use replace to replace every occurrence of "@!" with a newline character
        return input.replace("@!", "\n");
    }
}