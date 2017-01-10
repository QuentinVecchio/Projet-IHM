package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class Restaurant {
    private String name;
    private double lat;
    private double lon;
    private double prixMoyen;
    private ArrayList<Avis> avis;
    private MenuMatin menuMatin;
    private Menu menuMidi;
    private Menu menuSoir;

    public Restaurant(String name, double lat, double lon, ArrayList<Avis> avis, MenuMatin menuMatin, Menu menuMidi, Menu menuSoir,double prix) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.avis = avis;
        this.menuMatin = menuMatin;
        this.menuMidi = menuMidi;
        this.menuSoir = menuSoir;
        this.prixMoyen = prix;
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

    public double getPrixMoyen() { return prixMoyen; }

    public void setPrixMoyen(double prixMoyen) {this.prixMoyen = prixMoyen; }

    public void setAvis(ArrayList<Avis> avis) {
        this.avis = avis;
    }

    public MenuMatin getMenuMatin() { return menuMatin; }
    public void setMenuMatin(MenuMatin menuMatin) { this.menuMatin = menuMatin; }
    public Menu getMenuMidi() { return menuMidi; }
    public void setMenuMidi(Menu menuMidi) { this.menuMidi = menuMidi; }
    public Menu getMenuSoir() { return menuSoir; }
    public void setMenuSoir(Menu menuSoir) { this.menuSoir = menuSoir; }

    public double getMoyenneNote() {
        double sum = 0;
        if(avis.size() == 0) {
            return sum;
        }
        for (Avis a: avis) {
            sum += a.getNote();
        }
        return sum/avis.size();
    }
}
