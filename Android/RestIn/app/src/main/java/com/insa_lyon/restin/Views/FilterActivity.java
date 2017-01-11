package com.insa_lyon.restin.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;

import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FilterActivity extends AppCompatActivity {
    private Button clickButton;
    private LinearLayout bottomSheet;
    private RelativeLayout items;
    private SearchView searchView;
    private ListView listView;
    private RangeSeekBar price_bar;
    private RangeSeekBar time_bar;
    private RangeSeekBar distance_bar;
    private RatingBar ratingBar;
    private Spinner spinner;
    private double minPrice;
    private double maxPrice;
    private double minDistance;
    private double maxDistance;
    private double minTime;
    private double maxTime;
    private double minRating;
    private String sortingBy;
    private String keyWords;
    private RestaurantListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(R.string.filters);
        bottomSheet = (LinearLayout) findViewById(R.id.bottomSheet);
        bottomSheet.setOnClickListener(null);
        items = (RelativeLayout) findViewById(R.id.scroll_filter);
        clickButton = (Button) findViewById(R.id.button2);
        price_bar = (RangeSeekBar) findViewById(R.id.price_filter);
        time_bar = (RangeSeekBar) findViewById(R.id.time_filter);
        distance_bar = (RangeSeekBar) findViewById(R.id.distance_filter);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        spinner = (Spinner) findViewById(R.id.spinner);
        //SearchBar
        searchView = (SearchView) findViewById(R.id.search_filter);
        //on recupere les valeurs des filtres
        maxPrice = price_bar.getSelectedMaxValue().doubleValue();
        minPrice = price_bar.getSelectedMinValue().doubleValue();
        maxTime = time_bar.getSelectedMaxValue().doubleValue();
        minTime = time_bar.getSelectedMinValue().doubleValue();
        maxDistance = distance_bar.getSelectedMaxValue().doubleValue();
        minDistance = distance_bar.getSelectedMinValue().doubleValue();
        minRating = ratingBar.getRating();
        keyWords = searchView.getQuery().toString();
        sortingBy = spinner.getSelectedItem().toString();
        //ListView
        listView = (ListView) findViewById(R.id.restaurantListView);
        List<Restaurant> restaurantList = applySort();
        adapter = new RestaurantListViewAdapter(this, restaurantList);
        listView.setAdapter(adapter);
        listView.requestFocus();
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on met les valeurs par defaut
                maxPrice = 20;
                minPrice = 0;
                maxTime = 200;
                minTime = 0;
                maxDistance = 5000;
                minDistance = 0;
                minRating = 0;
                //on effectue la modification sur les filtres
                price_bar.setSelectedMaxValue(maxPrice);
                price_bar.setSelectedMinValue(minPrice);
                time_bar.setSelectedMaxValue(maxTime);
                time_bar.setSelectedMinValue(minTime);
                distance_bar.setSelectedMaxValue(maxDistance);
                distance_bar.setSelectedMinValue(minDistance);
                ratingBar.setRating((float)minRating);
                spinner.setSelection(0);
                List<Restaurant> restaurantList = applySort();
                adapter = new RestaurantListViewAdapter(FilterActivity.this, restaurantList);
                listView.setAdapter(adapter);
            }
        });
        price_bar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                minPrice = ((Number) minValue).doubleValue();
                maxPrice = ((Number) maxValue).doubleValue();
                List<Restaurant> restaurantList = applySort();
                adapter = new RestaurantListViewAdapter(FilterActivity.this, restaurantList);
                listView.setAdapter(adapter);
            }
        });

        time_bar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                minTime = ((Number) minValue).doubleValue();
                maxTime = ((Number) maxValue).doubleValue();
                List<Restaurant> restaurantList = applySort();
                adapter = new RestaurantListViewAdapter(FilterActivity.this, restaurantList);
                listView.setAdapter(adapter);
            }
        });

        distance_bar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                minDistance = ((Number) minValue).doubleValue();
                maxDistance = ((Number) maxValue).doubleValue();
                List<Restaurant> restaurantList = applySort();
                adapter = new RestaurantListViewAdapter(FilterActivity.this, restaurantList);
                listView.setAdapter(adapter);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sortingBy = spinner.getSelectedItem().toString();
                List<Restaurant> restaurantList = applySort();
                adapter = new RestaurantListViewAdapter(FilterActivity.this, restaurantList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                minRating = (double)rating;
                List<Restaurant> restaurantList = applySort();
                adapter = new RestaurantListViewAdapter(FilterActivity.this, restaurantList);
                listView.setAdapter(adapter);
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
                float visibleBottomSheetHeight = (bottomSheet.getHeight() - bottomSheetBehavior.getPeekHeight()) * slideOffset + bottomSheetBehavior.getPeekHeight();

                //LayoutParams
                ViewGroup.LayoutParams layoutParams = items.getLayoutParams();

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
                layoutParams.height = screenHeight - ((int) Math.ceil(visibleBottomSheetHeight)) - getSupportActionBar().getHeight() - statusBarHeight;
                items.setLayoutParams(layoutParams);
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
                Restaurant restaurant = (Restaurant)adapter.getItem(position);

                Intent intent = new Intent(FilterActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurantIndex",DataSingleton.getInstance().getRestaurantPosition(restaurant));
                FilterActivity.this.startActivity(intent);
            }
        });

        //searchView.setFilterTouchesWhenObscured(true);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                keyWords = s;
                List<Restaurant> restaurantList = applySort();
                final RestaurantListViewAdapter adapter2 = new RestaurantListViewAdapter(FilterActivity.this, restaurantList);
                listView.setAdapter(adapter2);
                BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
                return false;
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

    public List<Restaurant> applySort() {
        List<Restaurant> restaurantList = (ArrayList<Restaurant>) DataSingleton.getInstance().getRestaurants().clone();
        //on verifie les mots clés
        if(!keyWords.isEmpty()) {
            for (int i = restaurantList.size() - 1; i >= 0; i--) {
                String name = restaurantList.get(i).getName().toLowerCase();
                if (!name.toLowerCase().contains(keyWords)) {
                    restaurantList.remove(i);
                }
            }
        }
        //on verifie le prix
        for (int i = restaurantList.size() - 1; i >= 0; i--) {
            double prix = restaurantList.get(i).getPrixMoyen();
            if (prix > this.maxPrice || prix < this.minPrice) {
                restaurantList.remove(i);
            }
        }
        //on verifie le temps
        for (int i = restaurantList.size() - 1; i >= 0; i--) {
            double attente = (double)restaurantList.get(i).getDuration()/60.0;
            if (attente > this.maxTime || attente < this.minTime) {
                restaurantList.remove(i);
            }
        }
        //on verifie la distance
        for (int i = restaurantList.size() - 1; i >= 0; i--) {
            double distance = (double)restaurantList.get(i).getDistance();
            if(distance == -1)distance=0;
            if (distance < this.minDistance || distance > this.maxDistance) {
                restaurantList.remove(i);
            }
        }
        //on verifie la qualite
        for (int i = restaurantList.size() - 1; i >= 0; i--) {
            double rate = restaurantList.get(i).getMoyenneNote();
            if (rate < this.minRating) {
                restaurantList.remove(i);
            }
        }
        //on trie dans l'ordre demandé
        switch (sortingBy) {
            case "Prix moyen":
            {
                //on trie en fonction du prix asc
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2)
                    {
                        if(r1.getPrixMoyen()<r2.getPrixMoyen()) {
                            return -1;
                        }
                        if(r1.getPrixMoyen()>r2.getPrixMoyen()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                break;
            }
            case "Temps d'attente":
            {
                //on trie en fonction du temps d'attente asc
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2)
                    {
                        if(r1.getDuration()<r2.getDuration()) {
                            return -1;
                        }
                        if(r1.getDuration()>r2.getDuration()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                break;
            }
            case "Distance":
            {
                //on trie en fonction de la distance asc
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2)
                    {
                        if(r1.getDistance()<r2.getDistance()) {
                            return -1;
                        }
                        if(r1.getDistance()>r2.getDistance()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                break;
            }
            case "Qualité":
            {
                //on trie en fonction de la qualité desc
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2)
                    {
                        if(r1.getMoyenneNote()<r2.getMoyenneNote()) {
                            return 1;
                        }
                        if(r1.getMoyenneNote()>r2.getMoyenneNote()) {
                            return -1;
                        }
                        return 0;
                    }
                });
                break;
            }
        }
        return restaurantList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
