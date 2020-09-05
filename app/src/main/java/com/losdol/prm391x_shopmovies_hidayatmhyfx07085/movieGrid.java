package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.widget.StateSet.TAG;

public class movieGrid extends Fragment {
    GridView gridView;
    private ProgressDialog pDialog;
    private static String url = "https://api.androidhive.info/json/movies_2017.json";

    ArrayList<HashMap<String, String>> moviesList;

    public movieGrid() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        moviesList = new ArrayList<>();
        gridView = view.findViewById(R.id.moviesgv);
        new getMovies().execute();
        return view;
    }

    class getMovies extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            httpHandler sh = new httpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray moviesArray = new JSONArray(jsonStr);
                    for (int i = 0; i < moviesArray.length(); i++) {
                        JSONObject c = moviesArray.getJSONObject(i);

                        String title = c.getString("title");
                        String image = c.getString("image");
                        String price = c.getString("price");

                        // tmp hash map for single contact
                        HashMap<String, String> movieHashMap = new HashMap<>();

                        // adding each child node to HashMap key => value
                        movieHashMap.put("title", title);
                        movieHashMap.put("image", image);
                        movieHashMap.put("price", price);

                        // adding movies to movies list
                        moviesList.add(movieHashMap);
                    }
                }
                catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            gridView_adapter adapter = new gridView_adapter(getContext(), moviesList);
            gridView.setAdapter(adapter);
        }
    }

}
