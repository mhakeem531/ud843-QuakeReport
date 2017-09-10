/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=1&limit=10";

    private EarthquakeAdapter adapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        Log.e(LOG_TAG, "Hello it's me from onCreate");

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Earthquake earthquake = adapter.getItem(position);
                String earthquakeURL = earthquake.getEarthquakeURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(earthquakeURL));
                startActivity(i);

            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        Log.e(LOG_TAG, "Hello it's me from onCreateLoader");


        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);


    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        // TODO: Update the UI with the result
        progress = (ProgressBar) findViewById(R.id.loading_spinner);
        progress.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_earthquakes);
        adapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            adapter.addAll(earthquakes);
        }
        Log.e(LOG_TAG, "Hello it's me from onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        Log.e(LOG_TAG, "Hello it's me from onLoaderReset");
        // TODO: Loader reset, so we can clear out our existing data.
        adapter.clear();

    }


//    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
//
//        ///private  ArrayList<Earthquake> earthquakes = new ArrayList<>();
//
//        /**
//         * This method is invoked (or called) on a background thread, so we can perform
//         * long-running operations like making a network request.
//         * <p>
//         * It is NOT okay to update the UI from a background thread, so we just return an
//         * {@link Earthquake} object as the result.
//         */
//        protected ArrayList<Earthquake> doInBackground(String... urls) {
//            // ArrayList<Earthquake> earthquakes;// = new ArrayList<>();
//
//            // Don't perform the request if there are no URLs, or the first URL is null.
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//            ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
//            return result;
//        }
//
//        /**
//         * This method is invoked on the main UI thread after the background work has been
//         * completed.
//         * <p>
//         * It IS okay to modify the UI within this method. We take the {@link Earthquake} object
//         * (which was returned from the doInBackground() method) and update the views on the screen.
//         */
//        protected void onPostExecute(ArrayList<Earthquake> result) {
//            adapter.clear();
//            // If there is no result, do nothing.
//            if (result == null || result.isEmpty()) {
//                return;
//            }
//            adapter.addAll(result);
//
//        }
//
//
//    }
}
