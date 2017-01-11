package com.insa_lyon.restin.Modeles;

import java.util.Date;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class Avis {
    private String avis;
    private double note;
    private Date date;

    public Avis(String avis, double note, Date date) {
        this.avis = avis;
        this.note = note;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
