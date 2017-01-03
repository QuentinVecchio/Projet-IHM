package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class RestaurantFactory {
    private RestaurantFactory instance;

    private RestaurantFactory() {
        if(instance == null) {
            instance = new RestaurantFactory();
        }
    }

    public RestaurantFactory getInstance() {
        return instance;
    }

    static ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        //Restaurant RU Jussieu
        ArrayList<Avis> avisRUJussieu = new ArrayList<Avis>();

        Restaurant ruJussieu = new Restaurant("RU Jussieu", 0, 0, avisRUJussieu);
        restaurants.add(ruJussieu);

        //Restaurant l'olivier
        ArrayList<Avis> avisOlivier = new ArrayList<Avis>();

        Restaurant olivier = new Restaurant("L'olivier", 0, 0, avisOlivier);
        restaurants.add(olivier);

        //Restaurant le Prevert
        ArrayList<Avis> avisPrevert = new ArrayList<Avis>();

        Restaurant prevert = new Restaurant("Le Prevert", 0, 0, avisPrevert);
        restaurants.add(prevert);

        //Restaurant le grillon
        ArrayList<Avis> avisGrillon = new ArrayList<Avis>();

        Restaurant grillon = new Restaurant("Le Grillon", 0, 0, avisGrillon);
        restaurants.add(grillon);

        //Restaurant la Roulotte dorée
        ArrayList<Avis> avisRoulotteDoree = new ArrayList<Avis>();

        Restaurant roulotteDoree = new Restaurant("La Roulotte Dorée", 0, 0, avisRoulotteDoree);
        restaurants.add(roulotteDoree);

        //Restaurant Ali Baba Kebab
        ArrayList<Avis> avisAliBaba = new ArrayList<Avis>();

        Restaurant aliBaba = new Restaurant("Ali Baba Kebab", 0, 0, avisAliBaba);
        restaurants.add(aliBaba);

        return restaurants;
    }
}
