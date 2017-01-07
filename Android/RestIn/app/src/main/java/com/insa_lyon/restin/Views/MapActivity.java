package com.insa_lyon.restin.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.MapView;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.R;


public class MapActivity extends AppCompatActivity {

    private LinearLayout bottomSheet;

    private MapView mapView;

    //GoogleMap map;
    private ListView listView;

    private EditText searchEditText;

    private Button filterButton;

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

        bottomSheet = (LinearLayout)findViewById(R.id.bottomSheet);

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        bottomSheetBehavior.setHideable(false);
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
