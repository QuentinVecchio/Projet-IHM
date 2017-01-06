package com.insa_lyon.restin.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.MapView;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.R;


public class MapActivity extends AppCompatActivity {
    MapView mapView;
    //GoogleMap map;
    ListView listView;
    EditText searchEditText;
    Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //MapView
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        //SearchBar
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        filterButton = (Button) findViewById(R.id.filtreButton);

        //ListView
        listView = (ListView) findViewById(R.id.restaurantListView);
        RestaurantAdapter adapter = new RestaurantAdapter(this, DataSingleton.getInstance().getRestaurants());
        listView.setAdapter(adapter);

        listView.requestFocus();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
