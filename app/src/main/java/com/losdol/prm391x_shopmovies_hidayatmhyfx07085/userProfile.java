package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class userProfile extends Fragment {
    public View view;
    public TextView profileName, email;

    public userProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        profileName = view.getRootView().findViewById(R.id.user_name);
        email = view.getRootView().findViewById(R.id.email);
        ImageView imgIv = view.findViewById(R.id.user_ava);

        profileName.setText(sharedPreferences.getString("NAME", ""));
        email.setText(sharedPreferences.getString("EMAIL", ""));
        Picasso.get().load(sharedPreferences.getString("IMAGE", "")).into(imgIv);
        return view;
    }
}
