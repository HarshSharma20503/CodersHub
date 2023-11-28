package com.xoxoharsh.codershub;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xoxoharsh.codershub.model.Contest;
import java.util.List;
public class ContestsFragment extends Fragment {
    private List<Contest> contestList;
    public ContestsFragment() {
    }
    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contests, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ContestAdapter contestAdapter = new ContestAdapter(contestList);
        recyclerView.setAdapter(contestAdapter);
        return view;
    }
}