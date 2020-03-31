package com.app.mooch.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.mooch.CreateGroup;
import com.app.mooch.R;
import com.app.mooch.adapters.SelectUserAdapter;
import com.app.mooch.adapters.SelectedUserAdapter;
import com.app.mooch.modals.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddFriends extends Fragment {

    private Button next;

    private EditText userSearch;

    private RecyclerView selectedUsers;
    private RecyclerView.LayoutManager selectedUsersLayoutManager;
    private SelectedUserAdapter selectedUserAdapter;

    private RecyclerView searchResults;
    private RecyclerView.LayoutManager searchResultsLayoutManager;
    private SelectUserAdapter selectUserAdapter;


// phone number verification when sign in
    // save phone number in the user object


    // when retriving contacts search all users for phone number ?

    // when displaying contacts
        // if the user is registed then show an add button

        // if not then show the user with the ability to text

    private FirebaseFirestore db;
    private CollectionReference userCollectionRef;

    private List<User> contactUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_find_friends, container, false);

        db = FirebaseFirestore.getInstance();
        userCollectionRef = db.collection("users");


        contactUsers = new ArrayList<>();

        userSearch = (EditText) rootView.findViewById(R.id.searchUsername);

        searchResults = (RecyclerView) rootView.findViewById(R.id.userSearchResults);
        searchResultsLayoutManager = new LinearLayoutManager(getContext());
        searchResults.setLayoutManager( searchResultsLayoutManager );

        next = (Button) rootView.findViewById(R.id.next);


        //((CreateGroup) getActivity()).getGroup().addGroupMember( new User(FirebaseAuth.getInstance().getUid()) );
        getUserDetails( FirebaseAuth.getInstance().getUid()  );

        selectedUsers = (RecyclerView) rootView.findViewById(R.id.addFriends);
        selectedUsersLayoutManager = new LinearLayoutManager( getContext(), LinearLayout.HORIZONTAL, false);
        selectedUsers.setLayoutManager( selectedUsersLayoutManager );
        selectedUserAdapter = new SelectedUserAdapter( getContext(),
                getActivity(),
                ((CreateGroup) getActivity()).getGroup().getGroupMembers()  );

        selectedUsers.setAdapter( selectedUserAdapter );


        this.contactUsers = this.getContacts(getContext());

        for( int x = 0; x < this.contactUsers.size(); x++ ) {
            Log.d( "usercontact " , String.valueOf( this.contactUsers.get(x).isRegistered() ) );
        }

        selectUserAdapter = new SelectUserAdapter(getContext() , getActivity(), this.contactUsers );
        searchResults.setAdapter( selectUserAdapter );

        contactRegisteredCheck( selectUserAdapter );

        userSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                userCollectionRef.whereGreaterThanOrEqualTo("username", s.toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> userSnapshot = queryDocumentSnapshots.getDocuments();
                        List<User> users = new ArrayList<>();

                        for( int x = 0; x < userSnapshot.size(); x++ ) {
                            users.add(
                                    new User( userSnapshot.get(x).getId().toString() , userSnapshot.get(x).get("username").toString(),userSnapshot.get(x).get( "email" ).toString(),userSnapshot.get(x).get("profilePicPath").toString(), true )
                            );
                        }

                        if( users.size() > 0 ) {
                            selectUserAdapter.newUserList( users );
                            searchResults.setAdapter(selectUserAdapter);
                        }
                        else {

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d( "firebaseerror" , e.toString());
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        searchResults.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow(userSearch.getWindowToken(), 0);
                return false;

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateGroup)getActivity()).moveView( 1 );
            }
        });


        return rootView;
    }


    public SelectedUserAdapter getSelectedUserAdapter(){
        return this.selectedUserAdapter;
    }

    public SelectUserAdapter getSelectUserAdapter(){
        return this.selectUserAdapter;
    }

    public List<User> getContacts(Context ctx) {
        List<User> list = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {

                        String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String number = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Log.d("phonenumber", number );

                        final User user = new User(
                                username,
                                number,
                                pURI
                        );;

                        //info.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //info.photo = photo;
                        //info.photoURI= pURI;
                        list.add(user);
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        return list;
    }



    public void contactRegisteredCheck(  /*List<User> users,*/ final SelectUserAdapter selectUserAdapter ){

        final List<User> usersRef = selectUserAdapter.getDisplayedUsers();
        for( int x = 0; x < usersRef.size(); x++ ) {

            final int iter = x;
            userCollectionRef.whereEqualTo( "phoneNumber", usersRef.get(x).getPhoneNumber() ).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> userSnapshot = queryDocumentSnapshots.getDocuments();
                    if( !userSnapshot.isEmpty() ) {
                        usersRef.get(iter).setRegistered(true);
                        selectUserAdapter.updated();
                        Log.d( "contactSearch" , userSnapshot.get(0).get("username").toString());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d( "contactSearch" , "no users" );
                }
            });
        }


    }


    public void getUserDetails( final String id /*, final SelectedUserAdapter selectedUserAdapter */ ){
        userCollectionRef.document( id ).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if( documentSnapshot.exists() ) {
                    User user = new User( id,
                            documentSnapshot.get("username").toString() + " (You)",
                            documentSnapshot.get("email").toString(),
                            documentSnapshot.get("phoneNumber").toString(),
                            documentSnapshot.get("profilePicPath").toString() );

                    ( (CreateGroup) getActivity()).getGroup().addGroupMember( user );

                    // TO ADD ->

                    Log.d( "appuser" , user.getUsername() );
                }
                else {
                    Log.d("appuser", "no user found " + FirebaseAuth.getInstance().getUid() );
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d( "appuser" , e.toString() );
            }
        });
    }


}
