package com.app.mooch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class FindUsers extends AppCompatActivity {

    FirebaseFirestore db;
    CollectionReference userCollectionRef;


    private EditText userSearch;

    private TextView preSearch;
    private TextView noResults;


    private List<User> selectedUsersIds;
    private RecyclerView selectedUsers;
    private RecyclerView.LayoutManager selectedUserLayoutManager;
    private SelectedUserAdapter selectedUserAdapter;

    private RecyclerView searchResults;
    private RecyclerView.LayoutManager layoutManager;
    private SelectUserAdapter selectUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_users);
/*
        db = FirebaseFirestore.getInstance();
        userCollectionRef = db.collection("users");

        selectedUsersSinglton = selectedUsersSinglton.getInstance();

        selectedUsersIds = new ArrayList<>();
        // FIX / ADD  current username only have ID
        selectedUsersSinglton.addUser(new User( FirebaseAuth.getInstance().getUid() , "qwerty (you)"));
        selectedUsers = (RecyclerView) findViewById(R.id.addFriends);
        selectedUserLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        selectedUsers.setLayoutManager( selectedUserLayoutManager );
        selectedUserAdapter = new SelectedUserAdapter( getApplicationContext() ,getCallingActivity() selectedUsersIds, this.selectedUsersSinglton );
        selectedUsers.setAdapter( selectedUserAdapter );

        userSearch = (EditText) findViewById(R.id.searchUsername);

        preSearch = (TextView) findViewById(R.id.preSearch);
        noResults = (TextView) findViewById(R.id.noResults);

        searchResults = (RecyclerView) findViewById(R.id.userSearchResults);
        layoutManager = new LinearLayoutManager(this);
        searchResults.setLayoutManager( layoutManager );

        userSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d("edittextcount" , String.valueOf(count) );

                preSearch.setVisibility(View.GONE);
                noResults.setVisibility(View.GONE);
                searchResults.setVisibility(View.GONE);

                if( count > 0 ) {
                    //Query query = userCollectionRef.whereEqualTo("username", s.toString()+'\uf8ff');
                    userCollectionRef.whereGreaterThanOrEqualTo("username", s.toString()).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                                        selectUserAdapter = new SelectUserAdapter( getApplicationContext() , users , selectedUsersIds, selectedUsersSinglton);
                                        searchResults.setAdapter( selectUserAdapter );
                                        searchResults.setVisibility( View.VISIBLE );
                                    }
                                    else {
                                        noResults.setVisibility(View.VISIBLE);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d( "firebasereturn", e.toString() );
                        }
                    });
                }
                else if( count < 1 ) {
                    preSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        searchResults.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow(userSearch.getWindowToken(), 0);
                return false;

            }
        });


*/
    }



}
