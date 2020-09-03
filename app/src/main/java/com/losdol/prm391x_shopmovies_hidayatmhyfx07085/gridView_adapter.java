package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class gridView_adapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, String>> movieList;

    public gridView_adapter(Context mContext, ArrayList<HashMap<String, String>> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Here's my custom row adapter, the coding style soo bad, next time i'll be naming better instance/object
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customRow =  layoutInflater.inflate(R.layout.gv_adapter, parent, false);

        TextView textViewItem = customRow.findViewById(R.id.mov_name);
        TextView priceViewItem = customRow.findViewById(R.id.price);
        ImageView imageViewItem = customRow.findViewById(R.id.imageView);

        HashMap<String, String> mapMovies = (HashMap<String, String>) movieList.get(position);
        Log.d(TAG, "getView: " + mapMovies.get("image"));

        Picasso.get().load(mapMovies.get("image")).into(imageViewItem);
        priceViewItem.setText(mapMovies.get("price"));
        textViewItem.setText(mapMovies.get("title"));
        return convertView;

    }
}
