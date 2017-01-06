package com.insa_lyon.restin.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.insa_lyon.restin.Modeles.Restaurant;
import java.util.List;
import com.insa_lyon.restin.R;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    private List<Restaurant> restaurants = null;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        super(context, 0, restaurants);
        this.restaurants = restaurants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_layout,parent, false);
        }


        return convertView;
    }
}
