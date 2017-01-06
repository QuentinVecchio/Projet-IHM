package com.insa_lyon.restin.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.insa_lyon.restin.Modeles.Restaurant;
import java.util.List;
import com.insa_lyon.restin.R;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class RestaurantAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Restaurant> restaurants = null;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.restaurants = restaurants;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layoutItem;

        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "information_layout.xml"
            layoutItem = (LinearLayout) layoutInflater.inflate(R.layout.restaurant_layout, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView nameTextView = (TextView)layoutItem.findViewById(R.id.nameTextView);
        TextView waitingTimeTextView = (TextView)layoutItem.findViewById(R.id.waitingTimeTextView);
        TextView distanceTextView = (TextView)layoutItem.findViewById(R.id.distanceTextView);
        TextView opinionNumberTextView = (TextView)layoutItem.findViewById(R.id.opinionNumberTextView);
        RatingBar ratingBar = (RatingBar)layoutItem.findViewById(R.id.ratingBar);
        ratingBar.setRating(3);


        Restaurant restaurant = restaurants.get(position);

        nameTextView.setText(restaurant.getName());
        waitingTimeTextView.setText("Attente : 0 min");
        distanceTextView.setText("0 m");
        opinionNumberTextView.setText("0 avis");

        return layoutItem;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
