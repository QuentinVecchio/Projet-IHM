package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class DataSingleton {
    private static DataSingleton instance = null;

    private DataSingleton() {
        if(instance == null) {
            instance = new DataSingleton();
        }
    }

    public static DataSingleton getInstance() {
        if(instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

    public static ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        //Restaurant RU Jussieu
        ArrayList<Avis> avisRUJussieu = new ArrayList<Avis>();

        ArrayList<String> entreeJussieu = new ArrayList<String>();
        ArrayList<String> platJussieu = new ArrayList<String>();
        ArrayList<String> dessertJussieu = new ArrayList<String>();
        entreeJussieu.add("Salade Lyonnaise");
        entreeJussieu.add("Macédoine");
        platJussieu.add("Hamburger du chef");
        platJussieu.add("Bavette du chef");
        platJussieu.add("Pizza");
        dessertJussieu.add("Crème brulée");
        dessertJussieu.add("Clémentine");

        Menu menuMidiJussieu = new Menu(entreeJussieu, platJussieu, dessertJussieu);

        Restaurant ruJussieu = new Restaurant("RU Jussieu", 45.780691, 4.876519, avisRUJussieu, null, menuMidiJussieu, null);
        restaurants.add(ruJussieu);

        //Restaurant l'olivier
        ArrayList<Avis> avisOlivier = new ArrayList<Avis>();

        Restaurant olivier = new Restaurant("L'olivier", 45.784221, 4.874811, avisOlivier, null, null, null);
        restaurants.add(olivier);

        //Restaurant le Prevert
        ArrayList<Avis> avisPrevert = new ArrayList<Avis>();

        Restaurant prevert = new Restaurant("Le Prevert", 45.781173, 4.873638, avisPrevert, null, null, null);
        restaurants.add(prevert);

        //Restaurant le grillon
        ArrayList<Avis> avisGrillon = new ArrayList<Avis>();

        Restaurant grillon = new Restaurant("Le Grillon", 45.783927, 4.875049, avisGrillon, null, null, null);
        restaurants.add(grillon);

        //Restaurant la Roulotte dorée
        ArrayList<Avis> avisRoulotteDoree = new ArrayList<Avis>();

        Restaurant roulotteDoree = new Restaurant("La Roulotte Dorée", 45.782565, 4.876553, avisRoulotteDoree, null, null, null);
        restaurants.add(roulotteDoree);

        //Restaurant Ali Baba Kebab
        ArrayList<Avis> avisNinkasi = new ArrayList<Avis>();

        Restaurant ninkasi = new Restaurant("Ninkasi", 45.778878, 4.872942, avisNinkasi, null, null, null);
        restaurants.add(ninkasi);

        return restaurants;
    }
}
