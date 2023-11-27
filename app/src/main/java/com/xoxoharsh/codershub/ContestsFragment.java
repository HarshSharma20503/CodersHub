package com.xoxoharsh.codershub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.xoxoharsh.codershub.model.Contest;
import com.xoxoharsh.codershub.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContestsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContestAdapter contestAdapter;
    private List<Contest> contestList;
    Map<String, Object> contestsItems;
    public ContestsFragment() {
    }

    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contests, container, false);
        recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        contestAdapter = new ContestAdapter(contestList);
        recyclerView.setAdapter(contestAdapter);

        return view;
    }

}