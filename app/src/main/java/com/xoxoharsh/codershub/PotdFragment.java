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
            platform.setText(((String)bundle.getString("Platform")));
            problemStatement.setText(((String)userData.get("problemStatement")));
            examples.setText(((String)userData.get("examples")));
            String text  = (String)userData.get("constraints");
            constraints.setText(text);
        }
        else {
            Toast.makeText(getContext(), "Data Not recieved in fragment", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}