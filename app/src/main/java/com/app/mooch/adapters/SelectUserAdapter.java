package com.app.mooch.adapters;

import android.app.Activity;
import android.content.Context;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mooch.CreateGroup;
import com.app.mooch.R;
import com.app.mooch.modals.User;

import java.util.List;

public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.UserViewHolder> {

    private Context ctx;
    private Activity activity;
    private List<User> users;


    public SelectUserAdapter( Context ctx , Activity activity, List<User> users ) {
        this.users = users;
        this.ctx = ctx;
        this.activity = activity;

        Log.d("contacts" , this.users.get(1).getUsername());
    }

    public List<User> getDisplayedUsers(){
        return this.users;
    }

    public void newUserList( List<User> users ){
        this.users = users;
        updated();
    }

    public void updated(){
        Log.d( "updatedAdapter" , String.valueOf(this.users.size()) );
        for( int x = 0; x < this.users.size(); x++ ) {
            Log.d( "updatedAdapter" , String.valueOf(this.users.get(x).isRegistered()) );
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.adapter_layout_user_search_card , viewGroup,false );
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    public void update(){
        notifyDataSetChanged();
    };

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder userViewHolder, final int i) {

        final int currentUserId = i;

        userViewHolder.username.setText(users.get(i).getUsername());
        userViewHolder.email.setText(users.get(i).getEmail());
        if( users.get(i).getPhotoUri() != null ) {
            try{
                userViewHolder.profilePicture.setImageBitmap(MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), users.get(i).getPhotoUri()));
            }
            catch( Exception ex ){}
        }

        //Log.d( "userregistercheck" , String.valueOf(users.get(i).isSelected()) );

        if( !users.get(i).isSelected() ) {
            if( users.get(i).isRegistered() ) {
                userViewHolder.add.setVisibility(View.VISIBLE);
                userViewHolder.invite.setVisibility(View.GONE);
                userViewHolder.remove.setVisibility(View.GONE);
            }
            else {
                userViewHolder.add.setVisibility(View.GONE);
                userViewHolder.invite.setVisibility(View.VISIBLE);
                userViewHolder.remove.setVisibility(View.GONE);
            }
        }
        else {
            userViewHolder.add.setVisibility(View.GONE);
            userViewHolder.invite.setVisibility(View.GONE);
            userViewHolder.remove.setVisibility(View.VISIBLE);
        }

        //userViewHolder.profilePicture.setBitmap

        /*
        for( int x = 0; x < this.selectedUsers.size(); x++) {
            if( users.get(i).getId().equals( this.selectedUsers.get(x) ) ) {
                userViewHolder.selected.setVisibility( View.VISIBLE );
            }
        }
        */


        userViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateGroup) activity).getGroup().addGroupMember( users.get(currentUserId) );
                users.get(currentUserId).setSelected(true);
                ((CreateGroup) activity).updateView();
                notifyItemChanged( currentUserId );
            }
        });

        userViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateGroup) activity).getGroup().removeGroupMember( users.get(currentUserId) );
                ((CreateGroup) activity).updateView();
                users.get(currentUserId).setSelected(false);
                notifyItemChanged( currentUserId );
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView username;
        public TextView email;
        public ImageView profilePicture;

        public Button invite;
        public Button add;
        public Button remove;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.findUser_card);
            username = (TextView) itemView.findViewById(R.id.findUser_username);
            email = (TextView) itemView.findViewById(R.id.findUser_email);
            profilePicture = (ImageView) itemView.findViewById(R.id.findUser_profilePic);
            invite = (Button) itemView.findViewById(R.id.findUser_invite);
            add = (Button) itemView.findViewById(R.id.findUser_addFriend);
            remove = (Button) itemView.findViewById(R.id.findUser_remove);

        }

    }

}
