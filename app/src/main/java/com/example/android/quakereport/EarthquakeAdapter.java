package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;

import static com.example.android.quakereport.R.id.magnitude;

/**
 * Created by hakeem on 9/1/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    //to store to parts of the location (country and(offset of "near the"))
    private String[] locationParts;

    /**
     * Create a new {@link EarthquakeAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param Earthquakes is the list of {@link Earthquake}s to be displayed.
     */
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> Earthquakes) {
        super(context, 0, Earthquakes);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);




        TextView EarthquakeDegree = (TextView) listItemView.findViewById(magnitude);
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getEarthquakeDegree());
        EarthquakeDegree.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable)EarthquakeDegree.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getEarthquakeDegree());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        String wholeLocation = currentEarthquake.getLocation();
        splitLocation(wholeLocation);
        TextView EarthquakeLocationOffset = (TextView) listItemView.findViewById(R.id.location_offset);
        EarthquakeLocationOffset.setText(locationParts[0]);
        TextView EarthquakePrimaryLocation = (TextView) listItemView.findViewById(R.id.primary_location);
        EarthquakePrimaryLocation.setText(locationParts[1]);


        Date dateObject = new Date(currentEarthquake.getmTimeInMilliseconds());


        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        return listItemView;
    }



    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private void splitLocation(String location)
    {
        if(location.contains("of"))
        {
            this.locationParts = location.split("of");
            locationParts[0] += " of";
        }
        else {
            locationParts[0] = getContext().getString(R.string.near_the);
            locationParts[1] = location;
        }
    }


    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
