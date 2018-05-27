/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

/**
 *
 * @author fenina
 */
public class Ville {
    private int id;
    private String nom;
    private String longitude;
    private String latitude;

    public Ville() {
    }

    public Ville(int id, String nom, String longitude, String latitude) {
        this.id = id;
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public Ville(String nom, String longitude, String latitude) {
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
}
