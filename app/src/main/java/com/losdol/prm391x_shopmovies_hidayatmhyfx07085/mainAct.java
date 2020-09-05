package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

    public void fragMovieGrid(){
        movieGrid fmovieGrid = new movieGrid();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.grid_frag_contain, fmovieGrid);
        fragmentTransaction.addToBackStack("Fragment Gridview");
        fragmentTransaction.commit();
    }

    public void fragUserProfile(){
        userProfile fragUserProfile = new userProfile();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.grid_frag_contain, fragUserProfile);
        fragmentTransaction.addToBackStack("Fragment User");
        fragmentTransaction.commit();
    }
}
