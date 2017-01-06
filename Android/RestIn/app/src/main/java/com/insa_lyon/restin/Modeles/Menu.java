package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;

/**
 * Created by Pierre on 06/01/2017.
 */

public class Menu {
    private ArrayList<String> entrees;
    private ArrayList<String> plats;
    private ArrayList<String> desserts;

    public Menu(ArrayList<String> e, ArrayList<String> p, ArrayList<String> d) {
        this.entrees = e;
        this.plats = p;
        this.desserts = d;
    }

    public ArrayList<String> getEntrees() {
        return entrees;
    }

    public void setEntrees(ArrayList<String> entrees) {
        this.entrees = entrees;
    }

    public ArrayList<String> getPlats() {
        return plats;
    }

    public void setPlats(ArrayList<String> plats) {
        this.plats = plats;
    }

    public ArrayList<String> getDesserts() {
        return desserts;
    }

    public void setDesserts(ArrayList<String> desserts) {
        this.desserts = desserts;
    }
}
