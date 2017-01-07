package com.insa_lyon.restin.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Menu;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;

import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int position = this.getIntent().getIntExtra("restaurantIndex",-1);
        if(position != -1) {
            this.restaurant = DataSingleton.getInstance().getRestaurant(position);
            this.getSupportActionBar().setTitle(this.restaurant.getName());
        } else {
            this.restaurant = null;
        }

        showMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showMenu() {
        List<Restaurant> rest = DataSingleton.getInstance().getRestaurants();
        Restaurant jussieu = rest.get(0);
        Menu menu = jussieu.getMenuMidi();

        LinearLayout mLayout = (LinearLayout) findViewById(R.id.entranceContainer);
        LinearLayout mLayoutP = (LinearLayout) findViewById(R.id.dishContainer);
        LinearLayout mLayoutD = (LinearLayout) findViewById(R.id.dessertContainer);

        for (String e : menu.getEntrees()) {
            TextView textView = new TextView(this);
            textView.setText(e);
            mLayout.addView(textView);
        }

        for (String e : menu.getPlats()) {
            TextView textView = new TextView(this);
            textView.setText(e);
            mLayoutP.addView(textView);
        }

        for (String e : menu.getDesserts()) {
            TextView textView = new TextView(this);
            textView.setText(e);
            mLayoutD.addView(textView);
        }
    }
}
