package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    String personName,personGivenName,personFamilyName,personEmail,personId;
    Uri personPhoto;

    public userProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);



        TextView profileName = view.findViewById(R.id.user_name);
        TextView email = view.findViewById(R.id.email);
        //ImageView imgIv = view.findViewById(R.id.user_ava);

        profileName.setText(loginInfo.getRegisteredUser(getContext()));
        email.setText(loginInfo.getRegisteredEmail(getContext()));
        //Picasso.get().load(personPhoto).into(imgIv);

        return view;
    }

}
