/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;
import java.util.Date;

/**
 *
 * @author fenina
 */
public class voiture {
    private int id ;
    private int chauffeur_id;
    private String marque;
    private String comfort;
    private int nb_places;
 private Date visite;
    public Date getVisite() {
        return visite;
    }

    public void setVisite(Date visite) {
        this.visite = visite;
    }
   
    private String couleur;
    private String categorie;

    public voiture( int chauffeur_id, String marque, String comfort, int nb_places, String couleur, String categorie) {
        
        this.chauffeur_id = chauffeur_id;
        this.marque = marque;
        this.comfort = comfort;
        this.nb_places = nb_places;
        this.couleur = couleur;
        this.categorie = categorie;
    }
        public voiture( int chauffeur_id, String marque, String comfort, int nb_places, String couleur, String categorie, Date visite) {
        
        this.chauffeur_id = chauffeur_id;
        this.marque = marque;
        this.comfort = comfort;
        this.nb_places = nb_places;
        this.couleur = couleur;
        this.categorie = categorie;
        this.visite = visite;
    }

    public voiture() {
    }

    public voiture(String marque, String comfort, int nb_places, String couleur, String categorie) {
        this.marque = marque;
        this.comfort = comfort;
        this.nb_places = nb_places;
        this.couleur = couleur;
        this.categorie = categorie;
    }

    
    public voiture(int id  , int chauffeur_id, String marque, String comfort, int nb_places, String couleur, String categorie) {
        this.id = id;
        this.chauffeur_id = chauffeur_id;
        this.marque = marque;
        this.comfort = comfort;
        this.nb_places = nb_places;
        this.couleur = couleur;
        this.categorie = categorie;
    }
    
    

    @Override
    public String toString() {
        return "voiture{" + "id=" + id + ", chauffeur_id=" + chauffeur_id + ", marque=" + marque + ", comfort=" + comfort + ", nb_places=" + nb_places + ", couleur=" + couleur + ", categorie=" + categorie + '}';
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChauffeur_id() {
        return chauffeur_id;
    }

    public void setChauffeur_id(int chauffeur_id) {
        this.chauffeur_id = chauffeur_id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getComfort() {
        return comfort;
    }

    public void setComfort(String comfort) {
        this.comfort = comfort;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


    
    
    
}
