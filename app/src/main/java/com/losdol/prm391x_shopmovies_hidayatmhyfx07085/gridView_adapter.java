package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class gridView_adapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> moviesList;
    LayoutInflater inflater;

    public gridView_adapter(Context context, ArrayList<HashMap<String, String>> movList) {
        this.inflater=LayoutInflater.from(context);
        this.moviesList = movList;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.gv_adapter, viewGroup, false);
            TextView priceText = view.findViewById(R.id.price);
            TextView movieText = view.findViewById(R.id.mov_name);
            ImageView imgIV = view.findViewById(R.id.imageView);

            HashMap<String, String> mapMovies = (HashMap<String, String>) moviesList.get(position);

            Picasso.get().load(mapMovies.get("image")).into(imgIV);
            priceText.setText(mapMovies.get("price"));
            movieText.setText(mapMovies.get("title"));
        }
        return view;
    }
}
