package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class mainAct extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    userProfile userProfile = new userProfile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainact);
        bottomNav = findViewById(R.id.bottom_navigation);

        fragMovieGrid();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.movies_list_navbar:
                        fragMovieGrid();
                        return true;
                    case R.id.user_frag:
                        fragUserProfile();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences loginInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Adding login Info to Preference
        SharedPreferences.Editor editor = loginInfo.edit();
        switch (item.getItemId()) {
            case R.id.logout_menu:
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                editor.putBoolean("LOGIN_STATUS", false).commit();
                startActivity(new Intent(this, loginAct.class));
                finish();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void fragMovieGrid(){
        movieGrid fmovieGrid = new movieGrid();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.grid_frag_contain, fmovieGrid);
        fragmentTransaction.commit();

    }

    public void fragUserProfile(){
        userProfile fragUserProfile = new userProfile();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.grid_frag_contain, fragUserProfile);
        fragmentTransaction.commit();
    }
}
