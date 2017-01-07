package com.insa_lyon.restin.Views;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.maps.MapView;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.R;


public class MapActivity extends AppCompatActivity {

    private LinearLayout bottomSheet;

    private MapView mapView;

    //GoogleMap map;
    private ListView listView;

    private SearchView searchView;

    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //SearchBar
        searchView = (SearchView) findViewById(R.id.searchView);
        filterButton = (Button) findViewById(R.id.filtreButton);

        //ListView
        listView = (ListView) findViewById(R.id.restaurantListView);
        RestaurantAdapter adapter = new RestaurantAdapter(this, DataSingleton.getInstance().getRestaurants());
        listView.setAdapter(adapter);

        listView.requestFocus();

        bottomSheet = (LinearLayout)findViewById(R.id.bottomSheet);
        bottomSheet.setOnClickListener(null);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //MapView
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MapActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurantIndex",position);
                MapActivity.this.startActivity(intent);

            }
        });

        searchView.setFilterTouchesWhenObscured(true);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });
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
