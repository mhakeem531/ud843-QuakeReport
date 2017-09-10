package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.QueryUtils.fetchEarthquakeData;

/**
 * Created by hakeem on 9/7/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {


    private  String queryUrl = "";

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.queryUrl = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        if (queryUrl == null) {
            return null;
        }
        ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(this.queryUrl);
        return result;
    }
}
