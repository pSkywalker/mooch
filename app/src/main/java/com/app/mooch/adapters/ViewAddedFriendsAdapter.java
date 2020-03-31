package com.app.mooch.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

public class ViewAddedFriendsAdapter extends RecyclerView.Adapter<ViewAddedFriendsAdapter.FriendsViewHolder> {

    private Context ctx;
    private Activity activity;
    private List<User> users;

    public ViewAddedFriendsAdapter(Context ctx, Activity activity, List<User> users ) {
        this.ctx = ctx;
        this.activity = activity;
        this.users = users;

        Log.d( "addedFriends" , "view added contructor" );
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = (View) LayoutInflater.from( viewGroup.getContext()).inflate( R.layout.adapter_friend , viewGroup,false );
        ViewAddedFriendsAdapter.FriendsViewHolder friendsViewHolder = new FriendsViewHolder(view);
        return friendsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder friendsViewHolder, int i) {
        final int currentUser = i;

        friendsViewHolder.addUserWrap.setVisibility(View.GONE);
        friendsViewHolder.userWrap.setVisibility(View.VISIBLE);

        if( users.get(i).getId() == null ) {
            friendsViewHolder.addUserWrap.setVisibility(View.VISIBLE);
            friendsViewHolder.userWrap.setVisibility(View.GONE);
        }
        else {


            friendsViewHolder.username.setText( this.users.get(i).getUsername() );

            if( users.get(i).getPhotoUri() != null ) {
                //friendsViewHolder.profilePicture.setImageBitmap();
            }

            friendsViewHolder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ( (CreateGroup)activity ).getGroup().removeGroupMember( users.get( currentUser ) );
                    users.get(currentUser).setSelected(false);
                    ( (CreateGroup)activity).updateView();
                }
            });


        }


    }

    public void update(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout userWrap;
        private ConstraintLayout addUserWrap;

        private ImageView profilePicture;
        private TextView username;
        private ImageView remove;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.userWrap = (ConstraintLayout) itemView.findViewById(R.id.user);
            this.addUserWrap = (ConstraintLayout) itemView.findViewById(R.id.addUsers);

            this.profilePicture = itemView.findViewById(R.id.friendViewProfilePicture);
            this.username = itemView.findViewById(R.id.friendViewUsername);
            this.remove = itemView.findViewById(R.id.friendViewClose);
        }
    }
}
