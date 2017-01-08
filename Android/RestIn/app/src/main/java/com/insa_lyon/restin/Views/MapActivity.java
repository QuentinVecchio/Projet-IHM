package com.insa_lyon.restin.Views;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LinearLayout bottomSheet;

    private ListView listView;

    private SearchView searchView;

    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //SearchBar
        searchView = (SearchView) findViewById(R.id.searchView);

        //FilterButton
        filterButton = (Button) findViewById(R.id.filtreButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapActivity.this, FilterActivity.class);
                MapActivity.this.startActivity(intent);
            }
        });

        //ListView
        listView = (ListView) findViewById(R.id.restaurantListView);
        RestaurantAdapter adapter = new RestaurantAdapter(this, DataSingleton.getInstance().getRestaurants());
        listView.setAdapter(adapter);

        listView.requestFocus();

        bottomSheet = (LinearLayout)findViewById(R.id.bottomSheet);
        bottomSheet.setOnClickListener(null);

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float visibleBottomSheetHeight = (bottomSheet.getHeight() - bottomSheetBehavior.getPeekHeight()) * slideOffset+bottomSheetBehavior.getPeekHeight();

                //MapViewLayoutParams
                LinearLayout layout = (LinearLayout)findViewById(R.id.activityMapMainContent);
                android.view.ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();

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
                layout.setLayoutParams(layoutParams);
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

        //Initialize the map size
        ViewGroup contentView = (ViewGroup)getWindow().getDecorView();
        contentView.post(new Runnable() {
            public void run() {
                //MapViewLayoutParams
                LinearLayout layout = (LinearLayout)findViewById(R.id.activityMapMainContent);
                android.view.ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();

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

                switch (bottomSheetBehavior.getState()) {
                    case BottomSheetBehavior.STATE_EXPANDED :
                        layoutParams.height = screenHeight - bottomSheet.getHeight() - statusBarHeight - getSupportActionBar().getHeight();
                        layout.setLayoutParams(layoutParams);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED :
                        layoutParams.height = screenHeight - bottomSheetBehavior.getPeekHeight() - statusBarHeight - getSupportActionBar().getHeight();
                        layout.setLayoutParams(layoutParams);
                        break;
                }

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(45.782951, 4.878269);
        mMap.addMarker(new MarkerOptions().position(latLng).title("INSA Lyon"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }
}
