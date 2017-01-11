package com.insa_lyon.restin.Views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;
import com.insa_lyon.restin.Services.GoogleMapsDistanceMatrixRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cz.msebera.android.httpclient.Header;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    private LinearLayout bottomSheet;

    private ListView listView;

    private SearchView searchView;

    private Button filterButton;

    private Map<Marker,Restaurant> mapMarkersRestaurants = new HashMap<>();

    private Map<Restaurant,Marker> mapRestaurantsMarkers = new HashMap<>();

    private RestaurantListViewAdapter restaurantListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setTitle(R.string.app_name);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        restaurantListViewAdapter = new RestaurantListViewAdapter(this, DataSingleton.getInstance().getRestaurants());
        listView.setAdapter(restaurantListViewAdapter);

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
                Restaurant restaurant = (Restaurant)restaurantListViewAdapter.getItem(position);

                Intent intent = new Intent(MapActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurantIndex",DataSingleton.getInstance().getRestaurantPosition(restaurant));
                MapActivity.this.startActivity(intent);
            }
        });

        listView.requestFocus();

        //BottomSheet
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


        //SearchBar
        searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setFilterTouchesWhenObscured(true);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Restaurant> restaurants;
                if(s.isEmpty()) {
                    restaurants = DataSingleton.getInstance().getRestaurants();

                } else {
                    restaurants = new ArrayList<>();
                    restaurants.addAll(DataSingleton.getInstance().getRestaurants());
                    for(Restaurant restaurant : DataSingleton.getInstance().getRestaurants()) {
                        if(!restaurant.getName().toLowerCase().contains(s.toLowerCase())) {
                            restaurants.remove(restaurant);
                        }
                    }
                }
                restaurantListViewAdapter.setRestaurants(restaurants);
                mMap.clear();
                mapMarkersRestaurants.clear();
                mapRestaurantsMarkers.clear();
                for(Restaurant restaurant : restaurants) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(restaurant.getLat(),restaurant.getLon()));
                    markerOptions.title(restaurant.getName());
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    Marker marker = mMap.addMarker(markerOptions);
                    mapMarkersRestaurants.put(marker,restaurant);
                    mapRestaurantsMarkers.put(restaurant,marker);
                }
                restaurantListViewAdapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromInputMethod(searchView.getWindowToken(), 0);
                return false;
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

    @Override
    protected void onResume() {
        super.onResume();
        restaurantListViewAdapter.notifyDataSetChanged();
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

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setInfoWindowAdapter(new RestaurantInfoWindowAdapter(this));

        //Insa Lyon
        LatLng latLng = new LatLng(45.782951, 4.878269);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        for(Restaurant restaurant : DataSingleton.getInstance().getRestaurants()) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(restaurant.getLat(),restaurant.getLon()));
            markerOptions.title(restaurant.getName());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            Marker marker = mMap.addMarker(markerOptions);
            mapMarkersRestaurants.put(marker,restaurant);
            mapRestaurantsMarkers.put(restaurant,marker);
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurantIndex",DataSingleton.getInstance().getRestaurantPosition(mapMarkersRestaurants.get(marker)));
                MapActivity.this.startActivity(intent);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Calculate required horizontal shift for current screen density
                final int dX = getResources().getDimensionPixelSize(R.dimen.map_dx);
                // Calculate required vertical shift for current screen density
                final int dY = getResources().getDimensionPixelSize(R.dimen.map_dy);
                final Projection projection = mMap.getProjection();
                final Point markerPoint = projection.toScreenLocation(
                        marker.getPosition()
                );
                // Shift the point we will use to center the map
                markerPoint.offset(dX, dY);
                final LatLng newLatLng = projection.fromScreenLocation(markerPoint);
                // Buttery smooth camera swoop :)

                mMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng),500,null);
                // Show the info window (as the overloaded method would)
                marker.showInfoWindow();
                return true; // Consume the event since it was dealt with
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        this.calculDistanceAndTimeForRestaurants(latLng);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    public Map<Marker, Restaurant> getMapMarkersRestaurants() {
        return mapMarkersRestaurants;
    }

    private void calculDistanceAndTimeForRestaurants(LatLng origin) {
        Vector<LatLng> destinationLatLngs = new Vector<>();
        for(Restaurant restaurant : DataSingleton.getInstance().getRestaurants()) {
            destinationLatLngs.add(new LatLng(restaurant.getLat(),restaurant.getLon()));
        }

        GoogleMapsDistanceMatrixRestClient.get(origin, destinationLatLngs, GoogleMapsDistanceMatrixRestClient.TransportMode.WALKING, getString(R.string.google_maps_key), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray rows = response.getJSONArray("rows");
                    JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
                    for(int i = 0; i < elements.length(); i++) {
                        JSONObject element = elements.getJSONObject(i);
                        JSONObject distance = element.getJSONObject("distance");
                        JSONObject duration = element.getJSONObject("duration");
                        DataSingleton.getInstance().getRestaurants().get(i).setDistance(distance.getLong("value"));
                        DataSingleton.getInstance().getRestaurants().get(i).setDuration(duration.getLong("value"));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                System.out.println("--FAILURE");
            }
        });
    }
}
