package com.insa_lyon.restin.Services;

/**
 * Created by Nico on 10/01/2017.
 */
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.*;

import java.util.Vector;

public class GoogleMapsDistanceMatrixRestClient {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public enum TransportMode {
        WALKING("walking"),
        BICYCLING("bicycling"),
        DRIVING("driving");

        private final String value;

        private TransportMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void get(LatLng origin, Vector<LatLng> destinations, TransportMode transportMode, String apiKey, AsyncHttpResponseHandler responseHandler) {
        RequestParams requestParams = new RequestParams();

        //origin
        requestParams.add("origins",origin.latitude+","+origin.longitude);

        //destinations
        StringBuilder destinationsSB = new StringBuilder();
        for(int i = 0; i < destinations.size(); i++) {
            LatLng destinationLatLng = destinations.get(i);
            destinationsSB.append(destinationLatLng.latitude);
            destinationsSB.append(",");
            destinationsSB.append(destinationLatLng.longitude);
            if(i != destinations.size()-1) {
                destinationsSB.append("|");
            }
        }
        requestParams.add("destinations",destinationsSB.toString());

        //transport mode
        requestParams.add("mode",transportMode.getValue());

        //API Key
        requestParams.add("key",apiKey);

        client.get(BASE_URL, requestParams, responseHandler);
    }

}