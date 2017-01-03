package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class Restaurant {
    private String name;
    private double lat;
    private double lon;
    private ArrayList<Avis> avis;

    public Restaurant(String name, double lat, double lon, ArrayList<Avis> avis) {
        this.avis = avis;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public ArrayList<Avis> getAvis() {
        return avis;
    }

    public void setAvis(ArrayList<Avis> avis) {
        this.avis = avis;
    }

    public double getMoyenneNote() {
        double sum = 0;
        for (Avis a: avis) {
            sum += a.getNote();
        }
        return sum/avis.size();
    }
}
