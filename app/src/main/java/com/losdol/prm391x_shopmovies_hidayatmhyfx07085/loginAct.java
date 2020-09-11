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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class loginAct extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    LoginButton loginButton;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        SharedPreferences loginInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //This call back code i got from official facebook dev documentation
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        //Checking the login status before
        if(loginInfo.getBoolean("LOGIN_STATUS", false)){
            startActivity(new Intent(this, mainAct.class));
        }

        else{
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                }

                @Override
                public void onCancel() {
                    Log.d("Facebook", "onCancel: " + "Login Canceled");
                }

                @Override
                public void onError(FacebookException exception) {
                    Log.e("Facebook", "onError: Error Login", exception);
                }
            });

            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            Log.d("Facebook", "onSuccess: " + "Login Success, sharedpref");
                            setFacebookData(loginResult);
                            startActivity(new Intent(getApplicationContext(), mainAct.class));
                            Log.d("Facebook", "onSuccess: " + "Login Success, starting new activity");
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

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String nameFull = firstName + " " + lastName;
                            String profileURL = "";
                            if (Profile.getCurrentProfile() != null) {
                                profileURL = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 400, 400).toString();
                            }

                            SharedPreferences loginInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            //Adding login Info to Preference
                            SharedPreferences.Editor editor = loginInfo.edit();
                            editor.putString("NAME", nameFull);
                            editor.putString("EMAIL", email);
                            editor.putString("IMAGE", profileURL);
                            editor.putBoolean("LOGIN_STATUS", true);
                            editor.apply();
                            editor.commit();
                            Log.d("Google Login", "Starting new intent");

                        } catch (JSONException e) {
                            Toast.makeText(loginAct.this, "Error getting data from facebook", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
            Toast.makeText(getApplicationContext(), "Sign In Failed. Failed code: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
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
