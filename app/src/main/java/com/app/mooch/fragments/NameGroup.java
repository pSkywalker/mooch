package com.app.mooch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.mooch.CreateGroup;
import com.app.mooch.R;
import com.app.mooch.adapters.ViewAddedFriendsAdapter;

public class NameGroup extends Fragment {

    private EditText groupName;

    private RecyclerView groupMembers;
    private RecyclerView.LayoutManager groupMembersLayoutManager;
    private ViewAddedFriendsAdapter viewAddedFriendsAdapter;

    private Button back;
    private Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_name_group, container, false);

        groupName = (EditText) rootView.findViewById(R.id.groupName);

        back = (Button) rootView.findViewById(R.id.backToAddFriends);
        next = (Button) rootView.findViewById(R.id.next);


        groupMembers = (RecyclerView) rootView.findViewById(R.id.addedUsers);
        groupMembersLayoutManager = new LinearLayoutManager( getContext() , LinearLayoutManager.HORIZONTAL , false);
        viewAddedFriendsAdapter = new ViewAddedFriendsAdapter( getContext() , getActivity(),
                ((CreateGroup) getActivity()).getGroup().getGroupMembers() );
        groupMembers.setLayoutManager( groupMembersLayoutManager );
        groupMembers.setAdapter( viewAddedFriendsAdapter );



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateGroup) getActivity()).moveView(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( groupName.getText().toString().length() > 0 ) {
                    ((CreateGroup) getActivity()).moveView(2);
                }
            }
        });


        return rootView;
    }


    public ViewAddedFriendsAdapter getViewAddedFriendAdapter(){
        return viewAddedFriendsAdapter;
    }



}
