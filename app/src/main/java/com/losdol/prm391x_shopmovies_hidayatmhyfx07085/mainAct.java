package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

    private String TAG = mainAct.class.getSimpleName();

    private ProgressDialog pDialog;
    private GridView moviesGrid;

    // URL to get movies JSON
    private static String url = "https://api.androidhive.info/json/movies_2017.json";

    ArrayList<HashMap<String, String>> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainact);

        moviesList = new ArrayList<>();

        moviesGrid = (GridView) findViewById(R.id.moviesgv);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(mainAct.this);
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
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

            gridView_adapter adapter = new gridView_adapter(getApplicationContext(), moviesList);
            moviesGrid.setAdapter(adapter);
        }
    }
}
