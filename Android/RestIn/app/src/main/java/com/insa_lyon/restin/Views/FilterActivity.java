package com.insa_lyon.restin.Views;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.List;

import static android.R.id.list;

public class FilterActivity extends AppCompatActivity {
    private Button clickButton;
    private LinearLayout bottomSheet;
    private ScrollView items;
    private SearchView searchView;
    private ListView listView;
    private RangeSeekBar price_bar;
    private RangeSeekBar time_bar;
    private RangeSeekBar distance_bar;
    private RatingBar ratingBar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(R.string.filters);
        bottomSheet = (LinearLayout)findViewById(R.id.bottomSheet);
        bottomSheet.setOnClickListener(null);
        items = (ScrollView) findViewById(R.id.scroll_filter);
        clickButton = (Button) findViewById(R.id.button2);
        price_bar = (RangeSeekBar) findViewById(R.id.price_filter);
        time_bar = (RangeSeekBar) findViewById(R.id.time_filter);
        distance_bar = (RangeSeekBar) findViewById(R.id.distance_filter);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        spinner = (Spinner) findViewById(R.id.spinner);
        //SearchBar
        searchView = (SearchView) findViewById(R.id.search_filter);
        //ListView
        listView = (ListView) findViewById(R.id.restaurantListView);
        final RestaurantListViewAdapter adapter = new RestaurantListViewAdapter(this, DataSingleton.getInstance().getRestaurants());
        listView.setAdapter(adapter);

        listView.requestFocus();
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* action */

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                listView = (ListView) findViewById(R.id.restaurantListView);
                List<Restaurant> restaurantList = DataSingleton.getInstance().getRestaurants();
                final RestaurantListViewAdapter adapter2 = new RestaurantListViewAdapter(FilterActivity.this,restaurantList);
                listView.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float visibleBottomSheetHeight = (bottomSheet.getHeight() - bottomSheetBehavior.getPeekHeight()) * slideOffset+bottomSheetBehavior.getPeekHeight();

                //LayoutParams
                ScrollView scrollView = (ScrollView)findViewById(R.id.scroll_filter);
                android.view.ViewGroup.LayoutParams layoutParams = scrollView.getLayoutParams();

                //ScreenHeight
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int screenHeight = size.y;

                //StatusBarHeight
                Rect rectangle = new Rect();
                Window window = getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight = rectangle.top;

                //Updating MapViewLayoutHeight
                layoutParams.height = screenHeight - ((int)Math.ceil(visibleBottomSheetHeight)) - getSupportActionBar().getHeight() - statusBarHeight;
                scrollView.setLayoutParams(layoutParams);
            }


        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                /*Restaurant restaurant = (Restaurant)adapter.getItem(position);
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(restaurant.getLat(),restaurant.getLon()));
                googleMap.moveCamera(center);
                mapRestaurantsMarkers.get(restaurant).showInfoWindow();*/
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
