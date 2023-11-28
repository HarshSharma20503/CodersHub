package com.xoxoharsh.codershub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xoxoharsh.codershub.model.Contest;

import java.util.List;
public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestViewHolder>{
    private final List<Contest> contestList;
    public ContestAdapter(List<Contest>contestList) {
        this.contestList = contestList;
    }
    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contest, parent, false);
        return new ContestViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ContestAdapter.ContestViewHolder holder, int position) {
        Contest contest = contestList.get(position);
        holder.titleTextView.setText(contest.getTitle());
        holder.platformTextView.setText(contest.getPlatform());
        holder.timeTextView.setText(contest.getTime());
        holder.dateTextView.setText(contest.getDate());
    }
    @Override
    public int getItemCount() {
        return contestList.size();
    }
    public static class ContestViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView platformTextView;
        public TextView timeTextView;
        public TextView dateTextView;
        public ContestViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            platformTextView = itemView.findViewById(R.id.platformTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
