package com.app.mooch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.app.mooch.modals.SpendingDay;

import java.util.List;

public class SpendingDayAdapter extends RecyclerView.Adapter<SpendingDayAdapter.SpendingDayView> {

    private List<SpendingDay> spendingDays;

    public SpendingDayAdapter( List<SpendingDay> spendingDays ){
        this.spendingDays = spendingDays;
    }

    @NonNull
    @Override
    public SpendingDayView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingDayView spendingDayView, int i) {

    }

    @Override
    public int getItemCount() {
        return this.spendingDays.size();
    }

    public static class SpendingDayView extends  RecyclerView.ViewHolder{
        public SpendingDayView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
