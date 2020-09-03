package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;    //I use custom library for binding image from url to imageView
import java.util.ArrayList;
import java.util.HashMap;


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
