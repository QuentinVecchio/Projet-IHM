package com.insa_lyon.restin.Views;

/**
 * Created by Nico on 07/01/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;
import com.insa_lyon.restin.Services.GoogleMapsDistanceMatrixRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

import cz.msebera.android.httpclient.Header;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        testGoogleMapsDistanceMatrixRestClient();
        Intent intent = new Intent(this, MapActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    private void testGoogleMapsDistanceMatrixRestClient() {
        LatLng origin = new LatLng(45.782123, 4.886964);

        Vector<LatLng> destinationLatLngs = new Vector<>();
        for(Restaurant restaurant : DataSingleton.getInstance().getRestaurants()) {
            destinationLatLngs.add(new LatLng(restaurant.getLat(),restaurant.getLon()));
        }

        GoogleMapsDistanceMatrixRestClient.get(origin, destinationLatLngs, GoogleMapsDistanceMatrixRestClient.TransportMode.WALKING, getString(R.string.google_maps_key), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("--SUCCESS");
                System.out.println(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                System.out.println("--FAILURE");
            }
        });
    }
}
