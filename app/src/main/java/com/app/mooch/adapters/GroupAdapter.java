package com.app.mooch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.app.mooch.modals.Group;

import java.util.List;

public class GroupAdapter  extends RecyclerView.Adapter<GroupAdapter.GroupView> {

    private List<Group> allGroups;

    public GroupAdapter( List<Group> allGroup ){
        this.allGroups = allGroups;
    }

    @NonNull
    @Override
    public GroupView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupView groupView, int i) {

    }

    @Override
    public int getItemCount() {
        return allGroups.size();
    }



    public static class GroupView extends RecyclerView.ViewHolder {

        public GroupView(@NonNull View itemView) {
            super(itemView);
        }
    }


}
