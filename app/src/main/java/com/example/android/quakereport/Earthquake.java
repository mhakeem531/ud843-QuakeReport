package com.example.android.quakereport;

/**
 * Created by hakeem on 9/1/17.
 */

public class Earthquake {

    private double earthquakeDegree;
    private String location;
    private String earthquakeURL;
    private long mTimeInMilliseconds;


    public Earthquake(double earthquakeDegree, String location, String earthquakeURL, long mTimeInMilliseconds) {
        this.earthquakeDegree = earthquakeDegree;
        this.location = location;
        this.earthquakeURL = earthquakeURL;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
    }

    public Earthquake(double earthquakeDegree, String location, long mTimeInMilliseconds) {
        this.earthquakeDegree = earthquakeDegree;
        this.location = location;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
    }

    public void setEarthquakeDegree(double earthquakeDegree) {
        this.earthquakeDegree = earthquakeDegree;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setmTimeInMilliseconds(long mTimeInMilliseconds) {
        this.mTimeInMilliseconds = mTimeInMilliseconds;
    }

    public double getEarthquakeDegree() {
        return earthquakeDegree;
    }

    public String getLocation() {
        return location;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }


    public String getEarthquakeURL() {
        return earthquakeURL;
    }

    public void setEarthquakeURL(String earthquakeURL) {
        this.earthquakeURL = earthquakeURL;
    }


    @Override
    public String toString() {
        return "Earthquake{" +
                "Earthquake Degree='" + earthquakeDegree + '\'' +
                ", Location='" + location + '\'' +
                ", Earthquake Time and Date =" + mTimeInMilliseconds + '\'' +
                ", Earthquake URL =" + earthquakeURL +
                '}';
    }


}
