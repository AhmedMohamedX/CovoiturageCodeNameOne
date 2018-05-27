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
public class trajet {
    private int id;
    private int ville_dep;
    private int ville_arr;
    private float distance;
    private String escale;

    public trajet(int ville_dep, int ville_arr, float distance, String escale) {
        this.ville_dep = ville_dep;
        this.ville_arr = ville_arr;
        this.distance = distance;
        this.escale = escale;
    }

    public trajet(int id, int ville_dep, int ville_arr, float distance, String escale) {
        this.id = id;
        this.ville_dep = ville_dep;
        this.ville_arr = ville_arr;
        this.distance = distance;
        this.escale = escale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVille_dep() {
        return ville_dep;
    }

    public void setVille_dep(int ville_dep) {
        this.ville_dep = ville_dep;
    }

    public int getVille_arr() {
        return ville_arr;
    }

    public void setVille_arr(int ville_arr) {
        this.ville_arr = ville_arr;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getEscale() {
        return escale;
    }

    public void setEscale(String escale) {
        this.escale = escale;
    }

    public trajet() {
    }
    
}
