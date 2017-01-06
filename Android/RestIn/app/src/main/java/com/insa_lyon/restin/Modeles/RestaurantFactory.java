package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class RestaurantFactory {
    private RestaurantFactory instance = null;

    private RestaurantFactory() {
        if(instance == null) {
            instance = new RestaurantFactory();
        }
    }

    public RestaurantFactory getInstance() {
        if(instance == null) {
            instance = new RestaurantFactory();
        }
        return instance;
    }

    static ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        //Restaurant RU Jussieu
        ArrayList<Avis> avisRUJussieu = new ArrayList<Avis>();

        Restaurant ruJussieu = new Restaurant("RU Jussieu", 45.780691, 4.876519, avisRUJussieu);
        restaurants.add(ruJussieu);

        //Restaurant l'olivier
        ArrayList<Avis> avisOlivier = new ArrayList<Avis>();

        Restaurant olivier = new Restaurant("L'olivier", 45.784221, 4.874811, avisOlivier);
        restaurants.add(olivier);

        //Restaurant le Prevert
        ArrayList<Avis> avisPrevert = new ArrayList<Avis>();

        Restaurant prevert = new Restaurant("Le Prevert", 45.781173, 4.873638, avisPrevert);
        restaurants.add(prevert);

        //Restaurant le grillon
        ArrayList<Avis> avisGrillon = new ArrayList<Avis>();

        Restaurant grillon = new Restaurant("Le Grillon", 45.783927, 4.875049, avisGrillon);
        restaurants.add(grillon);

        //Restaurant la Roulotte dorée
        ArrayList<Avis> avisRoulotteDoree = new ArrayList<Avis>();

        Restaurant roulotteDoree = new Restaurant("La Roulotte Dorée", 45.782565, 4.876553, avisRoulotteDoree);
        restaurants.add(roulotteDoree);

        //Restaurant Ali Baba Kebab
        ArrayList<Avis> avisNinkasi = new ArrayList<Avis>();

        Restaurant ninkasi = new Restaurant("Ninkasi", 45.778878, 4.872942, avisNinkasi);
        restaurants.add(ninkasi);

        return restaurants;
    }
}
