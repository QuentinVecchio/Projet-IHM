package com.insa_lyon.restin.Modeles;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class Avis {
    private String avis;
    private double note;

    public Avis(String avis, double note) {
        this.avis = avis;
        this.note = note;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
