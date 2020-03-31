package com.app.mooch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String EMAIL = "email";

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

    private FirebaseFirestore db;

    private EditText email;
    private EditText password;

    private Button register;
    private LoginButton fbLogin;

    private TextView toLogin;

    private TextView errorMsg;
    private TextView emailTaken;
    private TextView weekPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        db = FirebaseFirestore.getInstance();

        fbLogin = (LoginButton) findViewById(R.id.login_button);
        fbLogin.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken( loginResult.getAccessToken() );
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


        email = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);
        register = (Button) findViewById(R.id.register);

        toLogin = (TextView) findViewById(R.id.login);

        errorMsg = (TextView) findViewById(R.id.errorMsg);
        emailTaken = (TextView) findViewById(R.id.emailTakenMsg);
        weekPassword = (TextView) findViewById(R.id.weekPassword);


        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Register.this, MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMsg.setVisibility(View.INVISIBLE);
                emailTaken.setVisibility(View.INVISIBLE);
                weekPassword.setVisibility(View.INVISIBLE);

                boolean error = false;

                if( email.getText().toString().length() < 1 ) {
                    error = true;
                }
                if( password.getText().toString().length() < 1 ) {
                    error = true;
                }

                if( !error ) {
                    registerUser( email.getText().toString(), password.getText().toString() );
                }
                else {
                    errorMsg.setVisibility(View.VISIBLE);
                }

            }
        });

    }


    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void registerUser( String email , String password ){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //startActivity();

                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("id", user.getUid());
                            userMap.put("email", user.getEmail());
                            userMap.put("username", "");
                            userMap.put("profilePicPath", "");
                            userMap.put("dateCreated", new Date());
                            userMap.put("totalSpending", 0);
                            userMap.put("moochCount", 0);
                            userMap.put("groups", 0);


                            db.collection("users").document(mAuth.getCurrentUser().getUid()).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.w("firebaseuser", "Error adding document");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("firebaseuser",  e.toString());
                                }
                            });


                            startActivity( new Intent( Register.this, Home.class) );
                            finish();

                        } else {
                            if( task.getException().getClass().getName().equals("com.google.firebase.auth.FirebaseAuthUserCollisionException") ) {
                                Log.d( "error login" , "email already exists" );
                                emailTaken.setVisibility(View.VISIBLE);
                            }
                            else if( task.getException().getClass().getName().equals("com.google.firebase.auth.FirebaseAuthWeakPasswordException") ) {
                                Log.d( "error login" , "weak password ");
                                weekPassword.setVisibility(View.VISIBLE);
                            }
                            else {
                                Log.d("error login", "comparison : " + task.getException().getClass().getName() );
                            }
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
