package com.insa_lyon.restin.Views;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;

import java.text.DecimalFormat;


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
        TextView priceTextView = (TextView)view.findViewById(R.id.priceTextView);
        TextView waitingTimeTextView = (TextView)view.findViewById(R.id.waitingTimeTextView);
        TextView distanceTextView = (TextView)view.findViewById(R.id.distanceTextView);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        Restaurant restaurant = activity.getMapMarkersRestaurants().get(marker);

        nameTextView.setText(marker.getTitle());
        priceTextView.setText("Prix moyen : " + restaurant.getPrixMoyen() + "€");
        waitingTimeTextView.setText("Attente moyenne : " + restaurant.getTempsMoyen() + " min");

        if(restaurant.getDuration() != -1) {
            distanceTextView.setText("Distance : " +   new DecimalFormat("##.#").format(restaurant.getDistance()/1000.0) + " km");
        } else {
            distanceTextView.setText("Distance : ?");
        }
        ratingBar.setRating((float)restaurant.getMoyenneNote());

        return view;
    }
}
