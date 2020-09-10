package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Share;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class loginAct extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences loginInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        if(loginInfo.getBoolean("LOGIN_STATUS", false)){
            startActivity(new Intent(this, mainAct.class));
        }
        else{
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences loginInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (!loginInfo.getBoolean("LOGIN_STATUS", false)) {
            mGoogleSignInClient.signOut();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            SharedPreferences loginInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //Adding login Info to Preference
            SharedPreferences.Editor editor = loginInfo.edit();
            editor.putString("NAME", account.getDisplayName());
            editor.putString("EMAIL", account.getEmail());
            editor.putString("IMAGE", String.valueOf(account.getPhotoUrl()));
            editor.putBoolean("LOGIN_STATUS", true);
            editor.apply();
            editor.commit();
            Log.d("Google Login", "Starting new intent");
            startActivity(new Intent(this, mainAct.class));
            //Start new intent
        } catch (ApiException e) {
            Log.w("Google Login", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:   //For Google
                signIn();
                Log.d("Google Login", "Login button pressed");
                break;
            case R.id.login_button: //For facebook
                //
                break;
        }
    }
}
