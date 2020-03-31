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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private static final String EMAIL = "email";

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

    private EditText email;
    private EditText password;
    private Button login;
    private TextView forgotPassword;
    private TextView register;

    private TextView errorMsg;

    private LoginButton fbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        if( mAuth.getCurrentUser() != null ) {
            startActivity( new Intent( MainActivity.this, Home.class ) );
            finish();
        }

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

        //AppEventsLogger.activateApp(getApplicationContext());

        email = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);
        login = (Button) findViewById(R.id.login);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        register = (TextView) findViewById(R.id.register);

        errorMsg = (TextView) findViewById(R.id.errorMsg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMsg.setVisibility(View.INVISIBLE);

                boolean error = false;


                if( email.getText().toString().length() < 1 ) {
                    error = true;
                }
                if( password.getText().toString().length() < 1 ) {
                    error = true;
                }

                if( error ) {
                    errorMsg.setVisibility(View.VISIBLE);
                }
                else {
                    signInWithFirebase( email.getText().toString(), password.getText().toString() );
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, Register.class) );
            }
        });


    }


    public void signInWithFirebase( String email, String password ){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d( "firebaseauthlogin" , "firebase logedin" );

                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity( new Intent( MainActivity.this, Home.class) );
                            finish();

                        } else {
                            Log.d( "firebaseauthlogin" , "firebase login failed" );
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, "Authentication failed.",
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
