package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;

/**
 * Created by Pierre on 06/01/2017.
 */

public class MenuMatin {
    private ArrayList<String> produits;
    private ArrayList<String> boissons;

    public MenuMatin(ArrayList<String> produits, ArrayList<String> boissons) {
        this.produits = produits;
        this.boissons = boissons;
    }

    public ArrayList<String> getProduits() {
        return produits;
    }

    public void setProduits(ArrayList<String> produits) {
        this.produits = produits;
    }

    public ArrayList<String> getBoissons() {
        return boissons;
    }

    public void setBoissons(ArrayList<String> boissons) {
        this.boissons = boissons;
    }
}
