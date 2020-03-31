package com.app.mooch.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.mooch.CreateGroup;
import com.app.mooch.R;
import com.app.mooch.adapters.ViewAddedFriendsAdapter;

import java.io.InputStream;

public class GroupSettings extends Fragment {

    private RecyclerView groupMembers;
    private RecyclerView.LayoutManager groupMembersLayoutManager;
    private ViewAddedFriendsAdapter viewAddedFriendsAdapter;

    private EditText groupName;
    private ImageView groupImage;

    private Button back;
    private Button createGroup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_group_settings, container, false);

        groupName = (EditText) rootView.findViewById(R.id.groupName);

        back = (Button) rootView.findViewById(R.id.backToAddFriends);
        createGroup = (Button) rootView.findViewById(R.id.next);


        groupMembers = (RecyclerView) rootView.findViewById(R.id.addedUsers);
        groupMembersLayoutManager = new LinearLayoutManager( getContext() , LinearLayoutManager.HORIZONTAL , false);
        viewAddedFriendsAdapter = new ViewAddedFriendsAdapter( getContext() , getActivity(),
                ((CreateGroup) getActivity()).getGroup().getGroupMembers() );
        groupMembers.setLayoutManager( groupMembersLayoutManager );
        groupMembers.setAdapter( viewAddedFriendsAdapter );


        groupName = (EditText) rootView.findViewById(R.id.groupName);
        groupImage = (ImageView) rootView.findViewById(R.id.groupImage);


        groupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateGroup) getActivity()).moveView(0);
            }
        });

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( groupName.getText().toString().length() > 0 ) {

                }
            }
        });

        return rootView;
    }


    public ViewAddedFriendsAdapter getViewAddedFriendsAdapter(){
        return this.viewAddedFriendsAdapter;
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == 0) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                groupImage.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {

        }
    }

}
