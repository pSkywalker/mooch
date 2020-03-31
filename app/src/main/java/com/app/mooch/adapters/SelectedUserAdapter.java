package com.app.mooch.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mooch.CreateGroup;
import com.app.mooch.R;
import com.app.mooch.modals.User;

import java.util.List;

public class SelectedUserAdapter extends RecyclerView.Adapter<SelectedUserAdapter.SelectedViewHolder>{

    private Context ctx;
    private Activity activity;
    private List<User> users;



    public SelectedUserAdapter( Context ctx , Activity activity, List<User> users){
        this.ctx = ctx;
        this.activity = activity;
        this.users = users;
    }


    public void update(){
        Log.d( "selectedUserAdapter" , String.valueOf(this.users.size()) );
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SelectedUserAdapter.SelectedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.adapter_layout_user_selected , viewGroup,false );
        SelectedUserAdapter.SelectedViewHolder selectedViewHolder = new SelectedUserAdapter.SelectedViewHolder(view);
        return selectedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedUserAdapter.SelectedViewHolder selectedViewHolder, int i) {

        final int currentUserId = i;

        selectedViewHolder.username.setText(this.users.get(i).getUsername());
        selectedViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateGroup) activity).getGroup().removeGroupMember( users.get(currentUserId) );
                ((CreateGroup) activity).updateView();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class SelectedViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private ImageView remove;

        public SelectedViewHolder(@NonNull View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.selectedUser_username);
            remove = (ImageView) itemView.findViewById(R.id.selectedUser_close);

        }
    }

}
