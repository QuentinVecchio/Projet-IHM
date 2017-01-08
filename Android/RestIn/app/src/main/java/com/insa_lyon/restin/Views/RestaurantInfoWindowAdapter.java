package com.insa_lyon.restin.Views;

import android.app.Activity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.vision.text.Text;
import com.insa_lyon.restin.R;


/**
 * Created by Nico on 08/01/2017.
 */

public class RestaurantInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private MapActivity activity;

    public RestaurantInfoWindowAdapter(MapActivity activity) {
        this.activity = activity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = this.activity.getLayoutInflater().inflate(R.layout.restautant_info_window_layout, null);

        TextView nameTextView = (TextView)view.findViewById(R.id.nameTextView);
        TextView waitingTimeTextView = (TextView)view.findViewById(R.id.waitingTimeTextView);
        TextView distanceTextView = (TextView)view.findViewById(R.id.distanceTextView);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        nameTextView.setText(marker.getTitle());
        waitingTimeTextView.setText("Attente : 2 min");
        distanceTextView.setText("Distance : 150 m");
        ratingBar.setRating((float)activity.getMapMarkersRestaurants().get(marker).getMoyenneNote());

        return view;
    }
}
